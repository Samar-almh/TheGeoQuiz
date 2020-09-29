package com.example.thegeoquiz

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: ImageButton
    private lateinit var prevButton: ImageButton
    private lateinit var questionTextView: TextView

    private val questionBank = listOf(
        Question(R.string.question_australia, true,2),
        Question(R.string.question_oceans, true,2),
        Question(R.string.question_mideast, false,2),
        Question(R.string.question_africa, false,2),
        Question(R.string.question_americas, true,2),
        Question(R.string.question_asia, true,2))
    private val TAG = "MainActivity"

    private var currentIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG ,"onCreate() called" + currentIndex )
        setContentView(R.layout.activity_main)
        trueButton = findViewById(R.id.t_Button)
        falseButton = findViewById(R.id.f_Button)
        nextButton = findViewById(R.id.next_Button)
        questionTextView = findViewById(R.id.question_text_view)
        prevButton= findViewById(R.id.prev_Button)
        updateQuestion()

        trueButton.setOnClickListener{
            checkAnswer(true)
        }

        falseButton.setOnClickListener{
            checkAnswer(false)
        }

        nextButton.setOnClickListener{
            currentIndex = (currentIndex + 1) % questionBank.size
            updateQuestion()
        }



        questionTextView.setOnClickListener{
            currentIndex = (currentIndex + 1) % questionBank.size
            updateQuestion()
        }



        prevButton.setOnClickListener{
            if (currentIndex==0){
                currentIndex =questionBank.size-1
            }else {
                currentIndex = (currentIndex - 1) % questionBank.size
            }
            updateQuestion()
        }


    }



    private fun updateQuestion() {
        val questionTextResId = questionBank[currentIndex].textResId
        question_text_view.setText(questionTextResId)

        if(questionBank[currentIndex].answered <= 1){
            trueButton?.isEnabled = false
            falseButton?.isEnabled = false
        }
        else{
            trueButton?.isEnabled = true
            falseButton?.isEnabled = true
        }

        if(showFinalScore() != 0){
            var scorePercent =Math.round((showFinalScore().toFloat() / questionBank.size.toFloat()) * 100.0f);
            Toast.makeText(this, "Your Final Score = " + showFinalScore() +"\n "
                    +scorePercent + "%", Toast.LENGTH_SHORT)
                .show()
        }


    }

    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = questionBank[currentIndex].answer
        val messageResId = if (userAnswer == correctAnswer) {
            questionBank[currentIndex].answered = 1
            R.string.correct_msg
        } else {
            questionBank[currentIndex].answered = 0
            R.string.inco_msg
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()

        trueButton?.isEnabled = false
        falseButton?.isEnabled = false

    }


    private fun showFinalScore() : Int
    {
        var mScore = 0;
        for ( n in questionBank )
        {
            if (n.answered==2) {
                mScore = 0;
                break
            }
            else {
                mScore+= n.answered
            }
        }
        return mScore ;
    }


    override fun onStart() {
        super.onStart()
        Log.d(TAG ,"onStart() called "  + currentIndex)
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG ,"onPause() called "  + currentIndex)
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(TAG ,"onRestart() called " + currentIndex )
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG ,"onResume() called " + currentIndex)
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG ,"onStop() called "  + currentIndex )
    }


    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG ,"onDestroy() called" )
    }
}