package com.example.invent_zeva

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class AboutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        val textEmail = findViewById<TextView>(R.id.textEmail)

        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            textEmail.text = "Email: ${user.email}"
        } else {
            textEmail.text = "Email: -"
        }
    }
}
