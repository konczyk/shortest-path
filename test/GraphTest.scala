import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class GraphTest extends FunSuite {

  test("compare edges") {
    val e1 = V(5,1)
    val e2 = V(2,3)
    val e3 = V(2,2)

    assert((e1 > e2) === true)
    assert((e3 < e1) === true)
  }

  test("build graph") {
    val g = "1 2,3 4,5; 3 3,4"
    val graph = Graph(g.split(";").toIterator, _.toInt)

    assert(graph.neighbors(1) === Set(V(2,3), V(4,5)))
    assert(graph.neighbors(3) === Set(V(3,4)))
    assert(graph.neighbors(2) === Set())
  }

  test("graph size") {
    val g = "1 2,3 4,5; 3 3,4"
    val graph = Graph(g.split(";").toIterator, _.toInt)

    assert(graph.size === 4)
  }

}
