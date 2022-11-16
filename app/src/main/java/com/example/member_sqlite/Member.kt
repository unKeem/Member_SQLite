package com.example.member_sqlite

data class Member(val id:String, val name: String, var password: String, var phone: String, val email: String, val address:String, var level:String)