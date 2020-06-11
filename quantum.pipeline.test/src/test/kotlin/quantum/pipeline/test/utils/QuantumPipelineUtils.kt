package quantum.pipeline.test.utils

import quantum.pipeline.QuantumPipelineFactory
import java.util.*

fun registeredFactories(): List<QuantumPipelineFactory> {

    val factories = mutableListOf<QuantumPipelineFactory>()
    val loader = ServiceLoader.load(QuantumPipelineFactory::class.java)
    val iterator = loader.iterator()

    while (iterator.hasNext()) {
        val factory = iterator.next();
        factories.add(factory)
    }

    return factories
}