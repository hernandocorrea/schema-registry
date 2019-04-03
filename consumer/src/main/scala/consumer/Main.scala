package consumer

import monix.eval.Task
import monix.execution.Scheduler
import monix.kafka.{KafkaConsumerConfig, KafkaConsumerObservable}
import monix.reactive.{Consumer, Observable}

object Main extends App {

  println("consumer")

  val consumerCfg = KafkaConsumerConfig.default.copy(
    bootstrapServers = List("127.0.0.1:9092"),
    groupId = "kafka-tests"
    // you can use this settings for At Most Once semantics:
    // observableCommitOrder = ObservableCommitOrder.BeforeAck
  )
  
  implicit val scheduler: Scheduler = Scheduler.global


  val observable =
    KafkaConsumerObservable[String,String](consumerCfg, List("my-topic"))
      .consumeWith(Consumer.foreach(
        x =>{
          println("consumed")
          println(x.value())
        }
      )).runAsyncAndForget

  println("size")
  KafkaConsumerObservable[String,String](consumerCfg, List("my-topic"))
      .toListL.map(l => println(l.size)).runAsyncAndForget

  Thread.sleep(100000)

}
