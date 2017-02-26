import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class MinPriorityQueueTest extends FunSuite {

  test("add less than 4 vertices") {
    val pq = new MinPriorityQueue[Int](10)
    assert(pq.toSet === Set())
    assert((pq ++= List(V(1,2))).toSet === Set(V(1,2)))
  }

  test("add more than 4 vertices") {
    val pq = new MinPriorityQueue[Int](10)
    val vertices = Set(V(1,1), V(1,2), V(1,3), V(1,4), V(1,5))
    assert((pq ++= vertices).toSet === vertices)
  }

  test("do not add duplicate vertices") {
    val pq = new MinPriorityQueue[Int](10)
    val v1 = V(5,1)
    pq ++= Set(v1)
    pq ++= Set(v1)

    assert(pq.toSet === Set(v1))
  }

  test("delMin from empty list") {
    val pq = new MinPriorityQueue[Int](4)
    assert(pq.delMin() === None)
  }

  test("delMin until empty") {
    val pq = new MinPriorityQueue[Int](10)
    val v1 = V(5,1)
    val v2 = V(1,2)
    val v3 = V(4,5)
    val v4 = V(2,8)
    pq ++= Set(v1,v2,v3,v4)

    assert(pq.delMin() === Some(v2))
    assert(pq.delMin() === Some(v4))
    assert(pq.delMin() === Some(v3))
    assert(pq.delMin() === Some(v1))
    assert(pq.delMin() === None)
  }

  test("increase priority") {
    val pq = new MinPriorityQueue[Int](10)
    // pq: 1,2 -> 3,9 -> 4,5 -> 5,1
    pq ++= Set(V(5,1), V(1,2), V(4,5), V(3,9))
    pq.update(List(V(2,1)))

    val actual = List(pq.delMin(), pq.delMin())
    val expected = List(Some(V(1,2)), Some(V(2,1)))
    assert(actual === expected)
  }

  test("decrease priority") {
    val pq = new MinPriorityQueue[Int](10)
    // pq: 1,2 -> 3,8 -> 4,5 -> 5,1
    pq ++= Set(V(5,1), V(1,2), V(4,5), V(3,8))
    pq.update(Set(V(8,2)))

    val actual = (for (_ <- 1 to 4) yield pq.delMin()).map{case Some(x) => x}
    val expected = List(V(3,8), V(4,5), V(5,1), V(8,2))
    assert(actual === expected)
  }

}
