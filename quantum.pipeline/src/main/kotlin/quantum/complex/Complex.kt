package quantum.complex

data class Complex private constructor(val real: Double, val imaginary: Double) {

    companion object {
        fun complex(real: Double, imaginary: Double = 0.0) = Complex(real, imaginary)
    }
}
