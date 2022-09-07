package com.example.persistencia

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView

class UserAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var userData = arrayListOf<User>()
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context=parent.context

        val viewHolder : RecyclerView.ViewHolder
        val inflater = LayoutInflater.from(parent.context)

        val view=inflater.inflate(R.layout.list_item_person,parent,false)
        viewHolder= ViewHolderUsr(view)

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
        })

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
}

class ViewHolderUsr (itemView: View) : RecyclerView.ViewHolder(itemView){
    val tvName : TextView = itemView.findViewById(R.id.tvItemName)
    val tvAddress : TextView = itemView.findViewById(R.id.tvAdres)
    val tvSex: TextView = itemView.findViewById(R.id.tvSex)
    val btnDel: ImageButton = itemView.findViewById(R.id.btnDel)
    val container: ConstraintLayout =  itemView.findViewById(R.id.clItemPerson)
}