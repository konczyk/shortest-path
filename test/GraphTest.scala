import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class GraphTest extends FunSuite {

  test("compare edges") {
    val e1 = Edge(5,1)
    val e2 = Edge(2,3)
    val e3 = Edge(2,2)

    assert((e1 > e2) === true)
    assert((e3 < e1) === true)
  }

  test("build graph") {
    val g = "1 2,3 4,5; 3 3,4"
    val graph = Graph(g.split(";").toIterator, _.toInt)

    assert(graph.edges(1) === Some(Set(Edge(2,3), Edge(4,5))))
    assert(graph.edges(3) === Some(Set(Edge(3,4))))
    assert(graph.edges(2) === None)
  }

  test("graph size") {
    val g = "1 2,3 4,5; 3 3,4"
    val graph = Graph(g.split(";").toIterator, _.toInt)

    assert(graph.size === 4)
  }

}
