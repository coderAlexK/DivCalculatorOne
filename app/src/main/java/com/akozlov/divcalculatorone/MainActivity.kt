package com.akozlov.divcalculatorone

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
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
    var inputDivider by remember { mutableStateOf("") }
    var inputDividend by remember { mutableStateOf("") }
    val numericRegex = Regex("[^0-9]")
    var resultText by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        // Передаем значение и способ его изменить
        DividerField(text = inputDivider,
            {
                // Remove non-numeric characters.
                val stripped = numericRegex.replace(it, "")
                inputDivider = if (stripped.length >= 10) {
                    stripped.substring(0..9)
                } else {
                    stripped
                }
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        DividendField(text = inputDividend,
            {
                // Remove non-numeric characters.
                val stripped = numericRegex.replace(it, "")
                inputDividend = if (stripped.length >= 10) {
                    stripped.substring(0..9)
                } else {
                    stripped
                }
            }
        )
        Spacer(modifier = Modifier.height(8.dp))

        var res = CalculationUnit(inputDivider, inputDividend)
        // Передаем действие при клике
        CalculateButton(onClick = { resultText = res})

        Spacer(modifier = Modifier.height(16.dp))

        // Передаем текст для отображения
        MyDisplayText(text = resultText)
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DivCalculatorOneTheme {

    }
}

@Composable
fun DividerField(text: String, onValueChange: (String) -> Unit) {
    TextField(
        value = text,
        onValueChange = onValueChange,
        label = { Text("Введите Делимое") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun DividendField(text: String, onValueChange: (String) -> Unit) {
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


fun CalculationUnit(divider:String, dividend:String): String {
    if (divider.isEmpty() or dividend.isEmpty()) return "0"
    val res = (divider.toInt()/dividend.toInt()).toString()
    return res
}





