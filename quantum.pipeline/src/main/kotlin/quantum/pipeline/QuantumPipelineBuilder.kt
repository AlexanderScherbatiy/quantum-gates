package quantum.pipeline

import quantum.gate.QuantumGate
import quantum.state.QuantumState
import java.lang.RuntimeException
import java.util.*

class QuantumPipelineBuilder {

    private var factoryIsInitialized = false
    private lateinit var factory: QuantumPipelineFactory
    private lateinit var state: QuantumState
    private val gates = mutableListOf<QuantumGate>()

    fun factory(factory: QuantumPipelineFactory): QuantumPipelineBuilder {
        this.factory = factory
        factoryIsInitialized = true
        return this
    }

    fun begin(): StateBuilder {
        if (!factoryIsInitialized) {
            val loader = ServiceLoader.load(QuantumPipelineFactory::class.java)
            val factories = loader.iterator()
            if (factories.hasNext()) {
                factory = factories.next()
            } else {
                throw RuntimeException("QuantumPipelineFactory is not provided.")
            }
            factoryIsInitialized = true
        }

        return StateBuilder()
    }

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
