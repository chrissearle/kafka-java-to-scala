# Kafka - java to scala - introduction

I was recently a participant on a [Confluent on-premesis kafka course](https://www.confluent.io/training/). While working through the labs (which are in java), for fun I tried the same code in kotlin. That was fun - but I thought it could be a nice exercise to convert from java to scala - step by step - and maybe learn some new scala stuff on the way. It will assume some level of kafka knowledge - what is a producer, consumer, topic etc.

It is important to understand that it is written from my viewpoint - someone who has played with scala, likes it, but has never really had time to get into it - so this will be somewhat of a discovery journey for me too.

Posts:

- [Get a java example running](2-java.md)
- [Convert java example to scala](3-scala-v1.md)
- [Update the scala version](4-scala-v2.md) to be more "scala-like" and add a config file instead of hard coded values
- [A slight digression to look at basic akka-streams](5-akka-basics.md)
- [Use akka-streams for the scala example](6-akka-kafka.md)


## Initial setup

Firstly you will need the code.

Clone this git repo: https://github.com/chrissearle/kafka-java-to-scala

## Docker

All of the related articles assume that you have kafka running using the supplied `docker-compose.yml` file - so in the working directory - run:

    docker-compose up -d

Make sure you have a good amount of memory available to docker - 4-5 Gb minimum :)
