case class V[A](name: A, dist: Double, prev: A) extends Ordered[V[A]] {
  override def compare(that: V[A]): Int = this.dist compareTo that.dist
}

class ShortestPath[A](graph: Graph[A]) {

  type Path[B] = (Double, Vector[B])

  // return shortest paths from source to all other vertices in the graph
  def find(src: A): Map[A,Path[A]] = {
    val pq = new MinPriorityQueue[A](graph.size)
    pq ++= graph.neighbors(src).map(e => V(e.head, e.weight, src))

    def loop(visited: Set[A], paths: Map[A,Path[A]]): Map[A,Path[A]] =
      pq.delMin() match {
        case None => paths
        case Some(V(name,dist,prev)) =>
          // get unvisited neighbors with a new Dijkstra greedy score
          val adj = graph.neighbors(name).filterNot(visited contains _.head).map(
            e => V(e.head, dist+e.weight, name))
          pq update adj
          val path = paths.get(prev) match {
            case None => Vector(name)
            case Some((_,p)) => p :+ name
          }
          loop(visited + name, paths + (name -> (dist, path)))
      }
    loop(Set(src), Map.empty)
  }

}
