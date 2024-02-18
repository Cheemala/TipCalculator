package com.cheemala.tipcalculator

import android.os.Bundle
import android.util.Log
import android.widget.Space
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Slider
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cheemala.tipcalculator.components.InputField
import com.cheemala.tipcalculator.ui.theme.TipCalculatorTheme
import com.cheemala.tipcalculator.util.calculateTotalBillAmountPerPerson
import com.cheemala.tipcalculator.util.calculateTotalTipAmount
import com.cheemala.tipcalculator.widgets.RoundIconBtn

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp {
                MainContent()
            }
        }
    }
}

@Composable
fun MyApp(content: @Composable () -> Unit) {
    TipCalculatorTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            color = Color.White
        ) {
            content()
        }
    }
}

//@Preview
@Composable
fun TopHeader(totalBillPerPersonAmount: Double = 0.0) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .padding(horizontal = 12.dp, vertical = 4.dp)
            .clip(shape = RoundedCornerShape(CornerSize(15.dp))),
        color = Color(0xFFE9D7F7)
    ) {
        Column(
            modifier = Modifier.padding(all = 12.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val point2DecimalVal = "%.2f".format(totalBillPerPersonAmount)
            Text(text = "Total Per Person", style = MaterialTheme.typography.h5)
            Text(
                text = "$$point2DecimalVal",
                style = MaterialTheme.typography.h4,
                fontWeight = FontWeight.ExtraBold
            )
        }
    }
}

@Preview
@Composable
fun MainContent() {
    Column() {
        BillForm() {}
    }
}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun BillForm(modifier: Modifier = Modifier, onValueChanged: (String) -> Unit) {

    val totalBillAmountState = remember {
        mutableStateOf("")
    }

    val validInputState = remember(totalBillAmountState.value) {
        totalBillAmountState.value.trim().isNotEmpty()
    }

    val splitNumberState = remember {
        mutableStateOf(1)
    }

    val sliderPositionState = remember {
        mutableStateOf(0f)
    }

    var tipPercentageVal = (sliderPositionState.value * 100).toInt()

    val totalTipAmount = remember {
        mutableStateOf(0)
    }

    val totalBillAmountPerPersonState = remember {
        mutableStateOf(0.0)
    }
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.White)
    ) {

        TopHeader(totalBillPerPersonAmount = totalBillAmountPerPersonState.value)

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp),
            shape = RoundedCornerShape(corner = CornerSize(8.dp)),
            border = BorderStroke(width = 1.dp, color = Color.LightGray),
            color = Color.White
        ) {
            Column() {
                InputField(valueState = totalBillAmountState,
                    labelId = "Enter Bill", isEnabled = true, isSingleLined = true,
                    onAction = KeyboardActions {
                        if (!validInputState)
                            return@KeyboardActions
                        onValueChanged(totalBillAmountState.value.trim())
                        keyboardController?.hide()
                    }
                )
                if (validInputState) {
                    Row(
                        modifier = Modifier
                            .padding(5.dp)
                            .fillMaxWidth(), horizontalArrangement = Arrangement.Start
                    ) {
                        Text(
                            text = "Split",
                            modifier = Modifier.align(alignment = Alignment.CenterVertically)
                        )
                        Spacer(modifier = Modifier.width(120.dp))

                        RoundIconBtn(imageDrawable = R.drawable.icon_add, onClick = {
                            splitNumberState.value = splitNumberState.value + 1
                            totalBillAmountPerPersonState.value = calculateTotalBillAmountPerPerson(
                                totalBillAmountState.value.toDouble(),
                                tipPercentageVal,
                                splitNumberState.value
                            )
                        })
                        Text(
                            text = "${splitNumberState.value}", modifier = Modifier
                                .align(alignment = Alignment.CenterVertically)
                                .padding(start = 10.dp, end = 10.dp)
                        )

                        RoundIconBtn(imageDrawable = R.drawable.icon_minus, onClick = {
                            splitNumberState.value =
                                if (splitNumberState.value > 1) splitNumberState.value - 1 else 1
                            totalBillAmountPerPersonState.value = calculateTotalBillAmountPerPerson(
                                totalBillAmountState.value.toDouble(),
                                tipPercentageVal,
                                splitNumberState.value
                            )
                        })
                    }

                    Row(
                        modifier = Modifier
                            .padding(5.dp)
                            .fillMaxWidth(), horizontalArrangement = Arrangement.Start
                    ) {
                        Text(
                            text = "Tip",
                            modifier = Modifier.align(alignment = Alignment.CenterVertically)
                        )
                        Spacer(modifier = Modifier.width(150.dp))
                        Image(
                            painter = painterResource(id = R.drawable.icon_rupee),
                            contentDescription = "Total tip amount"
                        )
                        Text(
                            text = "${totalTipAmount.value}",
                            modifier = Modifier.align(alignment = Alignment.CenterVertically)
                        )
                    }

                    Column(
                        modifier = Modifier,
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "$tipPercentageVal%")

                        Spacer(modifier = Modifier.height(20.dp))

                        Slider(
                            value = sliderPositionState.value,
                            onValueChange = {
                                sliderPositionState.value = it
                                totalTipAmount.value = calculateTotalTipAmount(
                                    totalBillAmountState.value.toDouble(),
                                    tipPercentageVal
                                ).toInt()
                                totalBillAmountPerPersonState.value =
                                    calculateTotalBillAmountPerPerson(
                                        totalBillAmountState.value.toDouble(),
                                        tipPercentageVal,
                                        splitNumberState.value
                                    )
                            }, modifier = Modifier.padding(start = 16.dp, end = 16.dp), steps = 5
                        )
                    }
                } else {
                    Box() {

                    }
                }
            }
        }
    }

}


//@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TipCalculatorTheme {
    }
}