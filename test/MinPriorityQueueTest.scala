import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class MinPriorityQueueTest extends FunSuite {

  test("add vertices to trigger swim") {
    val pq = new MinPriorityQueue[Int](10)
    assert(pq.toSet === Set())
    assert((pq ++= List(V(1,2))).toSet === Set(V(1,2)))
  }

  test("add vertices to trigger sink") {
    val pq = new MinPriorityQueue[Int](10)
    pq ++= Set(V(1,9), V(2,8), V(3,3), V(4,10), V(5,1), V(6,4))

    val actual = (for (_ <- 1 to 6) yield pq.delMin()).map{case Some(x) => x}
    val expected = List(V(5,1), V(3,3), V(6,4), V(2,8), V(1,9),  V(4,10))
    assert(actual === expected)
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
    val v1 = V(1,5)
    val v2 = V(2,1)
    val v3 = V(5,4)
    val v4 = V(8,2)
    pq ++= Set(v1,v2,v3,v4)

    assert(pq.delMin() === Some(v2))
    assert(pq.delMin() === Some(v4))
    assert(pq.delMin() === Some(v3))
    assert(pq.delMin() === Some(v1))
    assert(pq.delMin() === None)
  }

  test("increase priority") {
    val pq = new MinPriorityQueue[Int](10)
    // pq: 2,1 -> 9,3 -> 5,4 -> 1,5
    pq ++= Set(V(1,5), V(2,1), V(5,4), V(9,3))
    pq.update(List(V(1,2)))

    val actual = List(pq.delMin(), pq.delMin())
    val expected = List(Some(V(2,1)), Some(V(1,2)))
    assert(actual === expected)
  }

  test("do not decrease priority") {
    val pq = new MinPriorityQueue[Int](10)
    // pq: 2,1 -> 8,3 -> 5,4 -> 1,5
    val vs = Set(V(1,5), V(2,1), V(5,4), V(8,3))
    pq ++= vs
    pq.update(Set(V(2,8)))

    val actual = (for (_ <- 1 to 4) yield pq.delMin()).map{case Some(x) => x}
    val expected = List(V(2,1), V(8,3), V(5,4), V(1,5))
    assert(actual === expected)
  }

}
