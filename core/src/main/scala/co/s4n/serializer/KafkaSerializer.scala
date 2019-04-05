package co.s4n.serializer

import co.s4n.dto.User
import com.sksamuel.avro4s.AvroSchema
import com.sksamuel.avro4s.kafka.GenericSerde
import monix.kafka.Serializer
import monix.kafka.Deserializer
import org.apache.avro.Schema
import org.apache.kafka.common.serialization.{Serializer => KafkaSerializer}
import org.apache.kafka.common.serialization.{Deserializer => KafkaDeserializer}

object KafkaSerializer {

  def serializer(): Serializer[User] =
    Serializer[User](
      className = "co.s4n.serializer.KafkaSerializer",
      classType = classOf[KafkaSerializer[User]],
      constructor = _ => {
        implicit val schema: Schema = AvroSchema[User]
        new GenericSerde[User]().serializer()
      }
    )

  def deserializer(): Deserializer[User] =
    Deserializer[User](
      className = "co.s4n.serializer.KafkaSerializer",
      classType = classOf[KafkaDeserializer[User]],
      constructor = _ => {
        implicit val schema: Schema = AvroSchema[User]
        new GenericSerde[User]().deserializer()
      }
    )

}
