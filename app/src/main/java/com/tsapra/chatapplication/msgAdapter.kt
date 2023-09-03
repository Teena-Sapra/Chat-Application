package com.tsapra.chatapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.google.firebase.auth.FirebaseAuth
import org.w3c.dom.Text

class msgAdapter(val context:Context, val msgList:ArrayList<msg>):RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val ITEM_R=1
    val ITEM_S=2
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if(viewType == 1){
            val view:View = LayoutInflater.from(context).inflate(R.layout.received, parent, false)
            return receiveViewHolder(view)
        }else{
            val view:View = LayoutInflater.from(context).inflate(R.layout.sent, parent , false)
            return sentViewHolder(view)
        }
    }

    override fun getItemCount(): Int {
        return msgList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currMsg = msgList[position]
        if(holder.javaClass==sentViewHolder::class.java){
            val viewHolder= holder as sentViewHolder
            holder.sentMsg.text = currMsg.message
        } else{
            val viewHolder=holder as receiveViewHolder
            holder.receivedMsg.text = currMsg.message
        }
    }

    override fun getItemViewType(position: Int): Int {
        val currMsg= msgList[position]
        if(FirebaseAuth.getInstance().currentUser?.uid.equals(currMsg.senderId)){
            return ITEM_S
        }else{
            return ITEM_R
        }
    }
    class sentViewHolder(itemView : View):RecyclerView.ViewHolder(itemView){
        val sentMsg = itemView.findViewById<TextView>(R.id.sentMsgEdit)
    }
    class receiveViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        val receivedMsg= itemView.findViewById<TextView>(R.id.receivedMsgEdit)
    }
}