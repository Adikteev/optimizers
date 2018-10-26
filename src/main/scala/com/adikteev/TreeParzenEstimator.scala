package com.adikteev
import breeze.linalg._
import breeze.numerics.{round, _}
import breeze.stats.distributions.{Gaussian, Multinomial, RandBasis, ThreadLocalRandomGenerator}
import org.apache.commons.math3.random.MersenneTwister

object TreeParzenEstimator {
  // Sample from truncated 1-D Gaussian Mixture Model
  def GMM1(weights: Array[Double],
           mus: Array[Double],
           sigmas: Array[Double],
           low: Option[Float] = None,
           high: Option[Float] = None,
           q: Option[Double] = None,
           rng: Option[Float] = None,
           size: List[Int] = List()): DenseMatrix[Double] = {
    // Assert weights.size == mus.size == sigmas.size
    //weights, mus, sigmas = list(map(np.asarray, (weights, mus, sigmas)))
    // Vector product over sizes
    val nSamples = product(size)
    // n_components = len(weights)
    val samples : IndexedSeq[Double] = (low, high) match {
      case (None, None) => {
        val multinomial : IndexedSeq[Int] = Multinomial(DenseVector(weights: _*)).sample(nSamples)
        val active = argmax(DenseVector(multinomial: _*))
        val gaussian = Gaussian(mus(active), sigmas(active))
        gaussian.sample(1)
      }
      case (Some(x), Some(y)) =>
        if (x >= y) {
          throw new RuntimeException(s"x >= high $x, high")
        }
        IndexedSeq(0.0)
    }

//    val reshaped : DenseMatrix[Double] = reshape(DenseVector(samples: _*), size.head)
//    q.fold(reshaped) { rq =>
//      val rounded : DenseMatrix[Long] = round(reshaped /:/ rq)
//      val dm: DenseMatrix[Double] = rounded.*(rq)
//      dm
//    }
    DenseMatrix(DenseVector(0.0))
  }

  implicit val rander = new RandBasis(new ThreadLocalRandomGenerator(new MersenneTwister(123)))
  def GMM11(weights: Array[Double],
           mus: Array[Double],
           sigmas: Array[Double],
           low: Option[Float] = None,
           high: Option[Float] = None,
           q: Option[Double] = None,
           rng: Option[Float] = None,
           size: List[Int] = List()) = {
    // Assert weights.size == mus.size == sigmas.size
    // Vector product over sizes
    val nSamples = product(size)
    // n_components = len(weights)
    val samples : IndexedSeq[Double] = (low, high) match {
      case (None, None) => {
        val mult = Multinomial[DenseVector[Double], Int](DenseVector(weights))
        val accumNaive = DenseVector.zeros[Double](nSamples)
        (0 until nSamples).foreach { i =>
          accumNaive(mult.draw()) += 1
        }
        val active = argmax(accumNaive)
        println("ACCUM NAIVE", s"$accumNaive")
        println("ACTIVE", s"$active")
        val gaussian = Gaussian(mus(active), sigmas(active))
        gaussian.sample(1)
      }
      case (Some(x), Some(y)) =>
        if (x >= y) {
          throw new RuntimeException(s"x >= high $x, high")
        }
        IndexedSeq(0.0)
    }
    samples
  }
}

