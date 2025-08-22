package co.s4n.configuration

import com.typesafe.config.{Config, ConfigFactory}
import io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig
import net.ceedubs.ficus.Ficus._
import monix.kafka.{KafkaConsumerConfig, KafkaProducerConfig}
import pureconfig.loadConfig

object Configuration {

  val namespace = "s4n.schema-registry"

  val config: Config = sys.props.get("env").fold(ConfigFactory.load()) { env =>
    ConfigFactory.load(s"application-$env").withFallback(ConfigFactory.load())
  }

  val topic: String = loadConfig[String](config,s"$namespace.kafka.topic").fold(_ =>throw new Exception, identity)

  val schemaRegistryConf: Map[String, String] = {
    val schemaConfig = loadConfig[SchemaRegistryConfig](config, s"$namespace.schema-registry")
      .fold(_ =>throw new Exception, identity)
    Map(AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG -> schemaConfig.toString)
  }

  val kafkaProducerConfig: KafkaProducerConfig = KafkaProducerConfig.apply(config.as[Config](s"$namespace.kafka.producer.kafka-clients"))

  val kafkaConsumerConfig: KafkaConsumerConfig = KafkaConsumerConfig.apply(config.as[Config](s"$namespace.kafka.consumer.kafka-clients"))

}
