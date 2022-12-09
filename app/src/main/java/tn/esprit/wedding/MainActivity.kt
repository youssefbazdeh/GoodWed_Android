package tn.esprit.wedding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    lateinit var login: TextView
    lateinit var Signbtn: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        login = findViewById<TextView>(R.id.login)
        login.setOnClickListener(){
            startActivity(Intent(this, LoginActivity::class.java))
        }
        Signbtn = findViewById<Button>(R.id.signbtn)
        Signbtn.setOnClickListener(){
            startActivity(Intent(this, SignupActivity::class.java))
        }
    }
}