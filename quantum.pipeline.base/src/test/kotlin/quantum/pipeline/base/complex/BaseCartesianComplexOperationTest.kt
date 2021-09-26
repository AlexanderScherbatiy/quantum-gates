package quantum.pipeline.base.complex

import kotlin.math.cos
import kotlin.math.sin
import kotlin.test.assertTrue
import kotlin.test.assertFalse
import org.junit.Test
import org.junit.Assert

import quantum.complex.*
import quantum.pipeline.base.ComplexOperation
import quantum.pipeline.base.BaseCartesianComplexOperation

class BaseCartesianComplexOperationTest {

    val DELTA = 0.001

    @Test
    fun convertTest() {

        val op: ComplexOperation<CartesianComplex> = BaseCartesianComplexOperation()

        assertComplexEquals(complex(0.0, 0.0), op.convert(ZeroComplex)!!)
        assertComplexEquals(complex(1.0, 0.0), op.convert(OneComplex)!!)

        assertComplexEquals(complex(0.1, 0.2), op.convert(complex(0.1, 0.2))!!)
        assertComplexEquals(complex(4.1, 5.2), op.convert(complex(4.1, 5.2))!!)

        val r = 3.4
        var angle = 0.15

        assertComplexEquals(
            complex(r * cos(angle), r * sin(angle)),
            op.convert(polar(r, angle))!!
        )
    }

    @Test
    fun sumTest() {

        val op: ComplexOperation<CartesianComplex> = BaseCartesianComplexOperation()

        assertComplexEquals(
            complex(4.4, 6.6), op.sum(
                CartesianComplex(1.1, 2.2),
                CartesianComplex(3.3, 4.4)
            )
        )
    }

    @Test
    fun testIsZero() {

        val op: ComplexOperation<CartesianComplex> = BaseCartesianComplexOperation()

        assertTrue { op.isZero(CartesianComplex(0.0, 0.0), 0.001) }

        assertTrue { op.isZero(CartesianComplex(0.01, 0.01), 0.1) }
        assertTrue { op.isZero(CartesianComplex(-0.01, 0.01), 0.1) }
        assertTrue { op.isZero(CartesianComplex(0.02, -0.03), 0.1) }
        assertTrue { op.isZero(CartesianComplex(-0.04, -0.04), 0.1) }

        assertFalse { op.isZero(CartesianComplex(0.01, 0.01), 0.001) }
        assertFalse { op.isZero(CartesianComplex(-0.04, 0.05), 0.001) }
        assertFalse { op.isZero(CartesianComplex(0.06, -0.07), 0.001) }
        assertFalse { op.isZero(CartesianComplex(-0.08, -0.09), 0.001) }
    }

    fun assertComplexEquals(c1: CartesianComplex, c2: CartesianComplex) {
        Assert.assertEquals(c1.real, c2.real, DELTA)
        Assert.assertEquals(c1.imaginary, c2.imaginary, DELTA)
    }
}