package com.example.member_sqlite

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import com.example.member_sqlite.databinding.ActivityCustomDialogUpdateBinding

class CustomDialogUpdate(val context: Context) {
    val dialog = Dialog(context)

    fun showDialog(){
        val binding = ActivityCustomDialogUpdateBinding.inflate(LayoutInflater.from(context))
        dialog.setContentView(binding.root)


    }



}