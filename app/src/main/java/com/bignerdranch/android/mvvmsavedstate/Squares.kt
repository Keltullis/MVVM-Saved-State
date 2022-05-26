package com.bignerdranch.android.mvvmsavedstate

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class Squares private constructor(
    val size :Int,
    val colors: List<Int>
):Parcelable{

    constructor(
        size: Int,
        colorProducer:() -> Int
    ):this(
        size = size,
        colors = (1..size*size).map{colorProducer()}
    )
    // От 1 до 25,получим 25 цветов в листе
}