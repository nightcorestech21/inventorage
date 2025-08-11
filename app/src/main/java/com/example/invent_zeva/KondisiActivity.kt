package com.example.invent_zeva

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class KondisiActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_kondisi)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btn_pc = findViewById<ImageButton>(R.id.img_pc)
        val btn_laptop = findViewById<ImageButton>(R.id.img_laptop)
        val btn_ups = findViewById<ImageButton>(R.id.img_ups)
        val btn_printer = findViewById<ImageButton>(R.id.img_printer)
        val btn_scanner = findViewById<ImageButton>(R.id.img_scanner)

        btn_pc.setOnClickListener {
            val intent = Intent(this, TabelPCActivity::class.java)
            startActivity(intent)
        }
        btn_laptop.setOnClickListener {
            val intent = Intent(this, TabelLaptopActivity::class.java)
            startActivity(intent)
        }
        btn_ups.setOnClickListener {
            val intent = Intent(this, TabelUPSActivity::class.java)
            startActivity(intent)
        }
        btn_printer.setOnClickListener {
            val intent = Intent(this, TabelPrinterActivity::class.java)
            startActivity(intent)
        }
        btn_scanner.setOnClickListener {
            val intent = Intent(this, InventScannerActivity::class.java)
            startActivity(intent)
        }
    }
}