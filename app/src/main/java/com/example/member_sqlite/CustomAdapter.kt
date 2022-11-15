package com.example.member_sqlite

import android.content.DialogInterface
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.member_sqlite.databinding.ItemMemberBinding

class CustomAdapter(val dataList: MutableList<Member>) : RecyclerView.Adapter<CustomAdapter.CustomViewHolder>() {

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomAdapter.CustomViewHolder {
        val binding = ItemMemberBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val customViewHolder = CustomAdapter.CustomViewHolder(binding)

        customViewHolder.itemView.setOnClickListener {

        }

        customViewHolder.itemView.setOnLongClickListener {
            val position: Int = customViewHolder.bindingAdapterPosition
            val member = dataList.get(position)
            (parent.context as ListActivity).refreshRecyclerViewUpdate(member, position)
            true
        }
        //리싸이클러뷰에 아이템을 오래 누르면 알러터다이얼로그가 뜨고 리스트액티비티의 drop함수를 불러서 데이터를 지운다.
        binding.btnDeleteRecyclerview.setOnClickListener{
            val eventHandler = object: DialogInterface.OnClickListener{
                override fun onClick(p0: DialogInterface?, answer: Int) {
                    if(answer == DialogInterface.BUTTON_POSITIVE){
                        val position: Int = customViewHolder.bindingAdapterPosition
                        val member = dataList.get(position)
                        (parent.context as ListActivity).refreshRecyclerViewDrop(member, position)
                    }
                }
            }
            AlertDialog.Builder(parent.context).run {
                setMessage("해당 회원정보를 삭제하시겠습니까?")
                setPositiveButton("삭제", eventHandler)
                setNegativeButton("취소", null)
                show()
            }
            true
        }

        return customViewHolder
    }


    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val binding = (holder as CustomViewHolder).binding
        val member = dataList[position]

        binding.tvIDItem.text = member.id
        binding.tvNameItem.text = member.name
        binding.tvPhoneItem.text = member.phone
    }

    class CustomViewHolder(val binding: ItemMemberBinding) : RecyclerView.ViewHolder(binding.root)
}