package tn.esprit.wedding

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputEditText
import tn.esprit.wedding.models.User
import tn.esprit.wedding.viewmodels.SignUpViewModel
import java.text.SimpleDateFormat
import java.util.*



class SignupActivity : AppCompatActivity() {
    private lateinit var pickDateBtn: Button
    private lateinit var dateTv: TextView
    lateinit var signbtn: Button
    lateinit var fullnameinputedit: TextInputEditText
    lateinit var usernameinputedit: TextInputEditText
    lateinit var emailinputedit: TextInputEditText
    lateinit var passwordinputedit: TextInputEditText
    lateinit var signUpViewModel: SignUpViewModel
    val myFormat = "dd-MM-yyyy"
    val sdf = SimpleDateFormat(myFormat, Locale.getDefault())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        pickDateBtn = findViewById(R.id.pickDateBtn)
        dateTv = findViewById(R.id.dateTv)
        signbtn = findViewById(R.id.signbtn)
        fullnameinputedit = findViewById(R.id.fullnameinputedit)
        usernameinputedit = findViewById(R.id.usernameinputedit)
        emailinputedit = findViewById(R.id.emailinputedit)
        passwordinputedit = findViewById(R.id.passwordinputedit)



        signbtn.setOnClickListener{

            if (validationFullname()&& validationEmail()&&validationUsername()&&validationPassword()){
                SignUp()

            }

        }


        val myCalendar = Calendar.getInstance()

        val datePicker = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, month)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateLabel(myCalendar)
        }

            pickDateBtn.setOnClickListener {
            DatePickerDialog(this,datePicker, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show()
        }
        }

    fun validationFullname(): Boolean {
        val usename = usernameinputedit.text.toString().trim()

        if (usename.isEmpty()) {

            Toast.makeText(this, "obligatoire username", Toast.LENGTH_SHORT).show()
            return false

        }else
            return true
    }

    private fun validationEmail(): Boolean {
        val email = emailinputedit.text.toString().trim()
        if(email.isEmpty()) {
            Toast.makeText(this, "obligatoire ", Toast.LENGTH_SHORT).show()
            return false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "verifier votre adresse", Toast.LENGTH_SHORT).show()
            return false
        } else {

            return true
        }}

    private fun validationPassword(): Boolean {
        val password = passwordinputedit.text.toString().trim ()
        if(password.isEmpty()) {
            Toast.makeText(this, "obligatoire ", Toast.LENGTH_SHORT).show()
            return false
        } else {

            return true
        }}

    private fun validationUsername(): Boolean {
        val username = usernameinputedit.text.toString().trim ()
        if(username.isEmpty()) {
            Toast.makeText(this, "obligatoire ", Toast.LENGTH_SHORT).show()
            return false
        } else {
            return true
        }}

    private  fun SignUp(){
        val dd = sdf.parse(dateTv.text.toString())
        val user = User("", fullnameinputedit.text.toString().trim(),usernameinputedit.text.toString().trim(),emailinputedit.text.toString(),"user",dd,passwordinputedit.text.toString().trim(),0)

        signUpViewModel= ViewModelProvider(this).get(SignUpViewModel::class.java)
        signUpViewModel.signUp(user)
        signUpViewModel._signUpLiveData.observe(this, androidx.lifecycle.Observer<User>{
            if (it!=null){
                Toast.makeText(applicationContext, "ajout succes !", Toast.LENGTH_LONG).show()
                startActivity(Intent(this, LoginActivity::class.java))
                finish()

            }else{
                Toast.makeText(applicationContext, "Login failed !", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun updateLabel(myCalendar: Calendar) {

        dateTv.setText(sdf.format(myCalendar.time))


    }
  /*  private fun dateToString(format: String): String{
        val myFormat = "dd-MM-yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.getDefault())
        return sdf.format(this)
    }*/

}
