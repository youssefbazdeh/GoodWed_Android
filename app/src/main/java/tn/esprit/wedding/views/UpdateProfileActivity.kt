package tn.esprit.wedding.views

import android.app.DatePickerDialog
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputEditText
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import tn.esprit.wedding.*
import tn.esprit.wedding.models.ResponseUser
import tn.esprit.wedding.models.User
import tn.esprit.wedding.viewmodels.UpdateProfileViewModel
import java.text.SimpleDateFormat
import java.util.*

class UpdateProfileActivity : AppCompatActivity() {

     lateinit var usernameinputedit  : TextInputEditText
    lateinit var fullnameinputedit : TextInputEditText
    lateinit var emailinputedit : TextInputEditText
    lateinit var passwordinputedit : TextInputEditText
    lateinit var confBtn : Button
    lateinit var pickDateBtn1 : Button
    lateinit var dateTv1 : TextView
    lateinit var updateProfileViewModel: UpdateProfileViewModel
    lateinit var prefs : SharedPreferences

    val myFormat = "dd-MM-yyyy"
    val sdf = SimpleDateFormat(myFormat, Locale.getDefault())



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_profile)

        usernameinputedit = findViewById(R.id.usernameinputedit)
        fullnameinputedit = findViewById(R.id.fullnameinputedit)
        emailinputedit = findViewById(R.id.emailinputedit)
        passwordinputedit = findViewById(R.id.passwordinputedit)
        confBtn = findViewById(R.id.confBtn)
        pickDateBtn1 = findViewById(R.id.pickDateBtn1)
        dateTv1 = findViewById(R.id.dateTv1)
        prefs = getSharedPreferences(PREF_LOGIN, AppCompatActivity.MODE_PRIVATE)


        val id = prefs.getString(ID,"")
        Log.i("idddd",id.toString())
        getUserById(id!!)

        val myCalendar = Calendar.getInstance()

        val datePicker = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, month)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateLabel(myCalendar)
        }

        pickDateBtn1.setOnClickListener {
            DatePickerDialog(this,datePicker, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show()
        }

        confBtn.setOnClickListener {
            UpdateProfile(id!!)
        }



    }

    private fun getUserById(user_id: String) {
        updateProfileViewModel= ViewModelProvider(this).get(UpdateProfileViewModel::class.java)
        updateProfileViewModel.getUserById(user_id)
        updateProfileViewModel._UserLiveData.observe(this, Observer<ResponseUser?> {
            if (it!=null){
                usernameinputedit.setText(it.username)
                fullnameinputedit.setText(it.fullname)
                emailinputedit.setText(it.email)
                dateTv1.setText(sdf.format(it.datedenaissance).toString())
                passwordinputedit.setText(it.password)
            }else{
                Toast.makeText(this,"Affichage ne marche pas",Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun UpdateProfile(user_id: String) {

        updateProfileViewModel= ViewModelProvider(this).get(UpdateProfileViewModel::class.java)

        val dd = sdf.parse(dateTv1.text.toString())

        val username = usernameinputedit.text.toString().trim()
        val fullname = fullnameinputedit.text.toString().trim()
        val email = emailinputedit.text.toString().trim()
        val passwword = passwordinputedit.text.toString().trim()
        val user = User(user_id,username,fullname,email,"",dd,"",0)

        updateProfileViewModel.updateProfile(user_id ,user)

        Toast.makeText(applicationContext, "UPDATED !!!",Toast.LENGTH_SHORT).show()
        finish()


    }

    private fun updateLabel(myCalendar: Calendar) {
        dateTv1.setText(sdf.format(myCalendar.time))
    }
}