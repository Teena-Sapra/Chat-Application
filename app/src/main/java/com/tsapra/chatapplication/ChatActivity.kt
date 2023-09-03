package com.tsapra.chatapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_chat.chatRv
import kotlinx.android.synthetic.main.activity_chat.msgBox
import kotlinx.android.synthetic.main.activity_chat.sendBtn

class ChatActivity : AppCompatActivity() {
    private lateinit var adapter: msgAdapter
    private lateinit var msgList:ArrayList<msg>
    private lateinit var mDbRef : DatabaseReference
    var receiverRoom : String? =null
    var senderRoom : String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        val name= intent.getStringExtra("name")
        val receiverUid = intent.getStringExtra("uid")
        val senderUid= FirebaseAuth.getInstance().currentUser?.uid
        mDbRef = FirebaseDatabase.getInstance().getReference()
        senderRoom= receiverUid + senderUid
        receiverRoom = senderUid + receiverUid
        supportActionBar?.title= name
        msgList= ArrayList()
        adapter= msgAdapter(this, msgList)
        chatRv.layoutManager = LinearLayoutManager(this)
        chatRv.adapter = adapter
        //adding msg to screen
        mDbRef.child("chats").child(senderRoom!!).child("messages").addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                msgList.clear()
                for(postSnapshot in snapshot.children){
                    val message = postSnapshot.getValue(msg::class.java)
                    msgList.add(message!!)

                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })




        //addind msg to database
        sendBtn.setOnClickListener{
            val message = msgBox.text.toString()
            val msgObject = msg(message, senderUid!!)
            mDbRef.child("chats").child(senderRoom!!).child("messages").push().setValue(msgObject).addOnSuccessListener {
                mDbRef.child("chats").child(receiverRoom!!).child("messages").push().setValue(msgObject)
            }
            msgBox.setText("")


        }
    }
}