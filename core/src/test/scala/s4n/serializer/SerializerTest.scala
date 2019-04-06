package s4n.serializer


import co.s4n.configuration.Configuration
import co.s4n.dto.User
import co.s4n.serializer.{UserDeserializer, UserSerializer}
import org.scalatest.{FlatSpec, Matchers}

import collection.JavaConverters._

class SerializerTest  extends FlatSpec with Matchers {

  "Serialize" should "be OK" in {
    val config = Configuration.schemaRegistryConf.asJava

    val serializer = new UserSerializer
    serializer.configure(config, isKey = false)
    val user = User("My name", 18)
    val bytes: Array[Byte] = serializer.serialize("topic", user)

    val deserializer = new UserDeserializer
    deserializer.configure(config, isKey = false)
    val x = deserializer.deserialize("topic", bytes)

    println(x)

    0 shouldBe 0
  }

}
