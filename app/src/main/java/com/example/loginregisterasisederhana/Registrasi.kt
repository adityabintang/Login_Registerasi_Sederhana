package com.example.loginregisterasisederhana

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_registrasi.*

class Registrasi : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrasi)

        auth = FirebaseAuth.getInstance()

        btn_register.setOnClickListener {

            val email1 = inputEmail1.text.toString().trim()
            val password1 = inputPassword1.text.toString().trim()

            if (email1.isEmpty() || password1.isEmpty() || password1.length == 8) {
                inputEmail1.error = "Harus Diisi"
                inputEmail1.requestFocus()

                //TODO : Validasi Password
                inputPassword1.error = "Password harus lebih dari 8 karakter"
                inputPassword1.requestFocus()
//                Toast.makeText(this, "Please Insert Email and Password", Toast.LENGTH_SHORT)
//                    .show()
                return@setOnClickListener
            }

            //TODO : Validasi Email
            if (!Patterns.EMAIL_ADDRESS.matcher(email1).matches()) {
                inputEmail1.error = "Email tidak valid"
                inputEmail1.requestFocus()
            }
            //TODO : Register User
            registerUser(email1, password1)

            Toast.makeText(this, "Berhasil Registrasi", Toast.LENGTH_LONG).show()
            val intent = Intent(this, Welcome::class.java)
            startActivity(intent)
            finish()
        }

        btn_login1.setOnClickListener {
            Toast.makeText(this, "Have Already Account", Toast.LENGTH_LONG).show()
            val inten = Intent(this, MainActivity::class.java)
            startActivity(inten)
            finish()
        }
    }

    private fun registerUser(email1: String, password1: String) {
        auth.createUserWithEmailAndPassword(email1, password1)
            .addOnCompleteListener(this){
                if(it.isSuccessful) {
                   Intent(this@Registrasi, Welcome::class.java).also {
                        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                       startActivity(it)
                    }
                }else {
                    Toast.makeText(this, it.exception?.message, Toast.LENGTH_SHORT).show()
                }

            }
    }
}