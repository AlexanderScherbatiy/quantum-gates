package quantum.pipeline.test.utils

import quantum.complex.Complex
import quantum.pipeline.utils.base.toCartesian
import kotlin.test.assertEquals

fun assertComplexEquals(expected: Complex, actual: Complex) {
    assertEquals(expected.toCartesian(), actual.toCartesian())
}
