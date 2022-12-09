package tn.esprit.wedding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintSet.Layout

class ForgotPasswword : AppCompatActivity() {
    lateinit var back: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_passwword)
        back = findViewById<ImageView>(R.id.back)
        back.setOnClickListener() {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}