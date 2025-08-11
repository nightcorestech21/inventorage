package com.example.invent_zeva

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth

class AccInfoActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_acc_info)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        auth = FirebaseAuth.getInstance()

        // Tombol About
        val aboutBtn = findViewById<ImageButton>(R.id.imageButton5)
        aboutBtn.setOnClickListener {
            startActivity(Intent(this, AboutActivity::class.java))
        }

        // Tombol Reset Password
        val resetBtn = findViewById<ImageButton>(R.id.imageButton9)
        resetBtn.setOnClickListener {
            val user = auth.currentUser
            if (user != null && user.email != null) {
                auth.sendPasswordResetEmail(user.email!!)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Link reset dikirim ke ${user.email}", Toast.LENGTH_LONG).show()
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "Gagal mengirim email reset", Toast.LENGTH_SHORT).show()
                    }
            } else {
                Toast.makeText(this, "User belum login atau tidak ada email", Toast.LENGTH_SHORT).show()
            }
        }

        // Tombol Logout
        val logoutBtn = findViewById<ImageButton>(R.id.imageButton10)
        logoutBtn.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Konfirmasi Logout")
                .setMessage("Yakin mau keluar dari akun ini?")
                .setPositiveButton("Ya") { _, _ ->
                    auth.signOut()
                    val intent = Intent(this, LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()
                }
                .setNegativeButton("Batal", null)
                .show()
        }
    }
}
