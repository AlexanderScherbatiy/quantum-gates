package quantum.complex

fun Int.toComplex() = this.toDouble().toComplex()
fun Double.toComplex() = Complex.complex(this, 0.0)

data class Complex private constructor(val real: Double, val imaginary: Double) {

    companion object {
        val Zero = Complex(0.0, 0.0)
        val One = Complex(1.0, 0.0)
        val I = Complex(0.0, 1.0)
        fun complex(real: Double, imaginary: Double = 0.0) = Complex(real, imaginary)
    }

    override fun toString() = "Complex($real, $imaginary)"
}
