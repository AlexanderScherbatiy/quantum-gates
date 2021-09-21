package quantum.complex

interface Complex

object ZeroComplex : Complex

object OneComplex : Complex

data class PolarComplex(val radius: Double, val angle: Double = 0.0) : Complex

data class CartesianComplex(val real: Double, val imaginary: Double = 0.0) : Complex

fun polar(radius: Double, angle: Double = 0.0): Complex =
    PolarComplex(radius, angle)

fun complex(real: Double, imaginary: Double = 0.0): CartesianComplex =
    CartesianComplex(real, imaginary)
