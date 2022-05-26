package com.bignerdranch.android.mvvmsavedstate

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import kotlin.random.Random

class MainViewModel(savedStateHandle:SavedStateHandle):ViewModel() {

    private val _squares = MutableLiveData<Squares>()
    val squares:LiveData<Squares> = _squares

    init {
        generateSquares()
    }

    /*fun init(squares: Squares?){
        if(squares!=null){
            _squares.value = squares!!
        }
    }
    */

    fun generateSquares(){
        _squares.value = createSquare()
    }

    // min= 5 x 5, max = 10 x 10
    private fun createSquare():Squares{
        return Squares(
            size = Random.nextInt(5,11),
            colorProducer = { -Random.nextInt(0xFFFFFF) }
        )
    }

}