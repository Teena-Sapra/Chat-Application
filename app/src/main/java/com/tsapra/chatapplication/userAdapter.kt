package com.tsapra.chatapplication

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.compose.material3.contentColorFor
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth

class userAdapter(val context: Context, val userList:ArrayList<user>): RecyclerView.Adapter<userAdapter.userViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): userViewHolder {
        val view:View= LayoutInflater.from(context).inflate(R.layout.user_layout, parent, false)
        return userViewHolder(view)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: userViewHolder, position: Int) {
        val currUser= userList[position]
        holder.nameEdit.text= currUser.name
        holder.itemView.setOnClickListener{
            val intent= Intent(context, ChatActivity::class.java)
            intent.putExtra("name", currUser.name)
            intent.putExtra("uid", currUser.uid)
            context.startActivity(intent)
        }

    }
    class userViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val nameEdit = itemView.findViewById<TextView>(R.id.nameEdit)

    }

}