package tn.esprit.wedding.views

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputEditText
import tn.esprit.wedding.R
import tn.esprit.wedding.models.Budget
import tn.esprit.wedding.models.Guest
import tn.esprit.wedding.viewmodels.AddGuestViewModel
import tn.esprit.wedding.viewmodels.GuestViewModel
import tn.esprit.wedding.viewmodels.UpdateGuestViewModel
import java.util.ArrayList

class UpdateGuestActivity : AppCompatActivity() {
    var arrayAdapter: ArrayAdapter<String>? = null
    var arrayAdapter1: ArrayAdapter<String>? = null
    lateinit var listNote: ArrayList<String>
    lateinit var listSexe: ArrayList<String>
    lateinit var name : TextInputEditText
    lateinit var lastname : TextInputEditText
    lateinit var groupe : TextInputEditText
    lateinit var phone : TextInputEditText
    lateinit var email : TextInputEditText
    lateinit var adresse : TextInputEditText
    lateinit var txt_type_service : AutoCompleteTextView
    lateinit var genre : AutoCompleteTextView
    lateinit var ajouter : Button
    lateinit var updateGuestViewModel: UpdateGuestViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_guest)


        name = findViewById(R.id.nameguest)
        lastname = findViewById(R.id.lastnameguest)
        genre = findViewById(R.id.sexe)
        groupe = findViewById(R.id.groupeguest)
        phone = findViewById(R.id.phoneguest)
        email = findViewById(R.id.emailguest)
        adresse = findViewById(R.id.adresseguest)
        txt_type_service = findViewById(R.id.txt_type_service)
        ajouter = findViewById(R.id.ajouterguest)
        updateGuestViewModel = ViewModelProvider(this).get(UpdateGuestViewModel::class.java)


        var id = intent.getStringExtra("id")
        var nom = intent.getStringExtra("nom")
        name.setText(nom)
        var last = intent.getStringExtra("lastname")
        lastname.setText(last)
        var sex = intent.getStringExtra("sexe")
        genre.setText(sex)
        var grp = intent.getStringExtra("groupe")
        groupe.setText(grp)
        var ph = intent.getStringExtra("phone")
        phone.setText(ph)
        var em = intent.getStringExtra("email")
        email.setText(em)
        var adr = intent.getStringExtra("adresse")
        adresse.setText(adr)
        var not = intent.getStringExtra("note")
        txt_type_service.setText(not)

        serviceTypeDropdown()
        serviceTypeDropdown1()
        ajouter.setOnClickListener {
            UpdateGuest(id!!)
            finish()
        }


    }
    private fun serviceTypeDropdown(){
        listNote = ArrayList<String>()
        listNote.add("Confirmed")
        listNote.add("Not Confirmed")
        arrayAdapter = ArrayAdapter<String>(
            this,
            R.layout.item_dropdown,
            listNote
        )
        txt_type_service.setAdapter(arrayAdapter)
        txt_type_service.setOnItemClickListener(object :
            AdapterView.OnItemSelectedListener,
            AdapterView.OnItemClickListener {
            override fun onItemSelected(
                p0: AdapterView<*>?, p1: View?, position: Int, id: Long) {
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
            override fun onItemClick(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
            }
        })
    }
    private fun serviceTypeDropdown1(){
        listSexe = ArrayList<String>()
        listSexe.add("Homme")
        listSexe.add("Femme")
        listSexe.add("Autre")
        arrayAdapter1 = ArrayAdapter<String>(
            this,
            R.layout.item_dropdown,
            listSexe
        )
        genre.setAdapter(arrayAdapter1)
        genre.setOnItemClickListener(object :
            AdapterView.OnItemSelectedListener,
            AdapterView.OnItemClickListener {
            override fun onItemSelected(
                p0: AdapterView<*>?, p1: View?, position: Int, id: Long) {
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
            override fun onItemClick(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
            }
        })
    }
    private fun UpdateGuest (id : String){
        val name = name.text.toString().trim()
        val last = lastname.text.toString().trim()
        val ph = Integer.parseInt(phone.text.toString().trim())
        val sex = genre.text.toString().trim()
        val grp = groupe.text.toString().trim()
        val adr = adresse.text.toString().trim()
        val not = txt_type_service.text.toString().trim()
        val em = email.text.toString().trim()
        val guest = Guest(id,name,last,sex,grp,ph,em,adr,not,",0")
        updateGuestViewModel.updateGuest(id,guest)

    }
}