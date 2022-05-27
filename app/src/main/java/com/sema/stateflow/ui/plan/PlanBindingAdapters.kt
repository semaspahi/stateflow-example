package com.sema.stateflow.ui.plan

import android.widget.TextView
import androidx.databinding.BindingAdapter
import java.text.NumberFormat
import java.util.*

@BindingAdapter("app:totalPlanValue")
fun TextView.showTotalPlanValue(totalPlanValue: Float?) {
    totalPlanValue?.let {
        val format = NumberFormat.getCurrencyInstance(Locale.getDefault())
        format.minimumFractionDigits = 2
        format.currency = Currency.getInstance("GBP")
        text = format.format(totalPlanValue)
    }
}


