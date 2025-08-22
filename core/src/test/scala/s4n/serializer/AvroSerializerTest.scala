package s4n.serializer


import co.s4n.configuration.Configuration
import co.s4n.dto.User
import co.s4n.serializer.AvroSerializer
import com.sksamuel.avro4s.RecordFormat
import org.apache.avro.generic.GenericData.Record
import org.scalatest.{FlatSpec, Matchers}

class AvroSerializerTest  extends FlatSpec with Matchers {

  "Serialize" should "be OK" in {
    val config = Configuration

    val user = User("Hernando")
    val format = RecordFormat[User]
    val record = format.to(user)

    val serializer = AvroSerializer.serializer(config.schemaRegistryConf, isKey = false).create()
    val bytes = serializer.serialize(config.topic, record)

    val deserializer = AvroSerializer.deserializer(config.schemaRegistryConf, isKey = false).create()
    val result = deserializer.deserialize(config.topic, bytes)

    result match {
      case r: Record => format.from(r) shouldBe user
      case _ => fail("Fail cast object")
    }
  }

}
