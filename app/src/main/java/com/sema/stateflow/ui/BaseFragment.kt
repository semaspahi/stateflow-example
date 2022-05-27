package com.sema.stateflow.ui

import android.app.Dialog
import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.viewbinding.ViewBinding
import com.jintin.bindingextension.BindingFragment
import com.sema.stateflow.R
import com.sema.stateflow.data.api.HttpErrorData

open class BaseFragment<VB : ViewBinding> : BindingFragment<VB>() {

    private var loadingDialog: Dialog? = null

    fun showLoadingDialog(context: Context) {
        loadingDialog = Dialog(context)
        loadingDialog?.let {
            it.setCancelable(false)
            it.setCanceledOnTouchOutside(false)
//            it.setContentView(R.layout.custom_loading_dialog)
            it.show()
        }
    }

    open fun hideLoadingDialog() = loadingDialog?.dismiss()

    fun showToast(message: CharSequence) {
        val appContext = context?.applicationContext ?: return
        Toast.makeText(appContext, message, Toast.LENGTH_LONG).show()
    }

    fun showToast(@StringRes string: Int) {
        val appContext = context?.applicationContext ?: return
        Toast.makeText(appContext, string, Toast.LENGTH_LONG).show()
    }

    fun HttpErrorData?.handleErrorMessage() {
        this?.message?.let { showToast(it) }
            ?: showToast(R.string.generic_error_message)
    }
}