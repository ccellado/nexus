package nexus.diff.modules

import nexus.diff._
import nexus.diff.ops._
import nexus._
import nexus.diff.util._

/**
 * A fully-connected neural layer (i.e., affine transformation).
 * Variously known as `Dense` or `FullyConnected` in other libraries.
 * @author Tongfei Chen
 * @since 0.1.0
 */
class Affine[T[_], R, X <: Dim, Y <: Dim] private(
                                                   val W: Param[T[(Y, X)]],
                                                   val b: Param[T[Y]]
                                                 )
                                                 (implicit T: IsRealTensorK[T, R])
  extends Module1[T[X], T[Y]]
{

  def parameters = Set(W, b)

  /** The linear transformation matrix of this layer. */
  def weight = W

  /** The additive bias of this layer. */
  def bias = b

  def apply[D[_]: Algebra](x: D[T[X]]): D[T[Y]] =
    Add(MVMul(W.as[D], x), b.as[D])

}


object Affine {

  /**
   * Constructs an affine (fully-connected) layer with customized parameters.
   * @param W Weight matrix (axes `(Y, X)`)
   * @param b Bias vector (axes `Y`)
   */
  def from[T[_], R, X <: Dim, Y <: Dim]
  (W: Param[T[(Y, X)]], b: Param[T[Y]])(implicit T: IsRealTensorK[T, R]) = new Affine[T, R, X, Y](W, b)

  /**
   * Constructs an affine (fully-connected) layer with default parameters.
   * @example `Affine(In -> 784, Out -> 300)`
   */
  def apply[T[_], R, X <: Dim, Y <: Dim](
                                 inAxisAndSize: (X, Int),
                                 outAxisAndSize: (Y, Int),
                                 name: String = sourcecode.Name.generate.value,
                               )(implicit dType: RealDType.Aux[R, T]): Affine[T, R, X, Y] =
  {
    implicit val T = IsRealTensorK.fromDType[T, R]
    val (_, nX) = inAxisAndSize
    val (_, nY) = outAxisAndSize
    val W = Param(T.newGaussianTensor[(Y, X)](0f, 1f, Array(nY, nX)), name = s"$name.weight")
    val b = Param(T.newGaussianTensor[Y](0f, 1f, Array(nY)), name = s"$name.bias")
    from[T, R, X, Y](W, b)
  }

}
