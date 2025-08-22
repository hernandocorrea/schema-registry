package co.s4n.configuration

case class SchemaRegistryConfig(host:String, port: Int){

  override def toString: String = s"$host:$port"

}


