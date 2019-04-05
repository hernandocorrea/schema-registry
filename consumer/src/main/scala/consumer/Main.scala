package consumer

import co.s4n.configuration.Configuration
import co.s4n.dto.User
import co.s4n.serializer.KafkaSerializer
import monix.execution.Scheduler
import monix.kafka.{Deserializer, KafkaConsumerObservable}
import monix.reactive.Consumer

object Main extends App {

  val config = Configuration
  val serCfg: Map[String, String] = Map("schema.registry.url" -> config.schemaRegistryConf.toString)
  implicit val scheduler: Scheduler = Scheduler.global
  implicit val deserializer: Deserializer[User] = KafkaSerializer.deserializer()

  val kafkaConsumerConfig = config.kafkaConsumerConfig

  println("Start consuming ...")
  KafkaConsumerObservable[String, User](kafkaConsumerConfig, List("user-topic"))
    .consumeWith(Consumer.foreach(
      x =>{
        println("consuming")
        val user = x.value()
        println(s"User name => ${user.name}")
      }
    )).runAsyncAndForget

  //while(true)()

  Thread.sleep(100000)
  System.exit(0)
}