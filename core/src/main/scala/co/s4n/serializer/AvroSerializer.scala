package co.s4n.serializer

import io.confluent.kafka.serializers.{KafkaAvroDeserializer, KafkaAvroSerializer}
import monix.kafka.{Serializer => MonixSerializer}
import monix.kafka.{Deserializer => MonixDeserializer}
import collection.JavaConverters._

object AvroSerializer {

  def serializer(cfg: Map[String, String], isKey: Boolean): MonixSerializer[Object] =
    MonixSerializer[Object](
      className = "io.confluent.kafka.serializers.KafkaAvroSerializer",
      classType = classOf[KafkaAvroSerializer],
      constructor = _ => {
        val serializer = new KafkaAvroSerializer()
        serializer.configure(cfg.asJava, isKey)
        serializer
      }
    )

  def deserializer(cfg: Map[String, String], isKey: Boolean): MonixDeserializer[Object] =
    MonixDeserializer[Object](
      className = "io.confluent.kafka.serializers.KafkaAvroDeserializer",
      classType = classOf[KafkaAvroDeserializer],
      constructor = _ => {
        val deserializer = new KafkaAvroDeserializer()
        deserializer.configure(cfg.asJava, isKey)
        deserializer
      }
    )
}
