package nexus

import algebra.ring._

import scala.annotation._

/**
 * Encapsulates mathematical operations on integers.
 *
 * @author Tongfei Chen
 * @since 0.1.0
 */
@implicitNotFound("Cannot prove that type ${Z} is an integer.")
trait IsInt[@specialized(Byte, Short, Int, Long) Z] extends Ring[Z] {

  def zero: Z
  def one: Z
  def two: Z
  def negOne: Z

  def add(x: Z, y: Z): Z
  def sub(x: Z, y: Z): Z
  def neg(x: Z): Z
  def mul(x: Z, y: Z): Z
  def div(x: Z, y: Z): Z
  def mod(x: Z, y: Z): Z

  def fromLong(x: Long): Z

  // Conforming to algebra.ring.Ring
  def plus(x: Z, y: Z) = add(x, y)
  def negate(x: Z) = neg(x)
  def times(x: Z, y: Z) = mul(x, y)
}

@implicitNotFound("Cannot get integer value out of boxed type ${Z}.")
trait GetInt[@specialized(Byte, Short, Int, Long) Z] {

  def toInt(x: Z): Int
  def toLong(x: Z): Long

}
