package com.example.member_sqlite

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.member_sqlite.databinding.ActivityUpdateBinding

class UpdateActivity : AppCompatActivity() {
    var member: Member? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //아이디를 검색해서 정보를 가져옴
        binding.btnSearchIdUpdate.setOnClickListener {
            val dbHelper = DBHelper(applicationContext, "memberDB.db", null, 1)
            val id = binding.edtUpdateId.text.toString()
            var flag = true
            if (id.length == 0) {
                flag = false
                Toast.makeText(this, "수정할 회원ID를 입력하세요", Toast.LENGTH_SHORT).show()
            }
            if (flag == true) {
                member = dbHelper.selectID(id)
                if (member != null) {
                    binding.edtPhoneUpdate.setText(member?.phone)
                    binding.edtPwUpdate.setText(member?.password)
                    binding.edtLevelUpdate.setText(member?.level)
                    Log.d("memberDB.db", "수정완료")
                } else {
                    flag = false
                    Toast.makeText(this, "해당 ID는 존재하지 않습니다.", Toast.LENGTH_SHORT).show()
                }
            }
            dbHelper.close()
        }

        //위에서 검색학 아이디의 정보(비밀번호 전화번호 직급)를 수정한다
        binding.btnUpdate.setOnClickListener {
            val dbHelper = DBHelper(applicationContext, "memberDB.db", null, 1)
            val phone = binding.edtPhoneUpdate.text.toString()
            val password = binding.edtPwUpdate.text.toString()
            val level = binding.edtLevelUpdate.text.toString()
            var flag = false

            // isBank : 길이가 0 이거나, whitespace(스페이스, 탭, 엔터)가 있으면 true
            if (member != null && phone.isNotBlank() && password.isNotBlank() && level.isNotBlank()) {
                member!!.phone = phone
                member!!.password = password
                member!!.level = level
                flag = dbHelper.update(member)
                if(flag ==true){
                    Toast.makeText(this, "정보 수정 성공", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this, "정보 수정 실패", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this, "수정할 정보를 입력하세요", Toast.LENGTH_SHORT).show()
            }
            dbHelper.close()
        }
        binding.btnMainUpdate.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
