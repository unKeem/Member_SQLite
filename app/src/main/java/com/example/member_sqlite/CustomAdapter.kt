package com.example.member_sqlite

import android.content.DialogInterface
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.member_sqlite.databinding.CustomDialogDetailBinding
import com.example.member_sqlite.databinding.CustomDialogUpdateBinding
import com.example.member_sqlite.databinding.ItemMemberBinding

class CustomAdapter(val dataList: MutableList<Member>) :
    RecyclerView.Adapter<CustomAdapter.CustomViewHolder>() {

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val binding = ItemMemberBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val customViewHolder = CustomViewHolder(binding)

        //리싸이클러뷰에 아이템을 터치하면 해당 데이터의 상세 정보가 다이얼로그 창으로 띄워진다.
        customViewHolder.itemView.setOnClickListener {
            val detailBinding =
                CustomDialogDetailBinding.inflate(LayoutInflater.from(parent.context))
            val builderShow = AlertDialog.Builder(parent.context)
            val itemPosition = customViewHolder.adapterPosition
            var member = dataList.get(itemPosition)

            detailBinding.tvIDDetail.setText(member.id)
            detailBinding.tvNameDetail.setText(member.name)
            detailBinding.tvPwDetail.setText(member.password)
            detailBinding.tvPhoneDetail.setText(member.phone)
            detailBinding.tvEmailDetail.setText(member.email)
            detailBinding.tvAddDetail.setText(member.address)
            detailBinding.tvLevelDetail.setText(member.level)

            builderShow.setView(detailBinding.root)
            var detailDialog: AlertDialog = builderShow.create()
            detailDialog.setCanceledOnTouchOutside(false)
            detailDialog.setCancelable(false)
            detailDialog.show()

            detailBinding.btnCloseDetail.setOnClickListener {
                detailDialog.dismiss()
            }

            detailBinding.btnUpdateDetail.setOnClickListener {
                val showUpdateBinding =
                    CustomDialogUpdateBinding.inflate(LayoutInflater.from(parent.context))
                showUpdateBinding.tvIDUpdateDial.setText(member.id)
                showUpdateBinding.edtNameUpdateDial.setText(member.name)
                showUpdateBinding.edtPwUpdateDial.setText(member.password)
                showUpdateBinding.edtPhoneUpdateDial.setText(member.phone)
                showUpdateBinding.edtEmailUpdateDial.setText(member.email)
                showUpdateBinding.edtAddUpdateDial.setText(member.address)
                showUpdateBinding.edtLevelUpdateDial.setText(member.level)
                builderShow.setView(showUpdateBinding.root)
                val updateDialog: AlertDialog = builderShow.create()
                updateDialog.setCanceledOnTouchOutside(false)
                updateDialog.setCancelable(false)
                updateDialog.show()

                showUpdateBinding.btnCloseUpdateDial.setOnClickListener {
                    val id = showUpdateBinding.tvIDUpdateDial.text.toString()
                    val name = showUpdateBinding.edtNameUpdateDial.text.toString()
                    val password = showUpdateBinding.edtPwUpdateDial.text.toString()
                    val phone = showUpdateBinding.edtPhoneUpdateDial.text.toString()
                    val email = showUpdateBinding.edtEmailUpdateDial.text.toString()
                    val address = showUpdateBinding.edtAddUpdateDial.text.toString()
                    val level = showUpdateBinding.edtLevelUpdateDial.text.toString()
                    member = Member(id, name, password, phone, email, address, level)
                    dataList.set(itemPosition, member)
                    notifyItemInserted(itemPosition)

                    detailBinding.tvIDDetail.setText(id)
                    detailBinding.tvNameDetail.setText(name)
                    detailBinding.tvPwDetail.setText(password)
                    detailBinding.tvPhoneDetail.setText(phone)
                    detailBinding.tvEmailDetail.setText(email)
                    detailBinding.tvAddDetail.setText(address)
                    detailBinding.tvLevelDetail.setText(level)

                    updateDialog.dismiss()
                }
            }
        }

        customViewHolder.itemView.setOnLongClickListener {
//            val position: Int = customViewHolder.bindingAdapterPosition
//            val member = dataList.get(position)
//            (parent.context as ListActivity).refreshRecyclerViewUpdate(member, position)
            true
        }
        //리싸이클러뷰에 삭제아이콘버튼누르면 알러터다이얼로그가 뜨고 리스트액티비티의 drop함수를 불러서 데이터를 지운다.
        binding.btnDeleteRecyclerview.setOnClickListener {
            val eventHandler = object : DialogInterface.OnClickListener {
                override fun onClick(p0: DialogInterface?, answer: Int) {
                    if (answer == DialogInterface.BUTTON_POSITIVE) {
                        val position: Int = customViewHolder.bindingAdapterPosition
                        val member = dataList.get(position)
                        (parent.context as ListActivity).refreshRecyclerViewDrop(
                            member,
                            position
                        )
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

    class CustomViewHolder(val binding: ItemMemberBinding) :
        RecyclerView.ViewHolder(binding.root)
}