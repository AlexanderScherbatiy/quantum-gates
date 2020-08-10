package quantum.value

interface Value

data class ConstantValue(val n: Int)

data class VariableValue(val name: String)

fun constant(n: Int): ConstantValue = ConstantValue(n)

fun variable(name: String): VariableValue = VariableValue(name)