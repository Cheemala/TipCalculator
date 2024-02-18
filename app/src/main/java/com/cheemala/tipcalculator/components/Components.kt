package com.cheemala.tipcalculator.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cheemala.tipcalculator.R

@Composable
fun InputField(modifier: Modifier = Modifier,
               valueState:MutableState<String>,
               labelId:String,
               isEnabled:Boolean,
               isSingleLined: Boolean,
               keyboardType: KeyboardType = KeyboardType.Number,
               imeAction: ImeAction = ImeAction.Next,
               onAction: KeyboardActions = KeyboardActions.Default){

    OutlinedTextField(value = valueState.value,
        onValueChange = {valueState.value = it},
        modifier = modifier
            .padding(10.dp)
            .fillMaxWidth(),
        label = { Text(text = labelId)},
        singleLine = isSingleLined,
        enabled = isEnabled,
        textStyle = TextStyle(color = Color.Black, fontSize = 18.sp),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction),
        keyboardActions = onAction,
    leadingIcon = { Image(painter = painterResource(id = R.drawable.icon_rupee), contentDescription = "Enter bill amount")})

}