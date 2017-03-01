case class E[A](head: A, weight: Double)

class Graph[A] private (graph: Map[A, Set[E[A]]], val size: Int) {
  def neighbors(vertex: A): Set[E[A]] = graph.getOrElse(vertex, Set())
}

object Graph {

  // expected format
  // vertex  vertex,edge  length vertex,edge length [...]
  // vertex  vertex,edge  length vertex,edge length [...]
  // [...]
  // f function converts from String to a desired Vertex format (Int, String...)
  def apply[A](it: Iterator[String], f: String => A): Graph[A] = {
    build(it, Map.empty[A, Set[E[A]]], Set.empty, f)
  }

  private def build[A](it: Iterator[String], graph: Map[A, Set[E[A]]],
      vs: Set[A], f: String => A): Graph[A] = {
    if (!it.hasNext) new Graph(graph, vs.size)
    else {
      val e = it.next.trim.split("\\s+")
      val k = f(e(0))
      val v = e.drop(1).map(_.split(",")).map{
        case Array(a,b) => E(f(a), b.toDouble)
      }.toSet
      build(it, graph + (k -> v), vs + k ++ v.map(_.head), f)
    }
  }

}

