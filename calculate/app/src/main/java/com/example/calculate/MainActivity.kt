package com.example.calculate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import java.lang.NumberFormatException

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val etSum1 = findViewById<EditText>(R.id.etSummand1)
        val tvSum2 = findViewById<TextView>(R.id.tvSummand2)
        val tvOper= findViewById<TextView>(R.id.tvOperator)

        findViewById<Button>(R.id.btn1).let {
            it.setOnClickListener {
                etSum1.append("1")
            }
        }

        findViewById<Button>(R.id.btn2).let {
            it.setOnClickListener {
                etSum1.append("2")
            }
        }

        findViewById<Button>(R.id.btn3).let {
            it.setOnClickListener {
                etSum1.append("3")
            }
        }

        findViewById<Button>(R.id.btn4).let {
            it.setOnClickListener {
                etSum1.append("4")
            }
        }

        findViewById<Button>(R.id.btn5).let {
            it.setOnClickListener {
                etSum1.append("5")
            }
        }

        findViewById<Button>(R.id.btn6).let {
            it.setOnClickListener {
                etSum1.append("6")
            }
        }

        findViewById<Button>(R.id.btn7).let {
            it.setOnClickListener {
                etSum1.append("7")
            }
        }

        findViewById<Button>(R.id.btn8).let {
            it.setOnClickListener {
                etSum1.append("8")
            }
        }

        findViewById<Button>(R.id.btn9).let {
            it.setOnClickListener {
                etSum1.append("9")
            }
        }

        findViewById<Button>(R.id.btn0).let {
            it.setOnClickListener {
                if (etSum1.text.toString() != "0") {
                    etSum1.append("0")
                }
            }
        }

        findViewById<Button>(R.id.btnPoint).setOnClickListener {
            if (etSum1.text.isNotEmpty() && !etSum1.text.contains(".")) {
                etSum1.append(".")
            }
        }


        fun calculate() {
            if (tvSum2.text.isNotEmpty()) {

                var result = 0f
                when (tvOper.text) {
                    "+" -> result = tvSum2.text.toString().toFloat() + etSum1.text.toString().toFloat()
                    "-" -> result = tvSum2.text.toString().toFloat() - etSum1.text.toString().toFloat()
                    "*" -> result = tvSum2.text.toString().toFloat() * etSum1.text.toString().toFloat()
                    "/" -> {
                        val divisor = etSum1.text.toString().toFloat()
                        if (divisor != 0f) {
                            result = tvSum2.text.toString().toFloat() / divisor
                        } else {
                            etSum1.error = "На ноль делить нельзя!"
                            return
                        }
                    }
                }

                tvSum2.text = result.toString()
            } else {
                tvSum2.text = etSum1.text
            }
            etSum1.text.clear()
        }

        val operButtons = listOf<Button>(
            findViewById(R.id.btnPlus),
            findViewById(R.id.btnMinus),
            findViewById(R.id.btnMultiply),
            findViewById(R.id.btnDivide),
        )

        operButtons.forEach { button ->
            button.setOnClickListener {
                if (etSum1.text.isEmpty() && button.text == "-")
                {
                    etSum1.text.append("-")
                    return@setOnClickListener
                }

                var n1: Float? = null
                val sum1 = etSum1
                try {
                    n1 = sum1.text.toString().toFloat()
                } catch (nfe: NumberFormatException) {
                    sum1.error = "Введите число"
                }
                if (n1 == null) return@setOnClickListener

                if (etSum1.text.isEmpty() && button.text == "-")
                    tvSum2.text = "0"

                calculate()

                tvOper.text = button.text
                etSum1.setSelection(etSum1.text.length)
            }
        }



        findViewById<Button>(R.id.btnEqually).let {
            it.setOnClickListener {
                var n1: Float? = null
                val sum1 = etSum1
                try {
                    n1 = sum1.text.toString().toFloat()
                } catch (nfe: NumberFormatException) {
                    sum1.error = "Введите число"
                    return@setOnClickListener
                }

                if (tvOper.text.isEmpty()) {
                    etSum1.error = "Выберите операцию"
                    return@setOnClickListener
                }
                calculate()


                val etSumText = etSum1.text.toString()
                if (etSumText.isNotEmpty() && etSumText.toFloat() == 0f && tvOper.text == "/") {
                    return@setOnClickListener
                }
                etSum1.setText(tvSum2.text)
                tvSum2.text = ""
                tvOper.text = ""
                etSum1.setSelection(etSum1.text.length)
            }
        }

        findViewById<Button>(R.id.btnClear).let {
            it.setOnClickListener {
                etSum1.text.clear()
                tvSum2.text = ""
                tvOper.text = ""
            }
        }


    }
}
