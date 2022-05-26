package com.bignerdranch.android.mvvmsavedstate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.constraintlayout.widget.ConstraintLayout.LayoutParams
import androidx.core.view.setMargins
import com.bignerdranch.android.mvvmsavedstate.databinding.ActivityMainBinding
import kotlin.random.Random

const val KEY ="KET"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)


        //setContentView(R.layout.activity_main)
        setContentView(binding.root)

        //viewModel.init(savedInstanceState?.getParcelable(KEY))

        viewModel.squares.observe(this){
            renderSquares(it)
        }

        // Просим вью модель создать новый класс squares с новыми цветами,слушатель заметит это и вызовет рендер
        binding.generateColorsButton.setOnClickListener {
            viewModel.generateSquares()
        }

    }

    /*
    Сохраняем состояние вью модели,делаем класс squares parcelable,вытягиваем текущее состояние из вью модели и пиаем,потом в onCreate инициализируем вью модель
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(KEY,viewModel.squares.value)
    }
    */

    private fun renderSquares(squares: Squares) = with(binding){
        colorsContainer.removeAllViews()
        // Создаём массив id для вьюшек
        val identifiers = squares.colors.indices.map { View.generateViewId() }
        for (i in squares.colors.indices){
            // для каждой вьюшки определяем ряд 0 = (0,1,2,3,4) 1 = (5,6,7,8,9) 2 = (10,11,12,13,14) 3 = (15,16,17,18,19) 4 = (20,21,22,23,24)
            val row = i / squares.size
            // для каждой вьюшки определяем колонку 0,1,2,3,4 и по новой
            val column = i % squares.size

            // Создаём вьюшку
            val view = View(this@MainActivity)
            // Задаём ей цвет
            view.setBackgroundColor((squares.colors[i]))
            //Задаём ей id
            view.id = identifiers[i]

            // Задаём параметры(ширину,высоту,отступы)
            // ширина и высота равно 0,значит вьюшка заполнит всё доступное ей место учитывая другие вьюшки
            val params = LayoutParams(0, 0)
            params.setMargins(resources.getDimensionPixelOffset(R.dimen.space))
            view.layoutParams = params

            // Если это первый квадратик,то прикрепляем(constraint) его к родителю,если нет,то к предыдущему элементу
            if(column == 0){
                params.startToStart = LayoutParams.PARENT_ID
            } else{
                params.startToEnd = identifiers[i - 1]
            }

            // Если это последний квадратик,то прикрепляем(constraint) его к родителю,если нет,то к следующему элементу
            if(column == squares.size - 1){
                params.endToEnd = LayoutParams.PARENT_ID
            } else{
                params.endToStart = identifiers[i + 1]
            }

            // Если это первый ряд,то прикрепляем(constraint) его к родителю,если нет,то к предыдущему элементу 10-5=5,десятый элемент крепится к пятому
            // т.е последний элемент 2 строки крепится к последнему элементу первой строки
            if (row == 0){
                params.topToTop = LayoutParams.PARENT_ID
            } else{
                params.topToBottom = identifiers[i - squares.size]
            }


            // Если это последний ряд,то прикрепляем(constraint) его к родителю,если нет,то к следующему элементу 5+5=10,пятый элемент крепится к десятому
            // т.е последний элемент 1 строки крепится к последнему элементу второй строки
            if (row == squares.size - 1){
                params.bottomToBottom = LayoutParams.PARENT_ID
            } else{
                params.bottomToTop = identifiers[i + squares.size]
            }


            // Добавляем вью в контэйнер из кода
            colorsContainer.addView(view)
        }
    }
}