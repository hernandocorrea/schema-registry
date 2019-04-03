package consumer

import monix.execution.Scheduler
import monix.kafka.{KafkaConsumerConfig, KafkaConsumerObservable}
import monix.reactive.Consumer

object Main extends App {

  println("consumer")

  val consumerCfg = KafkaConsumerConfig.default.copy(
    bootstrapServers = List("127.0.0.1:9092"),
    groupId = "kafka-tests"
    // you can use this settings for At Most Once semantics:
    // observableCommitOrder = ObservableCommitOrder.BeforeAck
  )

  implicit val scheduler: Scheduler = Scheduler.global

    KafkaConsumerObservable[String,String](consumerCfg, List("my-topic"))
      .consumeWith(Consumer.foreach(
        x =>{
          println("consuming")
          println(x.value())
        }
      )).runAsyncAndForget



  while(true)()

}
