package quantum.pipeline.base

import quantum.complex.Complex
import java.util.*

interface ComplexOperation<C : Complex> {

    fun convert(complex: Complex): C?

    fun isZero(complex: C, delta: Double): Boolean

    fun sum(complex1: C, complex2: C): C
}