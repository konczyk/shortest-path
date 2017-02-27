import org.rogach.scallop._

class Conf(arguments: Seq[String]) extends ScallopConf(arguments) {
  banner("Usage: Client [options]")
  val src = opt[Int](name = "source", short = 's', required = true,
    descr = "Source vertex")
  val dst = opt[List[Int]](name = "destination", short = 'd',
    descr = "Space separated list of destination vertices (Default: all)")
  val help = opt[Boolean](name = "help", short = 'h', descr = "Usage help")
  verify()
}

object Client extends App {

  val conf = new Conf(args)
  val graph = Graph(scala.io.Source.stdin.getLines, _.toInt)
  val paths = new ShortestPath[Int](graph).find(conf.src())
  val dst: Set[Int] = conf.dst.toOption match {
    case None => Set()
    case Some(lst) => lst.toSet
  }

  paths
    .filterKeys(k => dst.isEmpty || dst.contains(k))
    .toList.sortBy(_._1)
    .foreach{
      case(t, (len, path)) =>
        val len2 = s"($len)"
        println(f"$t%-4s $len2%-6s ${path.mkString(" -> ")}")
    }

}
