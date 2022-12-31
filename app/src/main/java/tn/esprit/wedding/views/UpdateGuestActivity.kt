package tn.esprit.wedding.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import tn.esprit.wedding.R
import tn.esprit.wedding.models.Budget
import tn.esprit.wedding.models.Guest
import tn.esprit.wedding.viewmodels.GuestViewModel
import tn.esprit.wedding.viewmodels.UpdateGuestViewModel

class UpdateGuestActivity : AppCompatActivity() {
    lateinit var name : EditText
    lateinit var lastname : EditText
    lateinit var sexe : EditText
    lateinit var groupe : EditText
    lateinit var phone : EditText
    lateinit var email : EditText
    lateinit var adresse : EditText
    lateinit var note : EditText
    lateinit var ajouterguest : Button
    lateinit var updateGuestViewModel: UpdateGuestViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_guest)


        name = findViewById(R.id.nameguest)
        lastname = findViewById(R.id.lastnameguest)
        sexe = findViewById(R.id.sexeguest)
        groupe = findViewById(R.id.groupeguest)
        phone = findViewById(R.id.phoneguest)
        email = findViewById(R.id.emailguest)
        adresse = findViewById(R.id.adresseguest)
        note = findViewById(R.id.noteguest)
        ajouterguest = findViewById(R.id.ajouterguest)
        updateGuestViewModel = ViewModelProvider(this).get(UpdateGuestViewModel::class.java)


        var id = intent.getStringExtra("id")
        var nom = intent.getStringExtra("nom")
        name.setText(nom)
        var last = intent.getStringExtra("lastname")
        lastname.setText(last)
        var sex = intent.getStringExtra("sexe")
        sexe.setText(sex)
        var grp = intent.getStringExtra("groupe")
        groupe.setText(grp)
        var ph = intent.getStringExtra("phone")
        phone.setText(ph)
        var em = intent.getStringExtra("email")
        email.setText(em)
        var adr = intent.getStringExtra("adresse")
        adresse.setText(adr)
        var not = intent.getStringExtra("note")
        note.setText(not)


        ajouterguest.setOnClickListener {
            UpdateGuest(id!!)
            finish()
        }


    }
    private fun UpdateGuest (id : String){
        val name = name.text.toString().trim()
        val last = lastname.text.toString().trim()
        val ph = Integer.parseInt(phone.text.toString().trim())
        val sex = sexe.text.toString().trim()
        val grp = groupe.text.toString().trim()
        val adr = adresse.text.toString().trim()
        val not = note.text.toString().trim()
        val em = email.text.toString().trim()
        val guest = Guest(id,name,last,sex,grp,ph,em,adr,not,",0")
        updateGuestViewModel.updateGuest(id,guest)

    }
}