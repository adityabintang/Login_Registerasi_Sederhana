    package com.example.loginregisterasisederhana



import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        auth = FirebaseAuth.getInstance()

        val btn_register1 = findViewById<Button>(R.id.btn_register1)
        val btn_login = findViewById<Button>(R.id.btn_login)
        val inputemail = findViewById<EditText>(R.id.inputEmail)
        val inputpass = findViewById<EditText>(R.id.inputPassword)

        //TODO: handle button click btn_login
        btn_login.setOnClickListener {

            val email = inputemail.text.toString().trim()
            val password = inputpass.text.toString().trim()

            if (email.isEmpty() || password.isEmpty() || password.length == 8) {

                inputEmail.error = "Harus Diisi"
                inputEmail.requestFocus()

                inputPassword.error = "Harus berisi 8 karakter"
                inputPassword.requestFocus()

                return@setOnClickListener
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                inputEmail.error = "Email tidak valid"
                inputEmail.requestFocus()
                return@setOnClickListener
            }
//            if (email == "" && password == "") {
//                val intent = Intent(this, Welcome::class.java)
//                startActivity(intent)
//                finish()
//            }
//            if (email == "" && password == "") {
//
//                Toast.makeText(this, "Berhasil Login", Toast.LENGTH_LONG).show()
//            } else {
//
//                Toast.makeText(this, "Password atau email salah", Toast.LENGTH_LONG).show()
//            }

            loginUser(email, password)
        }

        //TODO: handle button click btn_register1
        btn_register1.setOnClickListener {

            val intent = Intent(this, Registrasi::class.java)
            startActivity(intent)


//            startActivity(Intent(this, Registrasi::class.java))
        }

    }

    private fun loginUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) {
                    if (it.isSuccessful) {
                        Intent(this, Welcome::class.java).also {
                            it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            startActivity(it)
                        }
                    } else {
                        Toast.makeText(this, it.exception?.message, Toast.LENGTH_SHORT).show()
                    }
                }
    }

}
