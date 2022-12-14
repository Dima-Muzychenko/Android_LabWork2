package com.example.test

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.test.databinding.ActivityParametersBinding
import kotlin.properties.Delegates

class ParametersActivity : AppCompatActivity() {

    lateinit var binding:ActivityParametersBinding
    lateinit var adapter1: ArrayAdapter<SizeOfMatrix>
    lateinit var adapter2: ArrayAdapter<Timer>
    lateinit var adapter3: ArrayAdapter<RangeOfTime>
    var SIZE_OF_MATRIX: Int = 3
    var TIMER: Int = 15
    var MIN_FREQUENCY: Float = 1f
    var MAX_FREQUENCY: Float = 1.5f
    val DEFAULT_VALUE:Int = 10000
    var geinedPoints:Int = DEFAULT_VALUE


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parameters)
        binding = ActivityParametersBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpSizeSpinnerWithAdapter()
        setUpTimerWithAdapter()
        setUpRangeWithAdapter()

        //викликаємо ShowInfoAboutCell (у нас onItemSelectedListener спрацьовує навіть одразу при викликанні
        //сторінки налаштувань, бо в Spinner ми одразу відображаемо 1 значення)
        binding.matrixSize.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                adapter1.getItem(position)?.let { ShowInfoAboutCell(it) }//let-перевірка на null
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {//у нас завжди щось вибрано
            }
        }

        binding.timer.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                TIMER = adapter2.getItem(position)?.meaning?:throw java.lang.IllegalArgumentException("meaning is null")
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {//у нас завжди щось вибрано
            }
        }

        binding.speedRange.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                MIN_FREQUENCY = adapter3.getItem(position)?.min?:throw java.lang.IllegalArgumentException("meaning is null")
                MAX_FREQUENCY = adapter3.getItem(position)?.max?:throw java.lang.IllegalArgumentException("meaning is null")
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {//у нас завжди щось вибрано
            }
        }

        binding.confirmButton.setOnClickListener{onConfirmButtonClicked()}
        binding.history.setOnClickListener { watchistory() }
    }

    private fun watchistory() {
        val intent = Intent(this, ForWatchingHistoryActivity::class.java)
        startActivity(intent)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==1 && resultCode==Activity.RESULT_OK){
            geinedPoints=data?.getIntExtra(GameActivity.GEINED_POINTS,DEFAULT_VALUE)?:
            throw IllegalArgumentException("can not get data from GameActivity")
        }
    }

    private fun onConfirmButtonClicked(){
        val intent = Intent(this, GameActivity::class.java)
        intent.putExtra(GameActivity.size_of_matrix,SIZE_OF_MATRIX)//передаємо дані типу ключ-значення в
        intent.putExtra(GameActivity.timer,TIMER)//intent. Тобто передаємо в іншу Activity
        intent.putExtra(GameActivity.min_frequency,MIN_FREQUENCY)
        intent.putExtra(GameActivity.max_frequency,MAX_FREQUENCY)
        startActivityForResult(intent,1)
    }

    //тестовий метод, щоб перевірити, чи можна з Spinner витягнути всю інформацію про елемент,
    //а не тільки текст який в ньому записаний
    private fun ShowInfoAboutCell(size: SizeOfMatrix) {
        var meaning = size.meaning
        var text = size.name
        println("meaning=$meaning,text=$text")
        SIZE_OF_MATRIX=meaning
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
        binding.matrixSize.adapter=adapter1 //призначаємо adapter1 до нашого sellSize Spinner
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

    class Timer(val name: String, val meaning: Int){
        override fun toString(): String {
            return name
        }
    }

    class RangeOfTime(val name: String, val min: Float, val max: Float){
        override fun toString(): String {
            return name
        }
    }

}