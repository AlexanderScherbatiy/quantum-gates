package quantum.pipeline.base.complex

import kotlin.math.cos
import kotlin.math.sin
import kotlin.test.assertTrue
import org.junit.Test
import org.junit.Assert

import quantum.complex.*
import quantum.pipeline.base.ComplexOperation
import quantum.pipeline.base.BaseCartesianComplexOperation

class BaseCartesianComplexOperationTest {

    val DELTA = 0.001

    @Test
    fun isSupportedTest() {

        val op: ComplexOperation<CartesianComplex> = BaseCartesianComplexOperation()
        assertTrue { op.isSupported(ZeroComplex) }
        assertTrue { op.isSupported(OneComplex) }
        assertTrue { op.isSupported(complex(2.3, 4.5)) }
        assertTrue { op.isSupported(polar(2.3, 4.5)) }
    }

    @Test
    fun convertTest() {

        val op: ComplexOperation<CartesianComplex> = BaseCartesianComplexOperation()

        assertComplexEquals(complex(0.0, 0.0), op.convert(ZeroComplex))
        assertComplexEquals(complex(1.0, 0.0), op.convert(OneComplex))

        assertComplexEquals(complex(0.1, 0.2), op.convert(complex(0.1, 0.2)))
        assertComplexEquals(complex(4.1, 5.2), op.convert(complex(4.1, 5.2)))

        val r = 3.4
        var angle = 0.15

        assertComplexEquals(
            complex(r * cos(angle), r * sin(angle)),
            op.convert(polar(r, angle))
        )
    }


    fun assertComplexEquals(c1: CartesianComplex, c2: CartesianComplex) {
        Assert.assertEquals(c1.real, c2.real, DELTA)
        Assert.assertEquals(c1.imaginary, c2.imaginary, DELTA)
    }
}