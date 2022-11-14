package com.example.member_sqlite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.member_sqlite.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    lateinit var binding :ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
    fun viewOnClick(view: View?){
        when(view?.id){
            R.id.btn_add_member ->{
                val intent = Intent(this, RegisterActivity::class.java)
                startActivity(intent)
            }
            R.id.btn_delete_member ->{
                val intent = Intent(this, DeleteActivity::class.java)
                startActivity(intent)
            }
            R.id.btn_list_member ->{}
            R.id.btn_update_memberInfo ->{}
            R.id.btn_back_loginActivity ->{}
            R.id.btn_delete_table ->{}
        }
    }
}