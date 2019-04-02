package consumer

import monix.kafka.{KafkaConsumerConfig, KafkaConsumerObservable}

object Main extends App {

  println("consumer")

  val consumerCfg = KafkaConsumerConfig.default.copy(
    bootstrapServers = List("127.0.0.1:9092"),
    groupId = "kafka-tests"
    // you can use this settings for At Most Once semantics:
    // observableCommitOrder = ObservableCommitOrder.BeforeAck
  )

  val observable =
    KafkaConsumerObservable[String,String](consumerCfg, List("my-topic"))
      .take(10000)
      .map(_.value())

}
