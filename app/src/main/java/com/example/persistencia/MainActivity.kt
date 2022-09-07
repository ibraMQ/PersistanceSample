package com.example.persistencia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.persistencia.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var edtName: EditText
    private lateinit var edtAddress: EditText
    private lateinit var rgSex: RadioGroup
    private lateinit var btnAdd: Button
    private lateinit var rvUsers: RecyclerView
    private lateinit var usrList: ArrayList<User>
    private lateinit var userAdapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        usrList = getUsrList()
        initComponents()
        setListeners()
    }

    private fun setListeners() {
        val onAdd=View.OnClickListener{
            val name = edtName.text.toString()
            val adres = edtAddress.text.toString()
            var sex = "M"
            val selectedSex: Int = rgSex.checkedRadioButtonId
            if(selectedSex == binding.rbFemale.id){
                sex = "F"
            }
            val newUsr=User(name,adres,sex)
            userAdapter.addItem(newUsr)
        }
        btnAdd.setOnClickListener(onAdd)
    }

    private fun getUsrList(): ArrayList<User> {
        return arrayListOf(
            User("Luis","Benito Juarez, Alvaro Obregon, num 24","M"),
            User("Regina","Torreon, Hidalgo, num 15","F")
        )
    }

    private fun initComponents() {
        edtName=binding.edtName
        edtAddress=binding.edtAdrres
        btnAdd=binding.btnAdd
        rvUsers = binding.rvUserList
        userAdapter = UserAdapter()
        rvUsers.adapter=userAdapter
        userAdapter.userData=usrList
        rgSex=binding.rgSex
    }
}