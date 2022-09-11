package com.example.persistencia

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.persistencia.ui.UserList.UserListFragmentDirections
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var userData = ArrayList<User>()
    private lateinit var context: Context
    private lateinit var navController: NavController

    private val db by lazy {
        Room.databaseBuilder(
            context,
            UserDatabase::class.java,
            "user_database"
        ).build()
    }
    private lateinit var userDao: UserDao

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context=parent.context

        val viewHolder : RecyclerView.ViewHolder
        val inflater = LayoutInflater.from(parent.context)
        userDao=db.userDao()

        val view=inflater.inflate(R.layout.list_item_person,parent,false)
        viewHolder= ViewHolderUsr(view)
        navController = parent.findNavController()

        return viewHolder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val user=userData[position]
        val vh = holder as ViewHolderUsr

        vh.tvSex.text=user.sex
        vh.tvName.text=user.name
        vh.tvAddress.text=user.address
        if(user.sex.equals("M")){
            vh.container.setBackgroundColor(Color.CYAN)
        }

        vh.btnDel.setOnClickListener(View.OnClickListener {
            deleteItem(user,position)
            deleteUser(user)
        })
        val onSelect = View.OnClickListener {
            navController.navigate(
                UserListFragmentDirections.actionNavigationListToNavigationUpdate(
                    user.name,user.address,user.sex
                ))
        }
        vh.container.setOnClickListener(onSelect)

    }

    override fun getItemCount() = userData.size

    fun deleteItem (user: User,position: Int){
        userData.remove(user)
        notifyItemRemoved(position)
    }

    fun addItem(user: User){
        userData.add(user)
        notifyItemInserted(userData.indexOf(user))
    }

    fun deleteUser(user: User){
        CoroutineScope(Dispatchers.IO).launch {
            userDao.delete(user)
        }
    }
}

class ViewHolderUsr (itemView: View) : RecyclerView.ViewHolder(itemView){
    val tvName : TextView = itemView.findViewById(R.id.tvItemName)
    val tvAddress : TextView = itemView.findViewById(R.id.tvAdres)
    val tvSex: TextView = itemView.findViewById(R.id.tvSex)
    val btnDel: ImageButton = itemView.findViewById(R.id.btnDel)
    val container: ConstraintLayout =  itemView.findViewById(R.id.clItemPerson)
}