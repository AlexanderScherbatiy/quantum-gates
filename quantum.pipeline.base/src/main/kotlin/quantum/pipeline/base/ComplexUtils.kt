package quantum.pipeline.utils.base

import quantum.complex.*
import java.lang.RuntimeException
import kotlin.math.cos
import kotlin.math.sin

fun Int.toComplex() = when (this) {
    0 -> ZeroComplex
    1 -> OneComplex
    else -> toDouble().toComplex()
}

fun Double.toComplex() = when (this) {
    0.0 -> ZeroComplex
    1.0 -> OneComplex
    else -> complex(this)
}

fun Complex.toCartesian(): CartesianComplex = when (this) {
    ZeroComplex -> CartesianComplex(0.0)
    OneComplex -> CartesianComplex(1.0)
    is PolarComplex -> {
        val r = this.radius
        val angle = this.angle
        CartesianComplex(r * cos(angle), r * sin(angle))
    }
    is CartesianComplex -> this
    else -> throw RuntimeException("Unknown complex: $this")
}