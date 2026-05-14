package com.akozlov.divcalculatorone

import android.R.attr.width
import android.graphics.Paint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.akozlov.divcalculatorone.ui.theme.DivCalculatorOneTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            DivCalculatorOneTheme {
                    Column(modifier = Modifier.padding(20.dp)) {

                        MainScreen()
                }
            }
        }
    }
}

@Composable
fun MainScreen() {
    // Состояние "поднято" сюда, чтобы все функции имели к нему доступ
    var inputDividend by remember { mutableStateOf("") }
    var inputDivisor by remember { mutableStateOf("") }
    val numericRegex = Regex("[^0-9]")
    val numericRegex2 = "^-?[0-9]*$".toRegex()
    var resultText by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        // Передаем значение и способ его изменить
        DividendField(text = inputDividend,
            {
                // Remove non-numeric characters.
                val stripped = numericRegex.replace(it, "")
                inputDividend = if (stripped.length >= 10) {
                    stripped.substring(0..9)
                } else if (!stripped.matches(numericRegex2)){
                    stripped + "0000"
                }  else {
                    stripped
                }
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        
        DivisorField(text = inputDivisor,
            {
                // Remove non-numeric characters.
                val stripped = numericRegex.replace(it, "")
                inputDivisor = if (stripped.length >= 10) {
                    stripped.substring(0..9)
                } else {
                    stripped
                }
            }
        )
        Spacer(modifier = Modifier.height(8.dp))

        val res = quotentAsStrind(inputDividend, inputDivisor)
        // Передаем действие при клике
        CalculateButton(onClick = { resultText = res})

        Spacer(modifier = Modifier.height(16.dp))

        // Передаем текст для отображения
        MyDisplayText(text = resultText)

        Spacer(modifier = Modifier.height(16.dp))

        SquareUnit()
    }
}

@Composable
fun DividendField(text: String, onValueChange: (String) -> Unit) {
    TextField(
        value = text,
        onValueChange = onValueChange,
        label = { Text("Введите Делимое") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun DivisorField(text: String, onValueChange: (String) -> Unit) {
    TextField(
        value = text,
        onValueChange = onValueChange,
        label = { Text("Введите Делитель") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        modifier = Modifier.fillMaxWidth()
    )
}
@Composable
fun CalculateButton(onClick: () -> Unit) {
    Button(onClick = onClick) {
        Text("Показать результат")
    }
}

@Composable
fun MyDisplayText(text: String) {
    if (text.isNotEmpty()) {
        Text(text = "Частное: $text", style = MaterialTheme.typography.bodyLarge)
    }
}

fun quotentAsStrind(divider:String, dividend:String): String {
    if (divider.isEmpty() or dividend.isEmpty()) return "0"
    val res = (divider.toDouble()/dividend.toDouble()).toString()
    return res
}

fun quotentAsDouble(divider:String, dividend:String): Double {
    if (divider.isEmpty() or dividend.isEmpty()) return 0.0
    val res = (divider.toDouble()/dividend.toDouble())
    return res
}

@Composable
fun SquareUnit() {
    Column(Modifier
        .size(width=25.dp, height = 25.dp)
        .border(width = 1.dp, color = Color.Blue),)
    {
        Row(modifier = Modifier.padding(top = 3.dp).fillMaxWidth().height(2.dp)
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    drawCircle(
                        color = Color.Black,
                        radius = size.minDimension / 1, // Размер точки
                        center = center // Центрирование внутри Canvas

                    )
                   }
               }
        Row(modifier = Modifier.fillMaxWidth()
            ,verticalAlignment = Alignment.CenterVertically) {

            Text(text = "-", textAlign = TextAlign.Center)
            Text(text = "0", textAlign = TextAlign.Center)
            Text(text = " ", textAlign = TextAlign.Center)
        }
        HorizontalDivider(
            modifier = Modifier.padding(vertical = 0.dp),
            thickness = 2.dp,
            color = Color.Black
        )
    }
}

fun ListFromRes(inputDividend: String, inputDivisor: String) {

}