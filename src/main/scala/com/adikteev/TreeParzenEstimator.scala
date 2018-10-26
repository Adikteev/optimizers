package com.adikteev

object TreeParzenEstimator {
  // Sample from truncated 1-D Gaussian Mixture Model
  def GMM1(weights: List[Double],
           mus: List[Double],
           sigmas: List[Double],
           low: Option[Float] = None,
           high: Option[Float] = None,
           q: Option[Float] = None,
           rng: Option[Float] = None,
           size: List[Int] = List()) = {
    // Assert weights.size == mus.size == sigmas.size
    // Vector product over sizes
//    nSamples = np.prod(size)
    // n_components = len(weights)
//    (low, high) match {
//      case (None, None) =>
//      case (Some(x), Some(y)) => {
//        if (x >= high) {
//          throw new RuntimeException(s"x >= high $x, high")
//        }
//      }
//    }
  }
}



//weights, mus, sigmas = list(map(np.asarray, (weights, mus, sigmas)))