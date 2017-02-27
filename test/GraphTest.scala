import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class GraphTest extends FunSuite {

  test("compare nodes") {
    val v1 = V(1,5,0)
    val v2 = V(3,2,0)
    val v3 = V(4,2,0)

    assert((v1 > v2) === true)
    assert((v3 < v1) === true)
  }

  test("build graph") {
    val g = "1 3,2 5,4; 3 4,3"
    val graph = Graph(g.split(";").toIterator, _.toInt)

    assert(graph.neighbors(1) === Set(E(3,2), E(5,4)))
    assert(graph.neighbors(3) === Set(E(4,3)))
    assert(graph.neighbors(2) === Set())
  }

  test("graph size") {
    val g = "1 3,2 5,4; 3 4,3"
    val graph = Graph(g.split(";").toIterator, _.toInt)

    assert(graph.size === 4)
  }

}
