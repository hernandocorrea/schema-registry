# Kafka Avro Schema Registry Example

This project demonstrates a simple producer-consumer setup using Kafka, with message serialization handled by Avro and a Schema Registry.

The project is written in Scala and built with `sbt`. It is divided into three main modules:

-   `core`: Contains shared logic, including the Avro serializer/deserializer and the `User` data transfer object (DTO).
-   `producer`: A simple application that sends 5 sample `User` messages to a Kafka topic.
-   `consumer`: A simple application that subscribes to the Kafka topic and prints the `User` messages it receives.

## Prerequisites

-   Java (version 8 or higher)
-   `sbt`
-   A running Kafka instance
-   A running Confluent Schema Registry instance

## Configuration

The project is configured via `application.conf` files located in each module's `src/main/resources` directory. You will need to update these files with the correct connection details for your Kafka broker and Schema Registry.

-   `core/src/main/resources/application.conf`: Main configuration for the Kafka topic, consumer group, and Schema Registry URL.
-   `consumer/src/main/resources/application.conf`: Specific configuration for the consumer.
-   `producer/src/main/resources/application.conf`: Specific configuration for the producer.

## How to Run

You can run the producer and consumer applications using `sbt`.

### Running the Producer

Open a terminal in the project root and run the following command:

```bash
sbt "producer/run"
```

This will compile the code and start the producer, which will send 5 messages and then exit.

### Running the Consumer

Open a second terminal in the project root and run the following command:

```bash
sbt "consumer/run"
```

This will start the consumer, which will listen for messages on the configured topic and print them to the console.
