package com.tsapra.chatapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.tsapra.chatapplication.databinding.ActivityLoginBinding
import kotlinx.android.synthetic.main.activity_login.signupBtn
import kotlinx.android.synthetic.main.activity_login.emailLogin
import kotlinx.android.synthetic.main.activity_login.loginBtn
import kotlinx.android.synthetic.main.activity_login.passLogin
import kotlinx.android.synthetic.main.activity_login.signupBtn
import kotlinx.android.synthetic.main.activity_signup.SignupBtn

class LoginActivity : AppCompatActivity() {
    private lateinit var binding:ActivityLoginBinding
    private lateinit var mAuth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        mAuth=FirebaseAuth.getInstance()
        signupBtn.setOnClickListener{
            val intent= Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }
        loginBtn.setOnClickListener{
            val email= emailLogin.text.toString().trim()
            val password= passLogin.text.toString()
            login(email,password)
        }
    }
    private fun login(email:String, password:String){
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val intent=Intent(this@LoginActivity, MainActivity::class.java)
                    finish()
                    startActivity(intent)
                } else {
                    Toast.makeText(this@LoginActivity, "User does not exist", Toast.LENGTH_SHORT).show()
                }
            }
    }
}