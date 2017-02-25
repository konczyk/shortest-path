import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class MinPriorityQueueTest extends FunSuite {

  test("add less than 4 items") {
    val pq = new MinPriorityQueue[Int](10)
    assert(pq.toSet === Set())
    assert((pq ++= List(Edge(1,2))).toSet === Set(Edge(1,2)))
  }

  test("add more than 4 items") {
    val pq = new MinPriorityQueue[Int](10)
    val edges = Set(Edge(1,1), Edge(1,2), Edge(1,3), Edge(1,4), Edge(1,5))
    assert((pq ++= edges).toSet === edges)
  }

  test("delMin from empty list") {
    val pq = new MinPriorityQueue[Int](4)
    assert(pq.delMin() === None)
  }

  test("delMin until empty") {
    val pq = new MinPriorityQueue[Int](10)
    val e1 = Edge(5,1)
    val e2 = Edge(1,2)
    val e3 = Edge(4,5)
    val e4 = Edge(2,2)
    pq ++= Set(e1,e2,e3,e4)

    assert(pq.delMin() === Some(e2))
    assert(pq.delMin() === Some(e4))
    assert(pq.delMin() === Some(e3))
    assert(pq.delMin() === Some(e1))
    assert(pq.delMin() === None)
  }

  test("increase priority") {
    val pq = new MinPriorityQueue[Int](10)
    // pq: 1,2 -> 3,2 -> 4,5 -> 5,1
    pq ++= Set(Edge(5,1), Edge(1,2), Edge(4,5), Edge(3,2))
    pq.updatePriority(Map(Edge(5,1) -> Edge(2,1)))

    val actual = List(pq.delMin(), pq.delMin())
    val expected = List(Some(Edge(1,2)), Some(Edge(2,1)))
    assert(actual === expected)
  }

  test("decrease priority") {
    val pq = new MinPriorityQueue[Int](10)
    // pq: 1,2 -> 3,2 -> 4,5 -> 5,1
    pq ++= Set(Edge(5,1), Edge(1,2), Edge(4,5), Edge(3,2))
    pq.updatePriority(Map(Edge(1,2) -> Edge(8,2)))

    val actual = (for (_ <- 1 to 4) yield pq.delMin()).map{case Some(x) => x}
    val expected = List(Edge(3,2), Edge(4,5), Edge(5,1), Edge(8,2))
    assert(actual === expected)
  }

}
