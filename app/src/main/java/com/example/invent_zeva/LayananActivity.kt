package com.example.invent_zeva

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class LayananActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_layanan)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val layananHardware = findViewById<ImageButton>(R.id.btnLayananHard)
        val layananSoftware = findViewById<ImageButton>(R.id.btnLayananSoft)
        val layananPerbaikan = findViewById<ImageButton>(R.id.btnLayananPerbaikan)

        layananHardware.setOnClickListener {
            val intent = Intent(this, TampilLayananHardwareActivity::class.java)
            startActivity(intent)
        }
        layananSoftware.setOnClickListener {
            val intent = Intent(this, TampilLayananSoftwareActivity::class.java)
            startActivity(intent)
        }
        layananPerbaikan.setOnClickListener {
            val intent = Intent(this, TampilLayananPerbaikanActivity::class.java)
            startActivity(intent)
        }
    }
}