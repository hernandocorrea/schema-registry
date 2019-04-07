package co.s4n.dto

case class User(name: String){

  override def toString: String = s"Name => $name"

}
