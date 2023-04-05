package com.racso.pokeapp.core

import android.content.Context
import android.view.View
import android.widget.Toast


fun Context.toast(txt: String, duration: Int = Toast.LENGTH_SHORT){
    Toast.makeText(this, txt, duration).show()
}

fun View.hide(){
    this.visibility = View.GONE
}

fun View.show(){
    this.visibility = View.VISIBLE
}