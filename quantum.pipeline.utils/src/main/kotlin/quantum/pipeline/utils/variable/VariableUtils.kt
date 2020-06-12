package quantum.pipeline.utils.variable

import java.lang.RuntimeException

fun <T> variableValue(componentName: String, variableName: String, map: Map<String, T>): T =
        map.getOrElse(variableName) {
            throw RuntimeException("$componentName variable '$variableName' is not found: $map")
        }
