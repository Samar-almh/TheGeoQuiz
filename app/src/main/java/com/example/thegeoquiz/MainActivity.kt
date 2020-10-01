package com.example.thegeoquiz

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_main.*

private const val REQUEST_CODE_CHEAT =0
class MainActivity : AppCompatActivity() {
private  var cheatNumber=0
    private val quizViewModel: QuizViewModel by lazy {
        ViewModelProviders.of(this).get(QuizViewModel::class.java)
    }
    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var cheatButton: Button
    private lateinit var nextButton: ImageButton
    private lateinit var prevButton: ImageButton
    private lateinit var questionTextView: TextView
    private lateinit var resultTextView:TextView



    private val TAG = "MainActivity"

    @SuppressLint("RestrictedApi")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG ,"onCreate() called" + quizViewModel)


        setContentView(R.layout.activity_main)
        trueButton = findViewById(R.id.t_Button)
        falseButton = findViewById(R.id.f_Button)
        cheatButton = findViewById(R.id.cheat_button)
        nextButton = findViewById(R.id.next_Button)
        questionTextView = findViewById(R.id.question_text_view)
        prevButton= findViewById(R.id.prev_Button)
        resultTextView=findViewById(R.id.result)

        updateQuestion()

        trueButton.setOnClickListener{
            checkAnswer(true)
        }

        falseButton.setOnClickListener{
            checkAnswer(false)
        }

        nextButton.setOnClickListener{
            quizViewModel.moveToNext()
            updateQuestion()
        }

        cheatButton.setOnClickListener {
            cheatNumber+=1
            val answerIsTrue = quizViewModel.currentQuestionAnswer

            val intent = CheatActivity.newIntent(this@MainActivity, answerIsTrue)
            startActivityForResult(intent, REQUEST_CODE_CHEAT)

            if (cheatNumber==3){
                cheatButton.isEnabled=false
            }
            cheatNumber=0
        }

         questionTextView.setOnClickListener{
             quizViewModel.moveToNext()
            updateQuestion()
        }

        prevButton.setOnClickListener{
            quizViewModel.moveToBack()
            updateQuestion()
        }

        updateQuestion()
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode!=Activity.RESULT_OK){return}
        if(requestCode== REQUEST_CODE_CHEAT)
        {
            quizViewModel.isCheater=data?.getBooleanExtra(EXTRA_ANSWER_SHOWN,false)?:false

        }
    }

    private fun updateQuestion() {
        val questionTextResId = quizViewModel.currentQuestionText
        question_text_view.setText(questionTextResId)

        if(quizViewModel.questionBank[quizViewModel.currentIndex].answered <= 1){
            trueButton?.isEnabled = false
            falseButton?.isEnabled = false
        }
        else{
            trueButton?.isEnabled = true
            falseButton?.isEnabled = true
        }




    }

    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer =  quizViewModel.currentQuestionAnswer
        val messageResId = when {
            quizViewModel.isCheater -> R.string.judgment_toast
            userAnswer == correctAnswer -> R.string.correct_msg
            else -> R.string.inco_msg
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_LONG).show()

        var add=isCheater(messageResId)

        quizViewModel.grade+=add

        resultTextView.setText(quizViewModel.text+quizViewModel.getGrade)
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()
        quizViewModel.isCheater=false
        if(quizViewModel.numberOfAnsweredQuestions==quizViewModel.size) {

            var scorePercent =Math.round(((quizViewModel.getGrade.toFloat() / quizViewModel.size.toFloat()) * 100.0f)/4.0f);

            val toast= Toast.makeText(this, "Your Final Score = ${quizViewModel.getGrade}"+"\n"+scorePercent+"%", Toast.LENGTH_LONG)
            toast.setGravity(Gravity.CENTER,0,0)
            toast.show()

        }

        trueButton?.isEnabled = false
        falseButton?.isEnabled = false

    }




    fun isCheater(message:Int):Int
    {
        var res=0
        if(message!=R.string.judgment_toast&&message!=R.string.inco_msg)
                res = when{

                    quizViewModel.kindOfQuestions=="easy"->2
                    quizViewModel.kindOfQuestions=="medial"->4
                    else ->6


                }
        return res

    }


    override fun onStart() {
        super.onStart()
        Log.d(TAG ,"onStart() called "  + quizViewModel.currentIndex)
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG ,"onPause() called "  +quizViewModel.currentIndex)
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(TAG ,"onRestart() called " + quizViewModel.currentIndex )
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG ,"onResume() called " + quizViewModel.currentIndex)
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG ,"onStop() called "  + quizViewModel.currentIndex )
    }


    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG ,"onDestroy() called" )
    }
}