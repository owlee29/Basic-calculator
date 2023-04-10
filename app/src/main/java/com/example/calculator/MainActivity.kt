package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import java.lang.ArithmeticException

class MainActivity : AppCompatActivity() {

    private var ScreenResult: TextView? = null
    var lastValueNumeric : Boolean = false
    var lastValueDot : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ScreenResult = findViewById(R.id.textViewResultScreen)
    }

    fun onNumber(view: View) {
        ScreenResult?.append((view as Button).text)
        lastValueNumeric = true
        lastValueDot = false
    }

    fun onClear(view: View) {
        ScreenResult?.text = ""
    }

    fun onDecimalPoint(view: View){
        if(lastValueNumeric && !lastValueDot) {
            ScreenResult?.append(".")
            lastValueNumeric = false
            lastValueDot = true
        }
    }

    fun onOperator(view: View) {
        ScreenResult?.text?.let {
            if(lastValueNumeric && !isOperatorAdded(it.toString())) {
                ScreenResult?.append((view as Button).text)
                lastValueNumeric = false
                lastValueDot = false
            }
        }
    }

    private fun removeZeroAfterDot(result : String) : String {

        var value = result
        if(result.contains(".0"))
            value = result.substring(0, result.length -2 )

        return value
    }

    private fun isOperatorAdded(value : String) : Boolean {
        return if(value.startsWith("-")) {
            false
        } else {
            value.contains("/")
                    || value.contains("*" )
                    || value.contains("+" )
                    || value.contains("-" )
        }
    }

    fun onEqual (view: View) {
        if(lastValueNumeric) {
            var textViewValue = ScreenResult?.text.toString()
            var prefix = ""

            try {
                if(textViewValue.startsWith("-")) {
                    prefix = "-"
                    textViewValue = textViewValue.substring(1)
                }
                if(textViewValue.contains("-")) {
                    var splitValue = textViewValue.split("-")

                    var one = splitValue[0]
                    var two = splitValue[1]

                    if(prefix.isNotEmpty()) {
                        one = prefix + one
                    }

                    ScreenResult?.text = removeZeroAfterDot((one.toDouble() - two.toDouble()).toString())

                } else if(textViewValue.contains("+")) {
                    var splitValue = textViewValue.split("+")

                    var one = splitValue[0]
                    var two = splitValue[1]

                    if(prefix.isNotEmpty()) {
                        one = prefix + one
                    }

                    ScreenResult?.text = removeZeroAfterDot((one.toDouble() + two.toDouble()).toString())

                } else if(textViewValue.contains("/")) {
                    var splitValue = textViewValue.split("/")

                    var one = splitValue[0]
                    var two = splitValue[1]

                    if(prefix.isNotEmpty()) {
                        one = prefix + one
                    }

                    ScreenResult?.text = removeZeroAfterDot((one.toDouble() / two.toDouble()).toString())

                } else if(textViewValue.contains("*")) {
                    var splitValue = textViewValue.split("*")

                    var one = splitValue[0]
                    var two = splitValue[1]

                    if(prefix.isNotEmpty()) {
                        one = prefix + one
                    }

                    ScreenResult?.text = removeZeroAfterDot((one.toDouble() * two.toDouble()).toString())
                }

            } catch (e: ArithmeticException) {
                e.printStackTrace()
            }
        }
    }
}