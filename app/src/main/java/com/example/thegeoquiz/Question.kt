package com.example.thegeoquiz

import androidx.annotation.StringRes

data class Question(@StringRes val textResId: Int, val answer: Boolean, var answered: Int){


    val kind: String
        get() {
            TODO()
        }
}
data class CheckAnsweredQuestions(val currentIndex:Int,var answered:Boolean)
{



}