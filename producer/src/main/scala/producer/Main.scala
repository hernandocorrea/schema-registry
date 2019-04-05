package producer

import co.s4n.dto.User
import monix.execution.Scheduler
import monix.kafka.{KafkaProducer, KafkaProducerConfig, Serializer}
import co.s4n.serializer.KafkaSerializer

object Main extends App {

  val topic = "user-topic"

  println("producer !!!!")

  val serCfg: Map[String, String] = Map("schema.registry.url" -> "http://localhost:8081")

  implicit val scheduler: Scheduler = Scheduler.global
  implicit val serializer:Serializer[User] = KafkaSerializer.serializer()

  val producerCfg = KafkaProducerConfig.default
  val producer = KafkaProducer[String, User](producerCfg, scheduler)

  val user = User("hernando correa lazo 123456", 15)

  (for{
    result <- producer.send(topic, user)
    _ <- producer.close()
  } yield {
    result match {
      case Some(recordMetadata) =>
        println("Message sent")
        println("offset " + recordMetadata.offset())
      case None => println("Error sending message")
    }
  }).runAsyncAndForget

  Thread.sleep(1000)

  println("Finish !!!")

}


