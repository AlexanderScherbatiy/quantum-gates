package quantum.pipeline.sample

import quantum.bit.*
import quantum.gate.ControlledFunctionGate
import quantum.gate.HadamardGate
import quantum.gate.tensor
import quantum.pipeline.QuantumPipelineFactory
import quantum.state.OneQubit
import quantum.state.ZeroQubit
import quantum.state.tensor
import java.util.*

fun main() {

    println("QuantumPipeline sample with constant and balanced functions");

    val f1 = function(listOf("x")) { ZeroBit }
    val f2 = function(listOf("x")) { VariableBit("x") }
    val f3 = function(listOf("x")) { VariableBit("x").not() }
    val f4 = function(listOf("x")) { OneBit }

    runDeutschJozsaTask(f1)
    runDeutschJozsaTask(f2)
    runDeutschJozsaTask(f3)
    runDeutschJozsaTask(f4)
}

fun runDeutschJozsaTask(f: BitFunctionWithParameters) {

    val resultQuantumState = getQuantumPipelineFactory()
        .getPipeline()
        .assembly(
            ZeroQubit tensor OneQubit,
            HadamardGate tensor HadamardGate,
            ControlledFunctionGate(4, VariableBitFunction(1, "f")),
            variables = mapOf("f" to f)
        )
        .evaluate()
        .output

    println("result quantum state: $resultQuantumState")
}

fun getQuantumPipelineFactory(): QuantumPipelineFactory {

    val loader = ServiceLoader.load(QuantumPipelineFactory::class.java)
    val iterator = loader.iterator()

    while (iterator.hasNext()) {
        return iterator.next();
    }

    throw Exception("No registered QuantumPipeline found!")
}