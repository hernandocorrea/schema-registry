package producer

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

  // For sending one message
  (1 to 10).foreach{
    x =>
    producer.send("my-topic", s"my-message-$x").map{
      case Some(r) =>
        println("sent *****************" +x)
        println(r.hasOffset)
        println(r.offset())
        println(r.topic())
        println("sent *****************"+x)
      case _ => println("nothing for " + x)
    }.runAsyncAndForget
  }

  // For closing the producer connection
  val closeF = producer.close().runToFuture

  Thread.sleep(10000)

  println("closeeee !!!")

}
