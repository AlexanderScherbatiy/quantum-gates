package quantum.pipeline.utils.base.complex

import org.junit.Test
import quantum.complex.CartesianComplex
import quantum.complex.Complex
import quantum.complex.OneComplex
import quantum.complex.ZeroComplex
import quantum.pipeline.utils.base.toComplex
import kotlin.test.assertEquals

class ToComplexTest {

    @Test
    fun intToComplex() {

        assertEquals(ZeroComplex, 0.toComplex())
        assertEquals(ZeroComplex, (-0).toComplex())

        assertEquals(OneComplex, 1.toComplex())

        assertEquals(CartesianComplex(2.0), 2.toComplex())
        assertEquals(CartesianComplex(-2.0), (-2).toComplex())
    }

    @Test
    fun doubleToComplex() {

        assertEquals(ZeroComplex, 0.0.toComplex())
        assertEquals(ZeroComplex, (-0.0).toComplex())

        assertEquals(OneComplex, 1.0.toComplex())

        assertEquals(CartesianComplex(2.0), 2.0.toComplex())
        assertEquals(CartesianComplex(-2.0), (-2.0).toComplex())
    }
}
