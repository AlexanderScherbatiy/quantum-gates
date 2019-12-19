package quantum.state

sealed class QuantumGate

data class IdentityGate(val size: Int) : QuantumGate()
