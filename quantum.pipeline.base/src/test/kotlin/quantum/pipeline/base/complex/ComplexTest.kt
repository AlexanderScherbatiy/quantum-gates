package quantum.pipeline.utils.base.complex

import org.junit.Test
import quantum.complex.*
import quantum.pipeline.utils.base.toCartesian
import quantum.pipeline.utils.base.toComplex
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin
import kotlin.test.assertEquals

class ComplexTest {

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

    @Test
    fun toCartesian() {
        assertEquals(CartesianComplex(0.0, 0.0), ZeroComplex.toCartesian())
        assertEquals(CartesianComplex(1.0, 0.0), OneComplex.toCartesian())

        assertEquals(CartesianComplex(2.0, 3.0), complex(2.0, 3.0).toCartesian())
        assertEquals(CartesianComplex(1.0, 0.0), PolarComplex(1.0, 0.0).toCartesian())
        assertEquals(CartesianComplex(1.0, 0.0), polar(1.0, 0.0).toCartesian())

        val r = 1.2
        val angle = PI / 3.0

        val x = r * cos(angle)
        val y = r * sin(angle)

        assertEquals(CartesianComplex(x, y), PolarComplex(r, angle).toCartesian())
        assertEquals(CartesianComplex(x, y), polar(r, angle).toCartesian())
    }

    @Test
    fun toCartesianWithZeros() {
        assertEquals(CartesianComplex(0.0, 0.0), complex(0.0, 0.0).toCartesian())
        //assertEquals(CartesianComplex(0.0, 0.0), complex(-0.0, 0.0).toCartesian())
        //assertEquals(CartesianComplex(0.0, 0.0), complex(0.0, -0.0).toCartesian())
        //assertEquals(CartesianComplex(0.0, 0.0), complex(-0.0, -0.0).toCartesian())
    }
}
