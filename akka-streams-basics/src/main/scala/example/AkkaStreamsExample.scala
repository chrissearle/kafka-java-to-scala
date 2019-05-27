package example

import akka.{Done, NotUsed}
import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Flow, Sink, Source}

import scala.concurrent.Future

object IntSeqExample extends App {
  implicit val system = ActorSystem("IntSeqExample")
  implicit val materializer = ActorMaterializer()

  val source: Source[Int, NotUsed] = Source(1 to 100)

  val done: Future[Done] = source.runForeach(i => println(i))

  implicit val ec = system.dispatcher
  done.onComplete(_ => system.terminate())
}

object IntSeqExample2 extends App {
  implicit val system = ActorSystem("IntSeqExample2")
  implicit val materializer = ActorMaterializer()

  val source: Source[Int, NotUsed] = Source(1 to 100)

  val sink: Sink[Int, Future[Done]] = Sink.foreach[Int](println)

  val done = source.runWith(sink)

  implicit val ec = system.dispatcher
  done.onComplete(_ => system.terminate())
}

object IntSeqExample3 extends App {
  implicit val system = ActorSystem("IntSeqExample2")
  implicit val materializer = ActorMaterializer()

  val evenFlow: Flow[Int, Int, NotUsed] =
    Flow[Int].filter(i => i % 2 == 0)
  val toStringFlow: Flow[Int, String, NotUsed] =
    Flow[Int].map(i => i.toString)

  val source: Source[Int, NotUsed] = Source(1 to 100)
  val evenSource: Source[Int, NotUsed] = source.via(evenFlow)
  val evenStringSource: Source[String, NotUsed] = evenSource.via(toStringFlow)

  val sink: Sink[String, Future[Done]] = Sink.foreach[String](println)

  val done = evenStringSource.runWith(sink)

  implicit val ec = system.dispatcher
  done.onComplete(_ => system.terminate())
}

object FactorialExample extends App {
  implicit val system = ActorSystem("FactorialExample")
  implicit val materializer = ActorMaterializer()

  val source: Source[Int, NotUsed] = Source(1 to 100)

  val factorials = source.scan(BigInt(1))((acc, next) => acc * next)

  val done: Future[Done] = factorials
    .zipWith(Source(0 to 100))((num, idx) => s"$idx! = $num")
    .runForeach(println)

  implicit val ec = system.dispatcher
  done.onComplete(_ => system.terminate())
}
