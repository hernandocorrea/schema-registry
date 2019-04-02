lazy val commonSettings = Seq(
  version := "0.1",
  scalaVersion := "2.12.8")

lazy val consumer = project
  .settings(name := "schema-registry-consumer")
  .settings(libraryDependencies += "io.monix" %% "monix-kafka-1x" % "1.0.0-RC2")

lazy val producer = project
  .settings(name := "schema-registry-producer")

//lazy val schema-registry = (project in file(".")).aggregate(consumer)