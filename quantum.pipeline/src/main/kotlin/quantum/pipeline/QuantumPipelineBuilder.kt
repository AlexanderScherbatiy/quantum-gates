package quantum.pipeline

import quantum.gate.QuantumGate
import quantum.state.QuantumState
import java.lang.RuntimeException
import java.util.*

class QuantumPipelineBuilder {

    private var factory: QuantumPipelineFactory
    private lateinit var state: QuantumState
    private val gates = mutableListOf<QuantumGate>()

    init {
        val loader = ServiceLoader.load(QuantumPipelineFactory::class.java)
        val factories = loader.iterator()
        if (factories.hasNext()) {
            factory = factories.next()
        } else {
            throw RuntimeException("QuantumPipelineFactory is not provided.")
        }
    }

    fun begin() = StateBuilder()

    inner class StateBuilder {
        fun state(state: QuantumState): GateBuilder {
            this@QuantumPipelineBuilder.state = state
            return GateBuilder()
        }
    }

    inner class GateBuilder {
        fun gate(gate: QuantumGate): GateBuilder {
            this@QuantumPipelineBuilder.gates.add(gate)
            return GateBuilder()
        }

        fun end(): QuantumPipeline = factory.getPipeline(state, gates)
    }
}
