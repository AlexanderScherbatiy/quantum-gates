package quantum.pipeline.utils.base

import quantum.complex.CartesianComplex
import quantum.complex.OneComplex
import quantum.complex.ZeroComplex

fun Int.toComplex() = when (this) {
    0 -> ZeroComplex
    1 -> OneComplex
    else -> toDouble().toComplex()
}

fun Double.toComplex() = when (this) {
    0.0 -> ZeroComplex
    1.0 -> OneComplex
    else -> CartesianComplex(this)
}
