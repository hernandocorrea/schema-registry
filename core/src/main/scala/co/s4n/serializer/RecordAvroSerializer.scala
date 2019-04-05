package co.s4n.serializer

import com.sksamuel.avro4s.Record
import io.confluent.kafka.serializers.AbstractKafkaAvroSerializer
//import com.sksamuel.avro4s.kafka.GenericSerde
import org.apache.kafka.common.serialization.Serializer

//class RecordAvroSerializer extends AbstractKafkaAvroSerializer //with Serializer {


//}

  /*
public class KafkaAvroSerializer extends AbstractKafkaAvroSerializer implements Serializer<Object> {
  private boolean isKey;

  public KafkaAvroSerializer() {
}

  public KafkaAvroSerializer(SchemaRegistryClient client) {
  this.schemaRegistry = client;
}

  public KafkaAvroSerializer(SchemaRegistryClient client, Map<String, ?> props) {
  this.schemaRegistry = client;
  this.configure(this.serializerConfig(props));
}

  public void configure(Map<String, ?> configs, boolean isKey) {
  this.isKey = isKey;
  this.configure(new KafkaAvroSerializerConfig(configs));
}

  public byte[] serialize(String topic, Object record) {
  return this.serializeImpl(this.getSubjectName(topic, this.isKey, record), record);
}

  public void close() {
}
}*/