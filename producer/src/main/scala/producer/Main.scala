package producer

import co.s4n.dto.User
import monix.execution.Scheduler
import monix.kafka.{KafkaProducer, KafkaProducerConfig, Serializer}
import co.s4n.serializer.KafkaSerializer

import scala.language.postfixOps

object Main extends App {

  val topic = "user-topic"

  println("producer !!!!")

  val serCfg: Map[String, String] = Map("schema.registry.url" -> "http://localhost:8081")

  implicit val scheduler: Scheduler = Scheduler.global
  implicit val serializer:Serializer[User] = KafkaSerializer.serializer(serCfg, isKey = false)

  val producerCfg = KafkaProducerConfig.default
  val producer = KafkaProducer[String, User](producerCfg, scheduler)

  val user = User("hernando correa lazo 121", 15)

  (for{
    result <- producer.send(topic, user)
    _ <- producer.close()
  } yield {
    result match {
      case Some(recordMetadata) =>
        println("Message sent")
        println("key size " + recordMetadata.serializedKeySize())
        println("value size " + recordMetadata.serializedValueSize())
        println("has offset " + recordMetadata.hasOffset)
        println("partition " + recordMetadata.partition())
        println("timestamp " + recordMetadata.timestamp())
        println("offset " + recordMetadata.offset())
        println("topic " + recordMetadata.topic())
      case None => println("Error sending message")
    }
  }).runAsyncAndForget

  Thread.sleep(1000)

  println("Finish !!!")

}


