package quantum.state

interface QuantumGate

data class IdentityGate(val size: Int) : QuantumGate

data class VariableGate(val name: String, val size: Int) : QuantumGate
