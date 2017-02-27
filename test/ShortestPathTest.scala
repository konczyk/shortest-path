import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class ShortestPathTest extends FunSuite {

  test("find") {
    val g = """
      | 1 2,3 3,4 4,2
      | 2 1,1 3,2 5,1
      | 3 2,3 4,2
      | 4 3,1 5,3""".stripMargin
    val graph = Graph(g.trim.lines, _.toInt)
    val sp = new ShortestPath[Int](graph)

    assert(sp.find(1) === Map(
      2 -> (3,Vector(2)), 3 -> (3,Vector(4,3)),
      4 -> (2,Vector(4)), 5 -> (4,Vector(2,5))))

    assert(sp.find(2) === Map(
      1 -> (1,Vector(1)), 3 -> (2,Vector(3)),
      4 -> (3,Vector(1,4)), 5 -> (1,Vector(5))))

    assert(sp.find(3) === Map(
      1 -> (4,Vector(2,1)), 2 -> (3,Vector(2)),
      4 -> (2,Vector(4)), 5 -> (4,Vector(2,5))))

    assert(sp.find(4) === Map(
      1 -> (5,Vector(3,2,1)), 2 -> (4,Vector(3,2)),
      3 -> (1,Vector(3)), 5 -> (3,Vector(5))))

    assert(sp.find(5) === Map.empty)
  }

}
