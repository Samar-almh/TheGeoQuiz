package com.example.thegeoquiz

import androidx.lifecycle.ViewModel

class QuizViewModel : ViewModel()  {

    var currentIndex = 0
    private var numAnswer=0
    var isCheater = false
    var grade=0
    var text="Your Current Score = "


    val easyQuestion = listOf(
        Question(R.string.question_australia, true,2),
        Question(R.string.question_oceans, true,2),
        Question(R.string.question_mideast, false,2),
        Question(R.string.question_africa, false,2),
        Question(R.string.question_americas, true,2),
        Question(R.string.question_asia, true,2))

    val medialQuestion = listOf(
        Question(R.string.question_medial1, false,2),
        Question(R.string.question_medial2, true,2),
        Question(R.string.question_medial3, false,2),
        Question(R.string.question_medial4, false,2),
        Question(R.string.question_medial5, false,2),
        Question(R.string.question_medial6, false,2))

    val hardQuestion = listOf(
        Question(R.string.question_hard1, false,2),
        Question(R.string.question_hard2, false,2),
        Question(R.string.question_hard3, false,2),
        Question(R.string.question_hard4, false,2),
        Question(R.string.question_hard5, false,2),
        Question(R.string.question_hard6, false,2))

    val questionBank = listOf(
        easyQuestion.random(),easyQuestion.random(),medialQuestion.random(),medialQuestion.random()
        ,hardQuestion.random(),hardQuestion.random())

    private val answerBank= listOf(CheckAnsweredQuestions(0,false),
        CheckAnsweredQuestions(1,false),
        CheckAnsweredQuestions(2,false),
        CheckAnsweredQuestions(3,false),
        CheckAnsweredQuestions(4,false),
        CheckAnsweredQuestions(5,false)

    )

    val currentQuestionAnswer: Boolean
        get() = questionBank[currentIndex].answer

    val currentQuestionText: Int
        get() = questionBank[currentIndex].textResId




    fun moveToNext() {
        currentIndex = (currentIndex + 1) % questionBank.size
    }
 fun moveToBack(){
     if (currentIndex==0){ currentIndex =questionBank.size-1
     }
     else {
         currentIndex = (currentIndex - 1) % questionBank.size
     }
 }
    val size:Int
        get()=questionBank.size


    val kindOfQuestions:String
        get()=questionBank[currentIndex].kind


    val getGrade:String
        get()=grade.toString()

    val numberOfAnsweredQuestions
        get()=numAnswer
}