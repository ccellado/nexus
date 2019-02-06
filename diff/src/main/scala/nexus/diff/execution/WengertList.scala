package nexus.diff.execution

import nexus.diff.exception._
import nexus.diff._
import nexus._

import scala.collection._

/**
 * A table for storing values/gradients.
 * It is essentially a map from symbolic expressions to their actual values.
 * @author Tongfei Chen
 * @since 0.1.0
 */
class WengertList(override val rawMap: ReversibleLinkedHashMap[Symbolic[_], Id[_]] = ReversibleLinkedHashMap[Symbolic[_], Id[_]]())
  extends BoxMap[Symbolic, Id](rawMap) with Iterable[Assignment] {

  def increment[X](e: Symbolic[X], v: X): Unit = e.tag.ev match {
    case gX: Grad[X] => // if differentiable
      if (contains(e)) {
        if (gX.mutable)
          gX.addInplace(this(e), v)
        else this(e) = gX.add(this(e), v)
      }
      else update(e, v)
    case _ => throw new ExpressionNotDifferentiableException(e)
  }

  private def asAssignment(p: (Symbolic[_], Id[_])): Assignment = p match {
    case (e: Symbolic[t], v) => new Assignment {
      type Data = t
      val expr = e
      val value = v.asInstanceOf[t]
    }
  }

  override def iterator: Iterator[Assignment] = rawMap.iterator.map(asAssignment)

  def reverseIterator: Iterator[Assignment] = rawMap.reverseIterator.map(asAssignment)

  def reverse: Iterable[Assignment] = new AbstractIterable[Assignment] {
    def iterator = reverseIterator
  }

}

object WengertList {

  def apply(as: Assignment*) = {
    val m = new WengertList
    for (a <- as)
      m(a.expr) = a.value
    m
  }

}
