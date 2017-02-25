case class Edge[A](weight: Int, head: A) extends Ordered[Edge[A]] {
  override def compare(that: Edge[A]):Int = this.weight - that.weight
}

