/**
  * Priority queue implementation with a heap, with modifiable priority
  */
import scala.collection.mutable

class MinPriorityQueue[A](size: Int) extends Iterable[V[A]] {

  private var n = 0
  // map vertices to tuple of current score and pq index)
  private val vmap = new mutable.HashMap[A,(Int, Int)]()
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
    vmap(v.name) = (v.score, n)
  }

  private def heapify(from: Int): Unit = {
    if (n-from <= 4) {
      for (i <- from to n) swim(i)
    } else {
      def loop(ks: Seq[Int]): Unit = {
        if (ks.nonEmpty) {
          val sunk = ks.foldLeft(List[Int]()) {
            (acc, k) => if (sink(k)) k :: acc else acc
          }
          loop(sunk.filter(_ % 2 == 0).map(_ / 2))
        }
      }
      loop(from to n)
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

  def update(xs: TraversableOnce[V[A]]): this.type = {
    xs.foreach{case V(newScore, name) =>
      val (oldScore, idx) = vmap(name)
      if (oldScore != newScore) {
        pq(idx) = V(newScore, name)
        vmap(name) = (newScore, idx)
        if (newScore < oldScore) swim(idx) else sink(idx)
      }
    }
    this
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
    vmap += (pq(n).name -> (pq(n).score, n),
             pq(m).name -> (pq(m).score, m))
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
