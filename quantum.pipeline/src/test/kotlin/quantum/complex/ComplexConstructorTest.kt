package quantum.complex

import org.junit.Test
import kotlin.test.assertEquals
import quantum.complex.Complex.Companion.complex

class ComplexConstructorTest {

    @Test
    fun testConstructor() {

        assertEquals(2.0, complex(2.0, 3.0).real)
        assertEquals(3.0, complex(2.0, 3.0).imaginary)

        assertEquals(complex(4.0, 0.0), complex(4.0))
        assertEquals(complex(4.0, 5.0), complex(4.0, 5.0))
        assertEquals(complex(2.0, 0.0), complex(2.0))
    }
}
