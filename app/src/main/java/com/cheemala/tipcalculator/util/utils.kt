package com.cheemala.tipcalculator.util

fun calculateTotalTipAmount(billAmount: Double, tipPercentage: Int): Double {
    if(billAmount.toString().isNotEmpty() && billAmount>0){
        return (billAmount*tipPercentage)/100
    }
    return 0.0
}

fun calculateTotalBillAmountPerPerson(billAmount: Double, tipPercentage: Int, splitBy:Int):Double{
    val totalBillAmountIncludingTip = calculateTotalTipAmount(billAmount,tipPercentage)+billAmount

    return totalBillAmountIncludingTip/splitBy
}