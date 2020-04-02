package com.example.project_3.util

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.project_3.R

open class MyDialog(private val context: Context) {
    private val dialog = Dialog(context)

    fun showLoading() = apply{
        val binding = LayoutInflater.from(context).inflate(R.layout.view_loading,null,false)
        show(binding)
    }

    private fun show(view: View, width: Double = .9, height: Int = ViewGroup.LayoutParams.WRAP_CONTENT) {
        dialog.window!!.setBackgroundDrawable(context.getDrawable(android.R.color.transparent))
        dialog.setContentView(view)
        dialog.setCancelable(false)
        dialog.window!!.setLayout((context.resources.displayMetrics.widthPixels * width).toInt(), height)
        dialog.show()
    }

    fun dismiss() = dialog.dismiss()

}