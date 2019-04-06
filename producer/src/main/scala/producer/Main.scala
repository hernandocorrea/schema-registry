package producer

import co.s4n.configuration.Configuration
import co.s4n.dto.User
import co.s4n.serializer.AvroSerializer
import com.sksamuel.avro4s.{Record, RecordFormat}
import monix.execution.Scheduler
import monix.kafka.{KafkaProducer, Serializer}

object Main extends App {

  val config = Configuration

  implicit val scheduler: Scheduler = Scheduler.global
  implicit val serializer: Serializer[Object] = AvroSerializer.serializer(config.schemaRegistryConf, isKey = false)

  val format = RecordFormat[User]
  val recordVal: Record = format.to(User("Hernando Correa", 0))

  val producer = KafkaProducer[String, Object](config.kafkaProducerConfig, scheduler)

  (for{
    result <- producer.send(config.topic, recordVal)
    _ <- producer.close()
  } yield {
    result match{
      case Some(recordMetadata) =>
        println("Message sent")
        println("offset => " + recordMetadata.offset())
      case None => println("Error sending message")
    }
  }).runAsyncAndForget

}
