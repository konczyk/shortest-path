case class V[A](name: A, score: Int) extends Ordered[V[A]] {
  override def compare(that: V[A]):Int = this.score - that.score
}

class Graph[A] private (map: Map[A, Set[V[A]]], val size: Int) {
  def neighbors(vertex: A): Set[V[A]] = map.getOrElse(vertex, Set())
}

object Graph {

  // expected format
  // vertex  vertex,edge  length vertex,edge length [...]
  // vertex  vertex,edge  length vertex,edge length [...]
  // [...]
  // f function converts from String to a desired Vertex format (Int, String...)
  def apply[A](it: Iterator[String], f: String => A): Graph[A] = {
    build(it, Map.empty[A, Set[V[A]]], Set.empty, f)
  }

  private def build[A](it: Iterator[String], map: Map[A, Set[V[A]]],
      vs: Set[A], f: String => A): Graph[A] = {
    if (!it.hasNext) new Graph(map, vs.size)
    else {
      val e = it.next.trim.split("\\s+")
      val k = f(e(0))
      val v = e.drop(1).map(_.split(",")).map{
        case Array(a,b) => V(f(a), b.toInt)
      }.toSet
      build(it, map + (k -> v), vs + k ++ v.map(_.name), f)
    }
  }

}

