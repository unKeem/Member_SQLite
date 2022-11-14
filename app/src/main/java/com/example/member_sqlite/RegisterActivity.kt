package com.example.member_sqlite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.member_sqlite.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    var flag: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //패스워드체크에서 커서가 넘어갈때 패스워드 값과 같은지 체크
        binding.edtRegPWCheck.setOnFocusChangeListener { view, b -> }

        binding.btnRegSave.setOnClickListener {
            //데이터베이스를 생성해서 SQLdatabase connection 가져온다
            val dbHelper = DBHelper(applicationContext, "memberDB.db", null, 1)

            val name = binding.edtRegName.text.toString()
            val id = binding.edtRegID.text.toString()
            val password = binding.edtRegPW.text.toString()
            val password_check = binding.edtRegPWCheck.text.toString()
            val phone = binding.edtRegPhone.text.toString()
            val email = binding.edtRegEmail.text.toString()
            val address = binding.edtRegAddress.text.toString()
            val level = binding.edtRegLevel.text.toString()

            //데이터를 모두 입력했는지
            if (name.length == 0 || id.length == 0 || password.length == 0 || password_check.length == 0 ||
                phone.length == 0 || email.length == 0 || address.length == 0 || level.length == 0
            ) {
                Toast.makeText(this, "모든 항목을 빠짐없이 입력해 주세요", Toast.LENGTH_SHORT).show()
                flag = false
            }
            //password와 passwordCheck가 일치하는지 체크
            if (!password.equals(password_check)) {
                Toast.makeText(this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
                flag = false
            }
            //아이디가 중복되는지
            if (dbHelper.selectCheckID(id)) {
                Toast.makeText(this, "사용할수 없는 아이디 입니다.", Toast.LENGTH_SHORT).show()
                flag = false
            }else{
                flag = true
            }
            if (!dbHelper.insert(id, name, password, phone, email, address, level)) {
                Toast.makeText(this, "회원가입 오류 발생.", Toast.LENGTH_SHORT).show()
            }
            if (flag != false) {
                finish()
            }
            dbHelper.close()
        }//btnRegSave event handler

        binding.btnRegBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}