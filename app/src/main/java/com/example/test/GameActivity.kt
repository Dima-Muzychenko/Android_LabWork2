package com.example.test

import android.graphics.Color
import android.os.*
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.setMargins
import com.example.test.databinding.ActivityGameBinding
import java.util.concurrent.TimeUnit
import kotlin.random.Random


class GameActivity : AppCompatActivity() {
    lateinit var binding:ActivityGameBinding
    var SIZE_OF_MATRIX: Int = 3
    var TIMER: Int=15
    var MIN_FREQUENCY: Float = 1f
    var MAX_FREQUENCY: Float = 1.5f
    var handler: Handler = Handler(Looper.getMainLooper())
    var buttonsList:MutableList<Button> = mutableListOf()
    var counter: Int=0
    var TheButtonId: Int=-1//id (місце в масиві) зеленої кнопки
    lateinit var countDownTimer:CountDownTimer
    lateinit var countDownTimer2:CountDownTimer




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        SIZE_OF_MATRIX = intent.getIntExtra(size_of_matrix, 0)
        TIMER = intent.getIntExtra(timer, 0)
        MIN_FREQUENCY = intent.getFloatExtra(min_frequency, 0f)
        MAX_FREQUENCY = intent.getFloatExtra(max_frequency, 0f)
//        println("size_of_matrix=$SIZE_OF_MATRIX, timer=$TIMER, frequency=$MIN_FREQUENCY, $MAX_FREQUENCY")
        if(savedInstanceState!=null){

        }
        var id = 0; //здається навіть id не потрібен
        for (i in SIZE_OF_MATRIX downTo 1 step 1) {
            val tableRowParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
            )

            /* create a table row */
            val tableRow = TableRow(this)
            tableRow.layoutParams = tableRowParams
            for (j in SIZE_OF_MATRIX downTo 1 step 1) {

                /* create cell element - button */
                val btn = Button(this)
                btn.setBackgroundColor(Color.GRAY)

                /* set params for cell elements */
                //P.S. Тільки так можна setMargins використати
                val cellParams = TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT)
                //те, на скільки за межі нашого LinearLayout можна виходити. якщо не вказати цей
                cellParams.weight=0.1f//параметр, то при великій кількості кнопок вони будуть вилізати за екран
                cellParams.setMargins(5)
                btn.layoutParams = cellParams
                btn.id=id
                id++
                btn.setOnClickListener { countPoints(btn) }

                //додаємо кнопку в список, щоб з нею взаємодіяти
                buttonsList.add(btn)
                /* add views to the row */
                tableRow.addView(btn)
            }
            // add Button to TableLayout
            binding.tablelayout.addView(tableRow)
        }
        setRandomColored(0)
        //просто для наглядності. Потім змінити
        binding.end.setOnClickListener {setRandomColored(TheButtonId) }

    }

    override fun onStart() {
        super.onStart()
        CountDown()
        handler.postDelayed({changeCardsAndTimer()},3200)

    }

    private fun CountDown() {
        binding.tablelayout.visibility= View.GONE
        var countDownTimer:CountDownTimer = object: CountDownTimer(3000,1000) {
            override fun onTick(millisUntilFinished: Long) {
                var seconds = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished)
                binding.timer.setText("$seconds")
            }

            override fun onFinish() {
                binding.timer.setText("0")
                binding.tablelayout.visibility= View.VISIBLE
            }
        }
        countDownTimer.start()

    }

    override fun onStop() {
        super.onStop()
        countDownTimer.cancel()//закриваємо таймери, щоб на бекграунді не висіли
        countDownTimer2.cancel()
    }

    private fun changeCardsAndTimer() {
        //таймер для кнопок
        countDownTimer = object: CountDownTimer((TIMER*1000).toLong(), (Random.nextDouble(MIN_FREQUENCY.toDouble(), MAX_FREQUENCY.toDouble())*1000).toLong()) {
            override fun onTick(millisUntilFinished: Long) {
                setRandomColored(TheButtonId)
            }

            override fun onFinish() {

            }
        }

        //таймер для таймера
        countDownTimer2= object: CountDownTimer((TIMER*1000).toLong(), 1000) {
            override fun onTick(millisUntilFinished: Long) {
                var seconds = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished)
                binding.timer.setText("$seconds")
            }

            override fun onFinish() {
                binding.timer.setText("0")
                dialog()
            }
        }
        countDownTimer.start()
        countDownTimer2.start()
//                updateViews()
    }

    fun dialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Game is overed")
        builder.setMessage("Your score is $counter")
        builder.setPositiveButton(android.R.string.yes) { dialog, which ->
            finish()
        }
        builder.show()
    }

    private fun countPoints(btn: Button) {
        if (btn.id==TheButtonId){
            counter+=2
        }else{counter--}
        println(counter)
    }

    private fun setRandomColored(i: Int): Int {
        buttonsList[i].setBackgroundColor(Color.GRAY)//кнопка, що до цього була зелена тепер знову сіра
        var r = Random.nextInt(SIZE_OF_MATRIX*SIZE_OF_MATRIX)//рандомне невід'ємне число, менше заданого
        buttonsList[r].setBackgroundColor(Color.GREEN)//рандомно 1 кнопку буде синя
        TheButtonId=r//помічаємо, яка кнопка у нас виділена зеленим
        return r//повертаємо

    }

    companion object{//константи
        val size_of_matrix: String = "size_of_matrix"
        val timer: String = "timer"
        val min_frequency: String = "min_frequency"
        val max_frequency: String = "max_frequency"
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
//        outState.put
//        outState.put
    }

}