package com.example.member_sqlite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.member_sqlite.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun viewOnClicked(view: View?) {
        when (view?.id) {
            R.id.btn_login -> {
                //데이터베이스를 생성해서 SQLdatabase connection 가져온다
                val dbHelper = DBHelper(applicationContext, "memberDB.db", null, 1)
                var flag = false
                val id = binding.edtID.text.toString()
                val password = binding.edtPW.text.toString()

                if (id.length == 0 || password.length == 0) {
                    Toast.makeText(this, "모든 항목을 빠짐없이 입력해 주세요", Toast.LENGTH_SHORT).show()
                    flag = false
                }

                if (dbHelper.selectLogin(id, password) == true) {
                    flag = true
                }
                if (flag == true) {
                    val intent = Intent(applicationContext, LoginActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "로그인 실패", Toast.LENGTH_SHORT).show()
                }
            }
            R.id.btn_signUp -> {
                val intent = Intent(applicationContext, RegisterActivity::class.java)
                startActivity(intent)
            }
            R.id.btn_displayMain -> {
            }
        }
    }
}