package producer

import monix.eval.Task
import monix.execution.Scheduler
import monix.kafka.{KafkaProducer, KafkaProducerConfig}

object Main extends App {

  println("producer !!!!")

  implicit val scheduler: Scheduler = Scheduler.global

  // Init
  val producerCfg = KafkaProducerConfig.default.copy(
    bootstrapServers = List("127.0.0.1:9092")
  )

  val producer = KafkaProducer[String,String](producerCfg, scheduler)


  val messagesTask = Task.sequence{(1 to 10).map{
    x => producer.send("my-topic", s"my-message-DDDDDDDDDDDDDD-$x")
  }}

  // For sending one message
  (for{
    _ <- messagesTask
    _ <- producer.close()
  } yield ()).runAsyncAndForget

  // For closing the producer connection
  Thread.sleep(10000)

  println("closeeee !!!")

}
