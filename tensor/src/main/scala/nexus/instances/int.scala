package nexus.instances

import nexus._
import cats._

object ByteIsInt extends IsInt[Byte] with GetInt[Byte] {
  def add(x: Byte, y: Byte) = (x + y).toByte
  def neg(x: Byte) = (-x).toByte
  def sub(x: Byte, y: Byte) = (x - y).toByte
  def mul(x: Byte, y: Byte) = (x * y).toByte
  def zero = 0.toByte
  def one = 1.toByte
  def two = 2.toByte
  def negOne = (-1).toByte
  def div(x: Byte, y: Byte) = (x / y).toByte
  def mod(x: Byte, y: Byte) = (x % y).toByte
  def lt(x: Byte, y: Byte) = x < y
  def eq(x: Byte, y: Byte) = x == y
  def ne(x: Byte, y: Byte) = x != y
  def le(x: Byte, y: Byte) = x <= y
  def fromLong(x: Long) = x.toByte
  def toInt(x: Byte) = x.toInt
  def toLong(x: Byte) = x.toLong
}

object ShortIsInt extends IsInt[Short] with GetInt[Short] {
  def add(x: Short, y: Short) = (x + y).toShort
  def neg(x: Short) = (-x).toShort
  def sub(x: Short, y: Short) = (x - y).toShort
  def mul(x: Short, y: Short) = (x * y).toShort
  def zero = 0.toShort
  def one = 1.toShort
  def two = 2.toShort
  def negOne = (-1).toShort
  def div(x: Short, y: Short) = (x / y).toShort
  def mod(x: Short, y: Short) = (x % y).toShort
  def lt(x: Short, y: Short) = x < y
  def eq(x: Short, y: Short) = x == y
  def ne(x: Short, y: Short) = x != y
  def le(x: Short, y: Short) = x <= y
  def fromLong(x: Long) = x.toShort
  def toInt(x: Short) = x.toInt
  def toLong(x: Short) = x.toLong
}

object IntIsInt extends IsInt[Int] with GetInt[Int] {
  def add(x: Int, y: Int) = x + y
  def neg(x: Int) = -x
  def sub(x: Int, y: Int) = x - y
  def mul(x: Int, y: Int) = x * y
  def zero = 0
  def one = 1
  def two = 2
  def negOne = -1
  def div(x: Int, y: Int) = x / y
  def mod(x: Int, y: Int) = x % y
  def lt(x: Int, y: Int) = x < y
  def eq(x: Int, y: Int) = x == y
  def ne(x: Int, y: Int) = x != y
  def le(x: Int, y: Int) = x <= y
  def fromLong(x: Long) = x.toInt
  def toInt(x: Int) = x
  def toLong(x: Int) = x.toLong
}

object LongIsInt extends IsInt[Long] with GetInt[Long] {
  def add(x: Long, y: Long) = x + y
  def neg(x: Long) = -x
  def sub(x: Long, y: Long) = x - y
  def mul(x: Long, y: Long) = x * y
  def zero = 0l
  def one = 1l
  def two = 2l
  def negOne = -1l
  def div(x: Long, y: Long) = x / y
  def mod(x: Long, y: Long) = x % y
  def lt(x: Long, y: Long) = x < y
  def eq(x: Long, y: Long) = x == y
  def ne(x: Long, y: Long) = x != y
  def le(x: Long, y: Long) = x <= y
  def fromLong(x: Long) = x
  override def toInt(x: Long) = x.toInt
  override def toLong(x: Long) = x
}
