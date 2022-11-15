package com.example.member_sqlite

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.widget.Toast
import java.sql.SQLException

class DBHelper(
    val context: Context?,
    val name: String?,
    val factory: SQLiteDatabase.CursorFactory?,
    val version: Int
) :
    SQLiteOpenHelper(context, name, factory, version) {

    //멤버 테이블 정의(앱이 설치돼서 DBHelper() 딱 한번만 실행된다!!!)
    override fun onCreate(db: SQLiteDatabase?) {
        if (db != null) {
            db.execSQL(
                "Create table memberTBL(" +
                        "id text primary Key," +
                        "password text not null," +
                        "name text not null," +
                        "phone text not null," +
                        "email text," +
                        "address text, " +
                        "level text);"
            )
            // 계속해서 더 작성할 수 있다.(ex 테이블을 더 만든다던가 조건을 건다던가)
        }
    }

    //데이터베이스 버전이 바뀔때마다 실행된다.
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("drop table if exists memberTBL")
        onCreate(db)
    }

    // 삽입 쿼리 함수 생성(RegisterActivity에서 reg버튼 눌러서 아이디 중복이나 패스워드 체크 하고 불러짐)
    fun insert(
        name: String,
        id: String,
        password: String,
        phone: String,
        email: String,
        address: String,
        level: String
    ): Boolean {
        val db: SQLiteDatabase = writableDatabase
        var flag = false
        try {
            db.execSQL("insert into memberTBL values('${name}','${id}','${password}','${phone}','${email}','${address}','${level}')")
            flag = true
        } catch (e: SQLException) {
            Log.d("member_sqlite", " ${id} ${name} insert실패")
            flag = false
        }finally {
            db.close()
        }
        Toast.makeText(context, "${id} 회원등록 성공", Toast.LENGTH_SHORT).show()
        Log.d("member_sqlite", " ${id} ${name} insert성공")
        return true
    }

    // 레지스터 액티비티에서 입력받은 아이디와 같은 아이디가 db에 존재하는지 체크한다
    fun selectCheckID(id: String): Boolean {
        val db: SQLiteDatabase = readableDatabase
        var cursor: Cursor? = null
        var flag = false
        try {
            cursor = db.rawQuery("select id, password from memberTBL where id = '${id}' ", null)
            if (cursor.moveToFirst()) {
                if (cursor.getString(0).equals(id)) flag = true
                Log.d("member_sqlite", " ${id} selectCheckID 성공 ")
            }
        } catch (e: SQLException) {
            Log.d("member_sqlite", " ${id} selectCheckID 실패 ")
            flag = false
        }finally {
            db.close()
        }
        return flag
    }

    // 멤버테이블의 로그인 기능
    fun selectLogin(id: String, password: String): Boolean {
        val db: SQLiteDatabase = readableDatabase
        var cursor: Cursor? = null
        var flag = false
        try {
            cursor = db.rawQuery(
                "select id, password from memberTBL where id = '${id}' and password = '${password}'",
                null
            )
            if (cursor.moveToFirst()) {
                if (cursor.getString(0).equals(id) && cursor.getString(1).equals(password)) flag =
                    true
                Log.d("member_sqlite", " ${id} 로그인 성공 ")
            }
        } catch (e: SQLException) {
            Log.d("member_sqlite", " ${id} 중복")
            flag = false
        }finally {
            db.close()
        }
        return flag
    }

    //memberTBL id record를 가져오기(회원정보 수정 update 에 사용)
    fun selectID(id: String): Member? {
        val db: SQLiteDatabase = readableDatabase
        var cursor: Cursor? = null
        var member: Member? = null

        try {
            cursor = db.rawQuery("select * from memberTBL where id = '${id}' ", null)
            if (cursor.moveToFirst()) {
                Log.d("member_sqlite", " ${id} selectCheckID 성공 ")
                member = Member(
                    cursor.getString(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getString(6)
                )
            }
        } catch (e: SQLException) {
            Log.d("member_sqlite", " ${id} selectCheckID 실패 ")
        }finally {
            db.close()
        }
        return member
    }

    //memberTBL 모든 레코드 가져오기
    fun selectAll(): MutableList<Member> {
        val db: SQLiteDatabase = readableDatabase
        var cursor: Cursor? = null
        val dataList: MutableList<Member> = mutableListOf<Member>()

        try {
            cursor = db.rawQuery("select * from memberTBL", null)
            if (cursor != null) {
                Log.d("member_sqlite", " selectAll 성공 ")
                while (cursor.moveToNext()) {
                    val member = Member(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6)
                    )
                    dataList.add(member)
                }
            }
        } catch (e: SQLException) {
            Log.d("member_sqlite", " selectAll 실패 ")
        }finally {
            db.close()
        }
        return dataList
    }

    // 멤버테이블의 삭제기능
    fun delete(id: String): Boolean {
        val db: SQLiteDatabase = writableDatabase
        var flag = false
        try {
            db.execSQL("delete from memberTBL where id = '${id}'")
            flag = true
        } catch (e: SQLException) {
            Log.d("member_sqlite", " ${id} 삭제실패")
            flag = false
        }finally {
            db.close()
        }
        Toast.makeText(context, "${id} 회원삭제 성공", Toast.LENGTH_SHORT).show()
        Log.d("member_sqlite", " ${id} delete 성공")
        return true
    }

    //memberTBL 수정기능
    fun update(member: Member?): Boolean {
        val db: SQLiteDatabase = writableDatabase
        var flag = false
        if (member != null) {
            try {
                db.execSQL("update memberTBL set phone = '${member?.phone}', password = '${member?.password}', level = '${member?.level}' where id = '${member?.id}'")
                Log.d("member_sqlite", " ${member?.id} update 성공")
                flag = true
            } catch (e: SQLException) {
                Log.d("member_sqlite", " ${member?.id} update 실패")
                flag = false
            }finally {
                db.close()
            }
        }
        return true
    }
}