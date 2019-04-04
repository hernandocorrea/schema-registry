package consumer

import co.s4n.dto.User
import co.s4n.serializer.KafkaSerializer
import monix.execution.Scheduler
import monix.kafka.{Deserializer, KafkaConsumerConfig, KafkaConsumerObservable, Serializer}
import monix.reactive.Consumer

object Main extends App {

  println("consumer")

  val serCfg: Map[String, String] = Map("schema.registry.url" -> "http://localhost:8081")
  implicit val deserializer: Deserializer[User] = KafkaSerializer.deserializer(serCfg, false)

  val consumerCfg = KafkaConsumerConfig.default.copy(
    bootstrapServers = List("127.0.0.1:9092"),
    groupId = "kafka-tests"
  )

  implicit val scheduler: Scheduler = Scheduler.global

    KafkaConsumerObservable[String, User](consumerCfg, List("user-topic"))
      .consumeWith(Consumer.foreach(
        x =>{
          println("consuming")
          val user = x.value()
          println(user.name)
          println(user.age)
        }
      )).runAsyncAndForget

  //while(true)()

  Thread.sleep(100000)

}