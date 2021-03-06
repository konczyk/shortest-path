/**
  * Priority queue implementation with a heap, with modifiable priority
  */
import scala.collection.mutable

class MinPriorityQueue[A](size: Int) extends Iterable[V[A]] {

  private var n = 0
  // map vertices to tuple of current score and pq index)
  private val vmap = new mutable.HashMap[A, (Double, Int)]()
  private val pq = new Array[V[A]](size+1)

  def ++=(xs: TraversableOnce[V[A]]): this.type = {
    val from = n + 1
    for (x <- xs.filter(v => !vmap.contains(v.name))) add(x)
    heapify(from)
    this
  }

  private def add(v: V[A]): Unit = {
    n += 1
    pq(n) = v
    vmap(v.name) = (v.dist, n)
  }

  private def heapify(from: Int): Unit = {
    if (n-from <= 4) {
      for (i <- from to n) swim(i)
    } else {
      def loop(ks: Seq[Int]): Unit = {
        if (ks.nonEmpty) {
          val sunk = ks.foldLeft(List[Int]()) {
            (acc, k) => if (sink(k) && k > 1) k :: acc else acc
          }
          loop(sunk.map(_ / 2).distinct)
        }
      }
      loop((n to from by -1).map(_ / 2).filter(_ > 0).distinct)
    }
  }

  def delMin(): Option[V[A]] = {
    if (n == 0) None
    else {
      val min = pq(1)
      swap(1, n)
      vmap -= min.name
      pq(n) = null
      n -= 1
      sink(1)
      Some(min)
    }
  }

  def update(vs: TraversableOnce[V[A]]): this.type = {
    // update existing vertices if new scores are better
    vs.filter(vmap isDefinedAt _.name).foreach{
      case V(name, newDist, prev) =>
        val (oldDist, idx) = vmap(name)
        if (newDist < oldDist) {
          pq(idx) = V(name, newDist, prev)
          vmap(name) = (newDist, idx)
          swim(idx)
        }
      }
    // add new vertices
    this ++= vs
  }

  // maintain the heap invariant by fixing the nodes >= k up the heap
  private def swim(k: Int): Unit = {
    def loop(k: Int, p: Int): Unit = {
      if (k > 1 && pq(k) < pq(p)) {
        swap(k, p)
        loop(p, p/2)
      }
    }
    loop(k, k/2)
  }

  // maintain the heap invariant by fixing the nodes <= k down the heap
  // return true if any swap has been made
  private def sink(k: Int): Boolean = {
    def loop(m: Int): Boolean = {
      if (2*m <= n) {
        val i = 2*m
        val j= if (i < n && pq(i) > pq(i+1)) i+1 else i
        if (pq(m) > pq(j)) {
          swap(m, j)
          loop(j)
        }
      }
      k != m
    }
    loop(k)
  }

  private def swap(n: Int, m: Int): Unit = {
    val tmp = pq(n)
    pq(n) = pq(m)
    pq(m) = tmp
    vmap += (pq(n).name -> (pq(n).dist, n),
             pq(m).name -> (pq(m).dist, m))
  }

  // returns items from the heap in arbitrary order
  override def iterator: Iterator[V[A]] = new Iterator[V[A]] {
    private var i = 1
    def hasNext: Boolean = i <= n
    def next(): V[A] = {
      val e = pq(i)
      i += 1
      e
    }
  }

}
