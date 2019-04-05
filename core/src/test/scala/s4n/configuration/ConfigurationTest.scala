package s4n.configuration

import co.s4n.configuration.Configuration
import org.scalatest.{FlatSpec, Matchers}

class ConfigurationTest  extends FlatSpec with Matchers {

  "Configured values" should "be OK" in {

    Configuration.topic shouldBe "user-topic"

    Configuration.schemaRegistryConf.host shouldBe "http://localhost"
    Configuration.schemaRegistryConf.port shouldBe 8081
    Configuration.schemaRegistryConf.toString shouldBe "http://localhost:8081"

    Configuration.kafkaProducerConfig.bootstrapServers shouldBe List("127.0.0.1:9092")

    Configuration.kafkaConsumerConfig.bootstrapServers shouldBe List("127.0.0.1:9092")
    Configuration.kafkaConsumerConfig.groupId shouldBe "user-consumer"

  }


}
