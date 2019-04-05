package producer

import co.s4n.configuration.Configuration
import co.s4n.dto.User
import monix.execution.Scheduler
import monix.kafka.{KafkaProducer, Serializer}
import co.s4n.serializer.KafkaSerializer

object Main extends App {

  val config = Configuration
  val serCfg: Map[String, String] = Map("schema.registry.url" -> config.schemaRegistryConf.toString)

  implicit val scheduler: Scheduler = Scheduler.global
  implicit val serializer:Serializer[User] = KafkaSerializer.serializer()

  val kafkaProducerConfig = config.kafkaProducerConfig
  val producer = KafkaProducer[String, User](kafkaProducerConfig, scheduler)

  val topic = config.topic
  val user = User("hernando correa lazo 3", 30)

  println("Start producing ...")
  (for{
    result <- producer.send(topic, user)
    _ <- producer.close()
  } yield {
    result match {
      case Some(recordMetadata) =>
        println("Message sent => " + user.name)
        println("offset " + recordMetadata.offset())
      case None => println("Error sending message")
    }
  }).runAsyncAndForget

  Thread.sleep(1000)

  println("Finish !!!")

}
