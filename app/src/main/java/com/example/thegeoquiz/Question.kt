package com.example.thegeoquiz

import androidx.annotation.StringRes

data class Question(@StringRes val textResId: Int, val answer: Boolean, var answered: Int){

}