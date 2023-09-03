package com.tsapra.chatapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.tsapra.chatapplication.databinding.ActivitySignupBinding
import kotlinx.android.synthetic.main.activity_signup.SignupBtn
import kotlinx.android.synthetic.main.activity_signup.emailSignup
import kotlinx.android.synthetic.main.activity_signup.nameSignup
import kotlinx.android.synthetic.main.activity_signup.passSignup

class SignupActivity : AppCompatActivity() {
    private lateinit var binding:ActivitySignupBinding
    private lateinit var mAuth:FirebaseAuth
    private lateinit var mDbRef:DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        mAuth=FirebaseAuth.getInstance()
        SignupBtn.setOnClickListener{
            val name= nameSignup.text.toString()
            val email= emailSignup.text.toString().trim()
            val password= passSignup.text.toString()
            signUp(name, email,password)
        }
    }
    private fun signUp(name:String, email:String, password:String){
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    addUserToDatabase(name, email, mAuth.currentUser?.uid!!)
                    val intent= Intent(this@SignupActivity, MainActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this@SignupActivity, "Some error occurred", Toast.LENGTH_SHORT).show()
                }
            }
    }
    private fun addUserToDatabase(name:String, email:String, uid:String){
        mDbRef= FirebaseDatabase.getInstance().getReference()
        mDbRef.child("users").child(uid).setValue(user(name,email,uid))
    }
}