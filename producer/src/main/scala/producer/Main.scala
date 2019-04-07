package producer

import co.s4n.configuration.Configuration
import co.s4n.dto.User
import co.s4n.serializer.AvroSerializer
import com.sksamuel.avro4s.{AvroSchema, RecordFormat}
import monix.eval.Task
import monix.execution.Scheduler
import monix.kafka.{KafkaProducer, Serializer}

object Main extends App {

  val config = Configuration

  implicit val scheduler: Scheduler = Scheduler.global
  implicit val serializer: Serializer[Object] = AvroSerializer.serializer(config.schemaRegistryConf, isKey = false)

  val producer = KafkaProducer[String, Object](config.kafkaProducerConfig, scheduler)


  println("*******************")
  val schema = AvroSchema[User]
  println(schema.toString)
  println("Producer")
  println(s"Topic ${config.topic}")
  println("*******************")

  val format = RecordFormat[User]
  val messagesTask = Task.sequence{(1 to 5).map{
    age =>
      val user = User(s"Hernando Correa $age")
      val recordVal = format.to(user)
      producer.send(config.topic, recordVal).map{
        case Some(value) =>
          println("**************")
          println(user)
          println(s"Offset => ${value.offset()}")
        case None => println("Error sending message")
      }
  }}
  // For sending one message
  (for{
    _ <- messagesTask
    _ <- producer.close()
  } yield ())runAsyncAndForget

  // For closing the producer connection
  Thread.sleep(10000)

  println("Closing producer")

}
