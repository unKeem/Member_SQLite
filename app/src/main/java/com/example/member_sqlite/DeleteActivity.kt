package com.example.member_sqlite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.member_sqlite.databinding.ActivityDeleteBinding

class DeleteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityDeleteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnDeleteDelete.setOnClickListener{
            //데이터베이스를 생성해서 SQLdatabase connection 가져온다
            val dbHelper = DBHelper(applicationContext, "memberDB.db", null, 1)
            var flag = false
            val id = binding.edtIDMemberDelete.text.toString()

            if (id.length == 0) {
                Toast.makeText(this,"삭제할 ID 입력해 주세요", Toast.LENGTH_SHORT).show()
                flag = false
            }

            if (dbHelper.delete(id) == true) {
                flag = true
                Toast.makeText(this, "${id} 삭제 성공", Toast.LENGTH_SHORT).show()
                Log.d("member_sqlite", "${id} 삭제 성공")
            }
            if (flag == true) {
               finish()
            } else {
                Toast.makeText(this, "${id} 삭제 실패", Toast.LENGTH_SHORT).show()
            }
        }
        binding.btnBacktToMainDelete.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}