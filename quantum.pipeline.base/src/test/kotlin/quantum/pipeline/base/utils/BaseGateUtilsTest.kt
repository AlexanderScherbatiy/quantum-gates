package quantum.pipeline.base.utils

import org.junit.Test
import kotlin.test.assertEquals

import quantum.pipeline.utils.base.baseDimensionsToRange

class BaseGateUtilsTest {

    @Test
    fun baseDimensionsToRangeTest0() {
        assertEquals(0, baseDimensionsToRange(0).size)
    }

    @Test
    fun baseDimensionsToRangeTest1() {

        val range = baseDimensionsToRange(1)

        assertEquals(1, range.size)
        assertEquals(listOf(0), range[0].toList())
    }

    @Test
    fun baseDimensionsToRangeTest2() {

        val range = baseDimensionsToRange(2)

        for (indices in range) {
            println("indices size: ${range.size}: ${indices.joinToString()}")
        }

        assertEquals(2, range.size)
        assertEquals(listOf(0), range[0].toList())
        assertEquals(listOf(1), range[1].toList())
    }

    @Test
    fun baseDimensionsToRangeTestN() {

        val N = 20
        val range = baseDimensionsToRange(N)

        assertEquals(N, range.size)
        for (i in 0 until N) {
            assertEquals(listOf(i), range[i].toList())
        }
    }

    @Test
    fun baseDimensionsToRangeTestNM() {

        val N = 4
        val M = 5
        val range = baseDimensionsToRange(N, M)

        assertEquals(N * M, range.size)
        var index = 0
        for (j in 0 until M) {
            for (i in 0 until N) {
                assertEquals(listOf(i, j), range[index].toList())
                index++
            }
        }
    }
}