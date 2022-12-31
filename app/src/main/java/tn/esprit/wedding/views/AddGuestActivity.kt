package tn.esprit.wedding.views

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import tn.esprit.wedding.ID
import tn.esprit.wedding.PREF_LOGIN
import tn.esprit.wedding.R
import tn.esprit.wedding.models.Guest
import tn.esprit.wedding.viewmodels.AddGuestViewModel

class AddGuestActivity : AppCompatActivity() {
    lateinit var name : EditText
    lateinit var lastname : EditText
    lateinit var sexe : EditText
    lateinit var groupe : EditText
    lateinit var phone : EditText
    lateinit var email : EditText
    lateinit var adresse : EditText
    lateinit var note : EditText
    lateinit var ajouter : Button
    lateinit var addGuestViewModel: AddGuestViewModel
    lateinit var preferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_guest)
        name = findViewById(R.id.nameguest)
        lastname = findViewById(R.id.lastnameguest)
        sexe = findViewById(R.id.sexeguest)
        groupe = findViewById(R.id.groupeguest)
        phone = findViewById(R.id.phoneguest)
        email = findViewById(R.id.emailguest)
        adresse = findViewById(R.id.adresseguest)
        note = findViewById(R.id.noteguest)
        ajouter = findViewById(R.id.ajouterguest)
        preferences = this.getSharedPreferences(PREF_LOGIN,MODE_PRIVATE)
        val id = preferences.getString(ID,"").toString().trim().toRequestBody("plain/text".toMediaTypeOrNull())
        Log.i("idddddd",preferences.getString(ID,"").toString())
        addGuestViewModel = ViewModelProvider(this).get(AddGuestViewModel::class.java)

        ajouter.setOnClickListener {
            addGuest(preferences.getString(ID,"").toString())
            finish()
        }



    }
    private fun addGuest(user_id : String){
        val n = name.text.toString().trim()
        val l = lastname.text.toString().trim()
        val s = sexe.text.toString().trim()
        val g = groupe.text.toString().trim()
        val p = Integer.parseInt(phone.text.toString().trim())
        val e = email.text.toString().trim()
        val a = adresse.text.toString().trim()
        val no = note.text.toString().trim()
        val guest = Guest("",n,l,s,g,p,e,a,no,user_id,0)

        addGuestViewModel.addGuest(guest)
        addGuestViewModel._addGuestLiveData.observe(this,Observer<Guest?>{
            Log.i("aaaaaaaaaaa",it.toString())
            if (it!=null){
                Toast.makeText(applicationContext,"Ajouter Guest success !!",Toast.LENGTH_SHORT).show()
                finish()
            }else{
                Toast.makeText(applicationContext,"Ajouter Guest failed !!",Toast.LENGTH_SHORT).show()

            }
        })


    }
}