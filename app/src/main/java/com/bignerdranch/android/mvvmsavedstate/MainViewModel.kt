package com.bignerdranch.android.mvvmsavedstate

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import kotlin.random.Random

class MainViewModel(savedStateHandle:SavedStateHandle):ViewModel() {

    // Вытягиваем значения из бандла,по ключу,а если не получилось то инициализируем случайными значениями
    // Вот так легко сохраняется состояние вью модели без вмешательства в мэйн активти и saveInstanceState
    private val _squares = savedStateHandle.getLiveData("squares",createSquare())
    val squares:LiveData<Squares> = _squares

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

// Эта функция нужна для первой версиии восстановления состояния
/*fun init(squares: Squares?){
    if(squares!=null){
        _squares.value = squares!!
    }
}
*/