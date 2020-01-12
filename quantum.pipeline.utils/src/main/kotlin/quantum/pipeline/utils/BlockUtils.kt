package quantum.pipeline.utils

import java.lang.RuntimeException

fun <T> blockValue(blockName: String, variableName: String, map: Map<String, T>): T =
        map.getOrElse(variableName) {
            throw RuntimeException("$blockName value '$variableName' is not provided.")
        }
