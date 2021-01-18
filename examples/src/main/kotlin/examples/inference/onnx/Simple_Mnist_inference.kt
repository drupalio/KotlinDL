/*
 * Copyright 2020 JetBrains s.r.o. and Kotlin Deep Learning project contributors. All Rights Reserved.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE.txt file.
 */

package examples.inference.onnx

import org.jetbrains.kotlinx.dl.api.core.metric.Metrics
import org.jetbrains.kotlinx.dl.api.inference.onnx.OnnxModel
import org.jetbrains.kotlinx.dl.datasets.Dataset
import org.jetbrains.kotlinx.dl.datasets.handlers.*

private const val PATH_TO_MODEL = "api/src/main/resources/models/onnx/mnist-8.onnx"

fun main() {
    val (train, test) = Dataset.createTrainAndTestDatasets(
        TRAIN_IMAGES_ARCHIVE,
        TRAIN_LABELS_ARCHIVE,
        TEST_IMAGES_ARCHIVE,
        TEST_LABELS_ARCHIVE,
        NUMBER_OF_CLASSES,
        ::extractImages,
        ::extractLabels
    )

    OnnxModel.load(PATH_TO_MODEL).use {
        println(it)

        it.reshape(::reshapeInput)

        val prediction = it.predict(train.getX(0))

        println("Predicted Label is: $prediction")
        println("Correct Label is: " + train.getLabel(0))

        val predictions = it.predictAll(test)
        println(predictions.toString())

        println("Accuracy is : ${it.evaluate(test, Metrics.ACCURACY)}")
    }
}

fun reshapeInput(inputData: FloatArray): Array<Array<Array<FloatArray>>> {
    val reshaped = Array(1) {
        Array(1)
        { Array(28) { FloatArray(28) } }
    }

    for (i in inputData.indices) reshaped[0][0][i / 28][i % 28] = inputData[i]
    return reshaped
}