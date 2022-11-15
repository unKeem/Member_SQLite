package com.example.member_sqlite

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.CursorAdapter
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.member_sqlite.databinding.ActivityListBinding
import com.google.android.material.snackbar.Snackbar

class ListActivity : AppCompatActivity() {
    lateinit var binding: ActivityListBinding
    lateinit var customAdapter: CustomAdapter
    var dataList = mutableListOf<Member>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //리싸이클러뷰, 아이템을 클릭하면 상세정보 출력, 뷰홀더 활용
        //아이템을 롱클릭하면 커스텀 다이얼로그를 통해 수정
        //플로팅 버튼으로 회원 추가

        //데이터베이스를 생성해서 SQLdatabase connection 가져온다
        val dbHelper = DBHelper(applicationContext, "memberDB.db", null, 1)
        dataList = dbHelper.selectAll()

        //리싸이클러뷰에 어댑터 연결
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = CustomAdapter(dataList)
        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(
                this,
                LinearLayoutManager.VERTICAL
            )
        )
        binding.btnMainList.setOnClickListener {
            finish()
        }
        dbHelper.close()
    }

    fun refreshRecyclerViewDrop(member: Member, position: Int) {
        val dbHelper = DBHelper(applicationContext, "memberDB.db", null, 1)
        Snackbar.make(binding.root, "${member.id}가 삭제되었습니다.", Toast.LENGTH_SHORT).show()
        dbHelper.delete(member.id)
        dataList.remove(member)
        customAdapter.notifyItemRemoved(position)
        dbHelper.close()
    }
    fun refreshRecyclerViewUpdate(member: Member, position: Int) {
        val dbHelper = DBHelper(applicationContext, "memberDB.db", null, 1)
        Snackbar.make(binding.root, "${member.id}가 수정되었습니다.", Toast.LENGTH_SHORT).show()



        customAdapter.notifyItemRemoved(position)
    }
}