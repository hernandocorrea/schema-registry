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
  val recordMetadataF = producer.send("my-topic", "my-message").runToFuture

  // For closing the producer connection
  val closeF = producer.close().runToFuture

}
