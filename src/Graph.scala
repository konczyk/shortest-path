case class Edge[A](weight: Int, head: A) extends Ordered[Edge[A]] {
  override def compare(that: Edge[A]):Int = this.weight - that.weight
}

class Graph[A] private (map: Map[A, Set[Edge[A]]], val size: Int) {
  def edges(vertex: A): Option[Set[Edge[A]]] = map.get(vertex)
}

object Graph {

  // expected format
  // vertex1  weight1,edge1  weight2,edge2 [...]
  // vertex2  weight1,edge1  weight2,edge2 [...]
  // [...]
  // f function converts from String to a desired Vertex format (Int, String...)
  def apply[A](it: Iterator[String], f: String => A): Graph[A] = {
    build(it, Map.empty[A, Set[Edge[A]]], Set.empty, f)
  }

  private def build[A](it: Iterator[String], map: Map[A, Set[Edge[A]]],
      vs: Set[A], f: String => A): Graph[A] = {
    if (!it.hasNext) new Graph(map, vs.size)
    else {
      val e = it.next.trim.split("\\s+")
      val k = f(e(0))
      val v = e.drop(1).map(_.split(",")).map{
        case Array(a,b) => Edge(a.toInt, f(b))
      }.toSet
      build(it, map + (k -> v), vs + k ++ v.map(_.head), f)
    }
  }

}

