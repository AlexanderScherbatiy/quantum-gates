package quantum.complex

interface Complex

object ZeroComplex : Complex

object OneComplex : Complex

data class CartesianComplex(val real: Double, val imaginary: Double = 0.0) : Complex
