package nexus.layer.recurrent

import nexus._
import nexus.algebra._
import nexus.algebra.typelevel._
import nexus.layer._
import nexus.op._
import nexus.util._

/**
 * Elman recurrent unit.
 * @author Tongfei Chen
 */
class ElmanUnit[T[_], R, X: Label, S: Label, Y: Label] private(
  val inputLayer: Affine[T, R, X, S],
  val outputLayer: Affine[T, R, S, Y],
  val stateActivation: Func1[T[S], T[S]],
  val outputActivation: Func1[T[Y], T[Y]],
  val inputAxis: X,
  val stateAxis: S,
  val outputAxis: Y
)
(implicit T: IsRealTensorK[T, R])
  extends RecurrentUnitWithOutput[T[S], T[X], T[Y]]
{
  def apply(s: Expr[T[S]], x: Expr[T[X]]) = {

    val sʹ =
      ((s |> Rename(stateAxis -> inputAxis)), x) |> Concat(inputAxis) |> inputLayer |> stateActivation

    val y = sʹ |> outputLayer |> outputActivation

    (sʹ, y) |> Tuple2
  }
}

object ElmanUnit {

  /**
   * Constructs an Elman recurrent unit -- the simplest recurrent unit.
   * @example {{{
   *   ElmanUnit(Input -> 200, State -> 300, Output -> 200, Tanh, Softmax)
   * }}}
   * @param inputAxisAndSize
   * @param stateAxisAndSize
   * @param outputAxisAndSize
   * @param stateActivation
   * @param outputActivation
   * @tparam T
   * @tparam R
   * @tparam X
   * @tparam S
   * @tparam Y
   * @return
   */
  def apply[T[_], R, X: Label, S: Label, Y: Label](
                                   inputAxisAndSize: (X, Int),
                                   stateAxisAndSize: (S, Int),
                                   outputAxisAndSize: (Y, Int),
                                   stateActivation: PolyFunc1,
                                   outputActivation: PolyFunc1,
                                   name: String = ExprName.nextId("ElmanUnit")
                                   )
                                   (implicit
                                    T: IsRealTensorK[T, R],
                                    saf: stateActivation.F[T[S], T[S]],
                                    oaf: outputActivation.F[T[Y], T[Y]]
                                   ) = {
    val (inputAxis, inputSize) = inputAxisAndSize
    val (stateAxis, stateSize) = stateAxisAndSize
    val (outputAxis, outputSize) = outputAxisAndSize
    val inputLayer = Affine(inputAxis -> (inputSize + stateSize), stateAxis -> stateSize, name = s"$name.Input")
    val outputLayer = Affine(stateAxis -> stateSize, outputAxis -> outputSize, name = s"$name.Output")
    new ElmanUnit[T, R, X, S, Y](
      inputLayer,
      outputLayer,
      stateActivation.ground(saf),
      outputActivation.ground(oaf),
      inputAxis,
      stateAxis,
      outputAxis
    )
  }

}