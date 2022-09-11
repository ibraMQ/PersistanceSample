package com.example.persistencia.ui.Update

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.room.Room
import com.example.persistencia.User
import com.example.persistencia.UserDao
import com.example.persistencia.UserDatabase
import com.example.persistencia.databinding.FragmentUpdateBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UpdateFragment: Fragment() {
    private var _binding: FragmentUpdateBinding? = null
    private val binding get() = _binding!!

    private lateinit var edtName: EditText
    private lateinit var edtAddress: EditText
    private lateinit var btnUpdate: Button
    private lateinit var rgSex: RadioGroup

    private val db by lazy {
        Room.databaseBuilder(
            requireContext(),
            UserDatabase::class.java,
            "user_database"
        ).build()
    }
    private lateinit var userDao: UserDao

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentUpdateBinding.inflate(inflater, container, false)
        val root: View = binding.root

        initComponents()
        setArgs()
        setListeners()

        return root
    }

    private fun setListeners() {
        val onUpdate = View.OnClickListener{
            val name = edtName.text.toString()
            val address = edtAddress.text.toString()
            var sex = "M"
            val selectedSex: Int = rgSex.checkedRadioButtonId
            if(selectedSex == binding.rbFemaleUP.id){
                sex = "F"
            }

            //Log.e("Navigation", name + address + sex)
            val updatedUser = User(name,address,sex)
            updateUser(updatedUser)

            findNavController().popBackStack()
        }
        btnUpdate.setOnClickListener(onUpdate)
    }

    private fun initComponents() {
        edtName=binding.edtNameUP
        edtAddress=binding.edtAdrresUP
        btnUpdate=binding.btnUpdate
        rgSex=binding.rgSexUP
        userDao=db.userDao()
    }

    fun updateUser(user: User){
        CoroutineScope(Dispatchers.IO).launch {
            userDao.update(user)
        }
    }

    private fun setArgs() {
        val args = arguments
        val name: String = args?.get("name").toString()
        val address: String = args?.get("address").toString()
        val sex: String = args?.get("sex").toString()

        edtName.setText(name)
        edtAddress.setText(address)
        if(sex.equals("M")){
            rgSex.check(binding.rbMaleUP.id)
        }else{
            rgSex.check(binding.rbFemaleUP.id)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}