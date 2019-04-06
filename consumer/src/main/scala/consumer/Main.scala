package consumer

import co.s4n.configuration.Configuration
import co.s4n.dto.User
import co.s4n.serializer.AvroSerializer
import monix.execution.Scheduler
import monix.kafka.{Deserializer, KafkaConsumerObservable}
import monix.reactive.Consumer

object Main extends App {

  val config = Configuration
  implicit val scheduler: Scheduler = Scheduler.global
  implicit val deserializer: Deserializer[Object] = AvroSerializer.deserializer(config.schemaRegistryConf, isKey = false)

  val kafkaConsumerConfig = config.kafkaConsumerConfig

  println("Start consuming ...")
  KafkaConsumerObservable[String, Object](kafkaConsumerConfig, List("user-topic"))
    .consumeWith(Consumer.foreach(
      record => record.value() match {
        case user: User =>
          println("Consuming user")
          println(user)
      }
    )).runAsyncAndForget

  while(true)()
}