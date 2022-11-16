package com.example.member_sqlite

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.Toast
import com.example.member_sqlite.databinding.CustomDialogRegisterBinding

class CustomDialogRegister(val context: Context) {
    val dialog = Dialog(context)

    fun showDialog() {
        val binding = CustomDialogRegisterBinding.inflate(LayoutInflater.from(context))
        dialog.setContentView(binding.root)
        dialog.window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        dialog.setCanceledOnTouchOutside(false)
        dialog.setCancelable(false)
        dialog.show()

        binding.btnBackRegisterDial.setOnClickListener {
            dialog.dismiss()
        }
        binding.btnRegisterRegisterDial.setOnClickListener {
            var flag = true
            val dbHelper = DBHelper(context, "memberDB.db", null, 1)

            val name = binding.edtNameRegisterDial.text.toString()
            val id = binding.edtIDRegisterDial.text.toString()
            val password = binding.edtPwRegisterDial.text.toString()
            val passwordCheck = binding.edtPwcheckRegisterDial.text.toString()
            val phone = binding.edtPhoneRegisterDial.text.toString()
            val email = binding.edtEmailRegisterDial.text.toString()
            val address = binding.edtAddRegisterDial.text.toString()
            val level = binding.edtLevelRegisterDial.text.toString()

            if (id.length == 0 || name.length == 0 || password.length == 0 || passwordCheck.length == 0 ||
                phone.length == 0 || email.length == 0 || address.length == 0 || level.length == 0
            ) {
                Toast.makeText(context, "모든 항목을 입력하세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!(password.equals(passwordCheck))) {
                Toast.makeText(
                    context, "비밀번호가 다릅니다.\n확인 후 다시 입력해주세요.",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            if (dbHelper.selectCheckID(id)) {
                Toast.makeText(context, "해당 아이디는 이미 사용중입니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            val member = Member(id, name, password, phone, email, address, level)
            if (!(dbHelper.insert(member))) {
                Toast.makeText(context, "회원가입 오류 발생.", Toast.LENGTH_SHORT).show()
            }

            flag = (context as ListActivity).refreshRecyclerViewAdd(member)
            // 모두 문제 없이 진행됐을 경우에 한하여 Activity 종료
            if (flag == false) {
                Toast.makeText(context, "회원정보 DB 저장 실패", Toast.LENGTH_SHORT).show()
            }
            dbHelper.close()
        }
    }
}
