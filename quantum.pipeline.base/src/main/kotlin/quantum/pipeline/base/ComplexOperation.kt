package quantum.pipeline.base

import quantum.complex.Complex

interface ComplexOperation<C : Complex> {

    fun isSupported(complex: Complex): Boolean

    fun convert(complex: Complex): C

    fun add(complex1: C, complex2: C): C
}