package quantum.pipeline.test.factory

import org.junit.Test
import quantum.pipeline.test.utils.registeredFactories
import kotlin.test.assertTrue

class QuantumPipelineFactoryTest {

    val basePipelineNames = listOf("base")

    @Test
    fun checkRegisteredFactoriesNonEmpty() {
        assertTrue(registeredFactories().isNotEmpty())
    }

    @Test
    fun checkBaseRegisteredFactories() {

        val pipelineNames = registeredFactories().map { it.name }
        for (baseName in basePipelineNames) {
            assertTrue(pipelineNames.contains(baseName),
                    "Factory $baseName is missed!")
        }
    }
}