package co.s4n.dto

case class User(name: String, age: Int){

  override def toString: String = s"Name => $name, Age => $age"

}
