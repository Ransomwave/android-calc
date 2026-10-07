package com.example.calc

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.allViews
import com.notkamui.keval.Keval

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        println("KEVAL RESULT: ${Keval.eval("(3+4)(2/8 * 5)")}")

        val calcText = findViewById<EditText>(R.id.calcText)
        val mainLayout = findViewById<ViewGroup>(R.id.MainVerticalLayout)

        // filter all main layout's descendants to only include buttons
        // and turn it into a list
        val buttons = mainLayout.allViews.filterIsInstance<Button>().toList()

        fun buttonClickHandler(view: View) {
            if (view !is Button) return

            if (view.tag == "number") {
                calcText.text.append(view.text)
            } else {
                when (view.id) {
                    R.id.btnBackspace -> {
                        calcText.setText(calcText.text.dropLast(1))
                    }

                    R.id.btnClear -> {
                        calcText.text.clear()
                    }

                    R.id.btnMultiply, R.id.btnPlus, R.id.btnMinus, R.id.btnDivide, R.id.btnModulo -> {
                        calcText.text.append(view.text)
                    }

                    R.id.btnBraces -> {
                        val text = calcText.text
                        val hasUnclosed = text.count { it == '(' } > text.count { it == ')' }
                        text.append(if (hasUnclosed) ")" else "(")
                    }

                    R.id.btnEquals -> {
                        val mathResult = Keval.eval(calcText.text.toString())
                        calcText.setText(mathResult.toString().format("%.2f"))
                    }
                }
            }
        }

        // "::" is a function reference
        buttons.forEach { it.setOnClickListener(::buttonClickHandler) }

    }
}