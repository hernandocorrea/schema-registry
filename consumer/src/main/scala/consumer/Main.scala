package consumer

import co.s4n.configuration.Configuration
import co.s4n.dto.User
import co.s4n.serializer.AvroSerializer
import com.sksamuel.avro4s.RecordFormat
import monix.execution.Scheduler
import monix.kafka.{Deserializer, KafkaConsumerObservable}
import monix.reactive.Consumer
import org.apache.avro.generic.GenericData.Record

object Main extends App {

  val config = Configuration
  implicit val scheduler: Scheduler = Scheduler.global
  implicit val deserializer: Deserializer[Object] = AvroSerializer.deserializer(config.schemaRegistryConf, isKey = false)

  val kafkaConsumerConfig = config.kafkaConsumerConfig

  println("Start consuming ...")
  println(s"Topic => ${config.topic}")
  KafkaConsumerObservable[String, Object](kafkaConsumerConfig, List(config.topic))
    .consumeWith(Consumer.foreach(
      record => record.value() match {
      case r: Record =>
        val format = RecordFormat[User]
        val user = format.from(r)
        println("**************")
        println(user)
      case _ => println("Fail cast object")
    }
    )).runAsyncAndForget

  Thread.sleep(10000)
}