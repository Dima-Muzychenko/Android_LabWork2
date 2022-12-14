package com.example.test

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.*
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.setMargins
import com.example.test.databinding.ActivityGameBinding
import java.util.concurrent.TimeUnit
import kotlin.properties.Delegates.notNull
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
    lateinit var countDownTimerPrepareToGame:CountDownTimer
    var iscountDownTimerActive:Boolean=false
    var iscountDownTimer2Active:Boolean=false
    var iscountDownTimerPrepareToGameActive:Boolean=false

    //for savedInstanceState
    var countDownTimerPrepareToGameAllTime:Long=3000
    var countDown by notNull<Long>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        SIZE_OF_MATRIX = intent.getIntExtra(size_of_matrix, 0)
        TIMER = intent.getIntExtra(timer, 0)
        MIN_FREQUENCY = intent.getFloatExtra(min_frequency, 0f)
        MAX_FREQUENCY = intent.getFloatExtra(max_frequency, 0f)
        println("size_of_matrix=$SIZE_OF_MATRIX, timer=$TIMER, frequency=$MIN_FREQUENCY, $MAX_FREQUENCY")
        countDown=(TIMER*1000).toLong()
        if(savedInstanceState!=null){
            countDownTimerPrepareToGameAllTime=savedInstanceState.getLong(PREPARE_TIMER)
            countDown=savedInstanceState.getLong(COUNT_DOWN)
            counter=savedInstanceState.getInt(COUNTER)
            iscountDownTimerPrepareToGameActive=savedInstanceState.getBoolean(IS_BEGGINING_COUNTER)
            iscountDownTimerActive=savedInstanceState.getBoolean(IS_FIRST_COUNTER)
            iscountDownTimer2Active=savedInstanceState.getBoolean(IS_SECOND_COUNTER)


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

        binding.end.setOnClickListener { onStop()
            finish() }
    }

    override fun onStart() {
        super.onStart()
        CountDown()
        //якщо під час countDownTimerPrepareToGameAllTime затримки був викликаний метод onStop(),
        //то ми не виконуємо те, що в handler. Як зупинути нормально сам handler незрозуміло
        handler.postDelayed({ if(iscountDownTimerPrepareToGameActive==true){changeCardsAndTimer()} },
            countDownTimerPrepareToGameAllTime)
    }

    private fun CountDown() {
        binding.tablelayout.visibility= View.GONE
        countDownTimerPrepareToGame = object: CountDownTimer(countDownTimerPrepareToGameAllTime,1000) {
            override fun onTick(millisUntilFinished: Long) {
                iscountDownTimerPrepareToGameActive=true
                var seconds = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished)
                countDownTimerPrepareToGameAllTime=millisUntilFinished//
                binding.timer.setText("$seconds")
                Log.v("countDownTimer 1 = ", countDownTimerPrepareToGameAllTime.toString())
            }

            override fun onFinish() {
                countDownTimerPrepareToGameAllTime=0
                binding.timer.setText("0")
                binding.tablelayout.visibility= View.VISIBLE
            }
        }.start()

    }

    override fun onStop() {
        super.onStop()
        if(iscountDownTimerPrepareToGameActive==true){
            countDownTimerPrepareToGame.cancel()
            iscountDownTimerPrepareToGameActive=false
        }
        if(iscountDownTimerActive==true){//якщо таймери активні, то закриваємо їх. Інакше при поворотах
            countDownTimer.cancel()//екрану у нас будуть працювати і старі і нові таймери
            iscountDownTimerActive=false
        }
        if(iscountDownTimer2Active==true){
            countDownTimer2.cancel()
            iscountDownTimer2Active=false
        }
    }

    private fun changeCardsAndTimer() {
        //таймер для кнопок
        countDownTimer = object: CountDownTimer(countDown+100, (Random.nextDouble(MIN_FREQUENCY.toDouble(), MAX_FREQUENCY.toDouble())*1000).toLong()) {
            override fun onTick(millisUntilFinished: Long) {
                iscountDownTimerActive=true
                setRandomColored(TheButtonId)
            }

            override fun onFinish() {
                cancel()
            }
        }.start()

        //таймер для таймера
        countDownTimer2= object: CountDownTimer(countDown, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                iscountDownTimer2Active=true
                countDown=millisUntilFinished
                var seconds = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished)
                binding.timer.setText("$seconds")
            }

            override fun onFinish() {
                countDown=0
                binding.timer.setText("0")
                dialog()
                cancel()
            }
        }.start()
//                updateViews()
    }

    fun dialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Game is overed")
        builder.setMessage("Your score is $counter")
        builder.setPositiveButton(android.R.string.yes) { dialog, which ->
            //результати передаємо в службу
            var intent:Intent = Intent(this, SaveIntoFileService::class.java)
            intent.setAction(SaveIntoFileService.ACTION_WRITE_TO_FILE)
            intent.putExtra(SaveIntoFileService.POINTS, counter)//передаємо кількість отриманих балів
            startService(intent)

            finish()
        }
        sendDataToParameters()
        builder.show()
    }

    private fun sendDataToParameters() {//передаємо отриманий результат назад в ParametersActivity
        val intent=Intent()
        intent.putExtra(GEINED_POINTS,counter)
        setResult(Activity.RESULT_OK,intent)
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
        //for saveInstantState
        private val PREPARE_TIMER="P_TIMER"
        private val COUNT_DOWN = "C_D"
//        private val TIMER_BUTTONS="T_B"
        private val COUNTER="COUNTER"
        private val IS_BEGGINING_COUNTER="IS_BEGGINING_COUNTER"
        private val IS_FIRST_COUNTER="IS_FIRST_COUNTER"
        private val IS_SECOND_COUNTER="IS_SECOND_COUNTER"
        val GEINED_POINTS="GEINED_POINTS"
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.v("countDownTimer 2 = ", countDownTimerPrepareToGameAllTime.toString())
        outState.putLong(PREPARE_TIMER,countDownTimerPrepareToGameAllTime)
        outState.putLong(COUNT_DOWN, countDown)
        outState.putInt(COUNTER, counter)
        outState.putBoolean(IS_BEGGINING_COUNTER, iscountDownTimerPrepareToGameActive)
        outState.putBoolean(IS_FIRST_COUNTER, iscountDownTimerActive)
        outState.putBoolean(IS_SECOND_COUNTER, iscountDownTimer2Active)
    }


}