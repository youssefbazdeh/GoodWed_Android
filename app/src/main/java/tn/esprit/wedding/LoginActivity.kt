package tn.esprit.wedding


import android.content.Intent
import android.content.SharedPreferences
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

const val ID="id"
const val PREF_LOGIN ="Remember Me"
const val  FULLNAME="Fullname"
const val  EMAIL="Email"
const val PASSWORD="Password"
const val TOKEN="Token"
const val DATE="Date de naissance"
const val  USERNAME="Username"
const val  USER="User"


class LoginActivity : AppCompatActivity() {

    lateinit var signbtn: Button
    lateinit var loginbtn: Button
    lateinit var passwordinputedit: TextInputEditText
    lateinit var emailinputedit: TextInputEditText
    lateinit var loginViewModel : LoginViewModel
    lateinit var forgot: TextView
    lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        prefs = getSharedPreferences(PREF_LOGIN,MODE_PRIVATE)
        signbtn = findViewById<Button>(R.id.signbtn)
        loginbtn = findViewById<Button>(R.id.loginbtn)
        passwordinputedit = findViewById<TextInputEditText>(R.id.passwordinputedit)
        emailinputedit = findViewById<TextInputEditText>(R.id.emailinputedit)
        forgot = findViewById<TextView>(R.id.forgot)


        if (prefs.getString(USERNAME,"")!!.isNotEmpty() and prefs.getString(PASSWORD,"")!!.isNotEmpty()){
            val intent = Intent(applicationContext,HomeActivity::class.java)
            startActivity(intent)
        }

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
                //Edit the SharedPreferences by putting all the data
                prefs.edit().apply(){
                    putString(ID, it.user?._id)
                    putString(FULLNAME,it.user?.fullname)
                    putString(USERNAME,it.user?.username)
                    putString(PASSWORD,it.user?.password)
                    putString(DATE,it.user?.datedenaissance.toString())
                    putString(TOKEN,it.accessToken)
                    putString(EMAIL,it.user?.email)
                    apply()
                }
                Toast.makeText(applicationContext, "Login succes !"+it.accessToken, Toast.LENGTH_LONG).show()
                startActivity(Intent(this,MarriageActivity::class.java))
                finish()

            }else{
                Toast.makeText(applicationContext, "Login failed !", Toast.LENGTH_LONG).show()
            }
        })
     }
}

