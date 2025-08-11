package com.example.invent_zeva

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class PenangananActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_penanganan)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val pasangHardware = findViewById<ImageButton>(R.id.btnpasangHardware)
        val pasangSoftware = findViewById<ImageButton>(R.id.btnpasangSoftware)
        val perbaikanopt = findViewById<ImageButton>(R.id.btnPerbaikan)

        pasangHardware.setOnClickListener {
            val intent = Intent(this, FilterHardwareActivity::class.java)
            startActivity(intent)
        }
        pasangSoftware.setOnClickListener {
            val intent = Intent(this, FilterSoftwareActivity::class.java)
            startActivity(intent)
        }
        perbaikanopt.setOnClickListener {
            val intent = Intent(this, FilterPerbaikanActivity::class.java)
            startActivity(intent)
        }

    }
}