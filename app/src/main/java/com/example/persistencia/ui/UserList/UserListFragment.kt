package com.example.persistencia.ui.UserList

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.persistencia.*
import com.example.persistencia.databinding.FragmentUserListBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class UserListFragment : Fragment() {
    private var _binding: FragmentUserListBinding? = null
    private val binding get() = _binding!!

    private lateinit var edtName: EditText
    private lateinit var edtAddress: EditText
    private lateinit var rgSex: RadioGroup
    private lateinit var btnAdd: Button
    private lateinit var rvUsers: RecyclerView
    private lateinit var userAdapter: UserAdapter
    private lateinit var userList : List<User>

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

        _binding = FragmentUserListBinding.inflate(inflater, container, false)
        val root: View = binding.root

        initComponents()
        setListeners()

        return root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userDao=db.userDao()
        userList= emptyList()
        getUsrList()
    }

    override fun onResume() {
        super.onResume()
        loadList()
    }

    private fun loadList() {
        userAdapter = UserAdapter()
        rvUsers.adapter=userAdapter
        userAdapter.userData=ArrayList(userList)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setListeners() {
        val onAdd=View.OnClickListener{
            //TODO: add validatons
            val name = edtName.text.toString()
            val adres = edtAddress.text.toString()
            var sex = "M"
            val selectedSex: Int = rgSex.checkedRadioButtonId
            if(selectedSex == binding.rbFemale.id){
                sex = "F"
            }
            val newUsr=User(name,adres,sex)
            userAdapter.addItem(newUsr)
            insertUser(newUsr)
        }
        btnAdd.setOnClickListener(onAdd)
    }

    private fun initComponents() {
        edtName=binding.edtName
        edtAddress=binding.edtAdrres
        btnAdd=binding.btnAdd
        rvUsers = binding.rvUserList
        rgSex=binding.rgSex
        getUsrList()
    }

    private fun insertUser(user: User) {
        CoroutineScope(Dispatchers.IO).launch {
            userDao.insert(user)
        }
    }

    private fun getUsrList() {
        CoroutineScope(Dispatchers.IO).launch {
            userDao.getUsers().distinctUntilChanged().collect(){
                it.let {
                    userList=it
                }
            }
        }
    }
}