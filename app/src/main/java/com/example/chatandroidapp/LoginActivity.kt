package com.example.chatandroidapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.view.marginTop
import androidx.lifecycle.lifecycleScope
import com.example.chatandroidapp.databinding.ActivityLoginBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity : AppCompatActivity() {

    private var binding: ActivityLoginBinding? = null
    private lateinit var auth: FirebaseAuth
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        progressBar = binding?.loadingBar!!

        auth = Firebase.auth

        if (auth.currentUser != null){
            startActivity(Intent(applicationContext, MainActivity::class.java))
        }
        binding?.buttonSubmit?.setOnClickListener {

            val email = binding?.editMail?.text.toString()
            val password = binding?.editPassword?.text.toString()
            if (email.isNotEmpty() && password.isNotEmpty()){

                it.visibility = View.INVISIBLE
                progressBar.visibility = View.VISIBLE
                binding?.buttonSubmit?.visibility = View.INVISIBLE

                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { result ->
                    if (result.isSuccessful){
                        startActivity(Intent(applicationContext, MainActivity::class.java))
                    }
                    else{
                        it.visibility = View.VISIBLE
                        progressBar.visibility = View.INVISIBLE
                        binding?.buttonSubmit?.visibility = View.VISIBLE
                        Toast.makeText(applicationContext, result.exception?.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
            else{
                showToast("Field is empty")
            }
        }
        binding?.linkSign?.setOnClickListener {
            startActivity(Intent(this, RegistrationActivity::class.java))
        }
    }

    private fun showToast(text: String){
        Toast.makeText(applicationContext,text, Toast.LENGTH_SHORT).show()
    }
    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}