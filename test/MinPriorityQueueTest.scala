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

}
