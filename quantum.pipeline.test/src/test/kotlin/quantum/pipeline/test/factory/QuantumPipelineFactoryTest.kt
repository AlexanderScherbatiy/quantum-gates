package quantum.pipeline.test.factory

import org.junit.Test
import quantum.pipeline.test.utils.registeredFactories
import kotlin.test.assertTrue

class QuantumPipelineFactoryTest {

    val expectedPipelineNames = listOf("memory")

    @Test
    fun checkRegisteredFactoriesNonEmpty() {
        assertTrue(registeredFactories().isNotEmpty())
    }

    @Test
    fun checkBaseRegisteredFactories() {

        val pipelineNames = registeredFactories().map { it.name }
        for (pipelineName in expectedPipelineNames) {
            assertTrue(pipelineNames.contains(pipelineName),
                    "Factory $pipelineName is missed!")
        }
    }
}