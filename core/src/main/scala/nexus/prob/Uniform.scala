package nexus.prob

import nexus._
import nexus.algebra._
import nexus.algebra.syntax._
import java.util._

/**
 * Represents the uniform distribution Uniform(l, r).
 * @author Tongfei Chen
 */
trait Uniform[R] extends HasPdf[R, R] {
  def left: R
  def right: R
}

object Uniform {

  class Impl[E[_], R](l: E[R], r: E[R])(implicit R: IsGenReal[E, R]) extends Uniform[E[R]] {

    import R._
    private[this] val standardUniform = new Random(GlobalSettings.seed)
    private[this] val d = r - l

    def left = l
    def right = r
    def pdf(x: E[R]) = ???
    def logPdf(x: E[R]) = ???
    def sample = {
      val u = standardUniform.nextDouble()
      add(l, mul(fromDouble(u), d))
    }

  }

  def standard[R](implicit R: IsReal[R]) = apply(R.zero, R.one)

  def apply[E[_], R](l: E[R], r: E[R])(implicit R: IsGenReal[E, R]): Uniform[E[R]] = new Impl(l, r)

}
