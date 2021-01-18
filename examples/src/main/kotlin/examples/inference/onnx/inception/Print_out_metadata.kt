/*
 * Copyright 2020 JetBrains s.r.o. and Kotlin Deep Learning project contributors. All Rights Reserved.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE.txt file.
 */

package examples.inference.onnx.inception

import org.jetbrains.kotlinx.dl.api.inference.onnx.OnnxModel

private const val PATH_TO_MODEL = "api/src/main/resources/models/onnx/inception-v1-9.onnx"

fun main() {
    val model = OnnxModel.load(PATH_TO_MODEL)

    println(model.toString())
}