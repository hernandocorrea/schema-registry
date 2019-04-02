
lazy val dependencies = libraryDependencies += "io.monix" %% "monix-kafka-1x" % "1.0.0-RC2"

lazy val commonSettings = Seq(
  version := "0.1",
  scalaVersion := "2.12.8")

lazy val consumer = project
  .settings(name := "schema-registry-consumer")
  .settings(commonSettings)
  .settings(dependencies)

lazy val producer = project
  .settings(name := "schema-registry-producer")
  .settings(commonSettings)
  .settings(dependencies)

//lazy val schema-registry = (project in file(".")).aggregate(consumer)