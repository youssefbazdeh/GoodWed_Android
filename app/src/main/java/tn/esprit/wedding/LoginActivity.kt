package tn.esprit.wedding


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputEditText
import tn.esprit.wedding.models.loginResponse
import tn.esprit.wedding.viewmodels.LoginViewModel


class LoginActivity : AppCompatActivity() {

    lateinit var signbtn: Button
    lateinit var loginbtn: Button
    lateinit var passwordinputedit: TextInputEditText
    lateinit var emailinputedit: TextInputEditText
    lateinit var loginViewModel : LoginViewModel
    lateinit var forgot: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        signbtn = findViewById<Button>(R.id.signbtn)
        loginbtn = findViewById<Button>(R.id.loginbtn)
        passwordinputedit = findViewById<TextInputEditText>(R.id.passwordinputedit)
        emailinputedit = findViewById<TextInputEditText>(R.id.emailinputedit)
        forgot = findViewById<TextView>(R.id.forgot)

        forgot.setOnClickListener{
            startActivity(Intent(this,ForgotPasswword::class.java))
        }
        loginbtn.setOnClickListener{
            if (validationEmail()&&validationPassword()){
                login()
            }
            //startActivity(Intent(this,HomeActivity::class.java))

        }

        signbtn.setOnClickListener(){
            startActivity(Intent(this, SignupActivity::class.java))
        }

    }
    fun validationEmail(): Boolean {
        val email = emailinputedit.text.toString().trim()

        if (email.isEmpty()) {

            Toast.makeText(applicationContext, "champ obligatoire", Toast.LENGTH_SHORT).show()
            return false

        }else {

            return true
        }

    }
    fun validationPassword(): Boolean {
        val password = passwordinputedit.text.toString().trim ()
        if(password.isEmpty()) {
            Toast.makeText(applicationContext, "champ obligatoire", Toast.LENGTH_SHORT).show()
            return false
        } else {

            return true
        }}
     fun login(){
        loginViewModel= ViewModelProvider(this).get(LoginViewModel::class.java)
         loginViewModel.login(emailinputedit.text.toString().trim(),passwordinputedit.text.toString().trim())
         loginViewModel._logingLiveData.observe(this, Observer<loginResponse>{
            if (it!=null){
                Toast.makeText(applicationContext, "Login succes !", Toast.LENGTH_LONG).show()
                startActivity(Intent(this,MarriageActivity::class.java))
                finish()

            }else{
                Toast.makeText(applicationContext, "Login failed !", Toast.LENGTH_LONG).show()
            }
        })
     }
}

