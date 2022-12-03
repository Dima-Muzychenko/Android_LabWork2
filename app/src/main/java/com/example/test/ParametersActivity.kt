package com.example.test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.test.databinding.ActivityParametersBinding

class ParametersActivity : AppCompatActivity() {

    lateinit var binding:ActivityParametersBinding
    lateinit var adapter1: ArrayAdapter<SizeOfMatrix>
    lateinit var adapter2: ArrayAdapter<Timer>
    lateinit var adapter3: ArrayAdapter<RangeOfTime>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parameters)
        binding = ActivityParametersBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpSizeSpinnerWithAdapter()
        setUpTimerWithAdapter()
        setUpRangeWithAdapter()

        //викликаємо ShowInfoAboutCell
        binding.sellSize.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                adapter1.getItem(position)?.let { ShowInfoAboutCell(it) }//let-перевірка на null
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
    }


    //метод, щоб перевірити, чи можна з Spinner витягнути всю інформацію про елемент,
    //а не тільки текст який в ньому записаний
    private fun ShowInfoAboutCell(size: SizeOfMatrix) {
        var meaning = size.meaning
        var text = size.name
        println("meaning=$meaning,text=$text")
    }

    private fun setUpSizeSpinnerWithAdapter() {
        val data: List<SizeOfMatrix> = listOf(
            SizeOfMatrix(name = "3x3", meaning = 3),
            SizeOfMatrix(name = "4x4", meaning = 4),
            SizeOfMatrix(name = "5x5", meaning = 5)
        )
        adapter1 = ArrayAdapter(this,
            android.R.layout.simple_list_item_1,
            data)
        binding.sellSize.adapter=adapter1 //призначаємо adapter1 до нашого sellSize Spinner
    }

    private fun setUpTimerWithAdapter() {
        val data: List<Timer> = listOf(
            Timer(name = "15 seconds", meaning = 15),
            Timer(name = "30 seconds", meaning = 30),
            Timer(name = "45 seconds", meaning = 45)
        )
        adapter2 = ArrayAdapter(this,
            android.R.layout.simple_list_item_1,
            data)
        binding.timer.adapter=adapter2 //призначаємо adapter1 до нашого sellSize Spinner
    }

    private fun setUpRangeWithAdapter() {
        val data: List<RangeOfTime> = listOf(
            RangeOfTime(name = "1-1.5 seconds", min = 1f, max = 1.5f),
            RangeOfTime(name = "0.7-1 seconds", min = 0.7f, max = 1f),
            RangeOfTime(name = "0.3-0.6 seconds", min = 0.3f, max = 0.6f)
        )
        adapter3 = ArrayAdapter(this,
            android.R.layout.simple_list_item_1,
            data)
        binding.speedRange.adapter=adapter3 //призначаємо adapter1 до нашого sellSize Spinner
    }

    class SizeOfMatrix(val name:String, val meaning: Int){
        override fun toString(): String {
            return name
        }
    }

    class Timer(private val name: String, val meaning: Int){
        override fun toString(): String {
            return name
        }
    }

    class RangeOfTime(private val name: String, val min: Float, val max: Float){
        override fun toString(): String {
            return name
        }
    }

}