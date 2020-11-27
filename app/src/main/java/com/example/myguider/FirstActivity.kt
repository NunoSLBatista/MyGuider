package com.example.myguider

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

import kotlinx.android.synthetic.main.activity_first.*
import android.util.DisplayMetrics


class FirstActivity : AppCompatActivity() {

    var currentScreen = 1
    var linesArray = arrayOfNulls<View>(3)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first)

        linesArray.set(0, line1)
        linesArray.set(1, line2)
        linesArray.set(2, line3)

        mainImage.setOnTouchListener(object : OnSwipeTouchListener(this@FirstActivity) {
            override fun onSwipeLeft() {
                super.onSwipeLeft()
                if(currentScreen != 3){
                    currentScreen += 1
                    updateScreen()
                }
            }
            override fun onSwipeRight() {
                super.onSwipeRight()
                if(currentScreen != 1){
                    currentScreen -= 1
                    updateScreen()
                }
            }
            override fun onSwipeUp() {
                super.onSwipeUp()
                val intent = Intent(applicationContext, LoginActivity::class.java)
                startActivity(intent)
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out)
            }
            override fun onSwipeDown() {
                super.onSwipeDown()
            }
        })

    }

    fun updateScreen(){
        val currentScreenText = "0" + currentScreen.toString()
        currentScreenLabel.setText(currentScreenText)

    }

    fun convertDpToPixel(dp: Float, context: Context): Int {
        val resources = context.getResources()
        val metrics = resources.getDisplayMetrics()
        return (dp * (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)).toInt()
    }

}
