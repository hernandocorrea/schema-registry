
lazy val commonSettings = Seq(
  version := "0.1",
  scalaVersion := "2.12.8",
  resolvers += "confluent.io" at "http://packages.confluent.io/maven/",
  libraryDependencies ++= Seq(
    "io.monix" %% "monix-kafka-1x" % "1.0.0-RC2",
    "com.sksamuel.avro4s" %% "avro4s-core" % "2.0.4",
    "com.sksamuel.avro4s" %% "avro4s-kafka" % "2.0.4",
    "io.confluent" % "kafka-streams-avro-serde" % "5.0.0",
    "org.scalatest" % "scalatest_2.12" % "3.0.1",
    "com.iheart" %% "ficus" %  "1.4.3",
    "com.github.pureconfig" %% "pureconfig" % "0.9.2",
    "io.confluent" % "kafka-avro-serializer" % "5.0.0"),
  excludeDependencies ++= Seq(
    "log4j"                    % "log4j",
    "org.apache.logging.log4j" % "log4j-to-slf4j",
    "org.slf4" % "log4j-over-slf4j",
    
    "org.slf4j" % "slf4j-log4j12"
  ))

lazy val core = project
  .settings(name := "core")
  .settings(commonSettings)

lazy val consumer = project
  .settings(name := "schema-registry-consumer")
  .settings(commonSettings)
  .dependsOn(core)

lazy val producer = project
  .settings(name := "schema-registry-producer")
  .settings(commonSettings)
  .dependsOn(core)

//lazy val schema-registry = (project in file(".")).aggregate(consumer)