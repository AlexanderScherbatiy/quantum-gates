package quantum.pipeline.base

import quantum.complex.*
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sin

class BaseCartesianComplexOperation : ComplexOperation<CartesianComplex> {

    override fun isSupported(complex: Complex) =
        complex == ZeroComplex
                || complex == OneComplex
                || complex is CartesianComplex
                || complex is PolarComplex

    override fun convert(complex: Complex): CartesianComplex = when (complex) {
        ZeroComplex -> complex(0.0, 0.0)
        OneComplex -> complex(1.0, 0.0)
        is CartesianComplex -> complex
        is PolarComplex -> complex(complex.radius * cos(complex.angle), complex.radius * sin(complex.angle))
        else -> throw Exception("Unable to convert complex $complex")
    }

    override fun isZero(complex: CartesianComplex, delta: Double): Boolean =
        abs(complex.real) < delta && abs(complex.imaginary) < delta


    override fun sum(complex1: CartesianComplex, complex2: CartesianComplex): CartesianComplex =
        CartesianComplex(complex1.real + complex2.real, complex1.imaginary + complex2.imaginary)
}