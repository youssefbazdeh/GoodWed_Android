package tn.esprit.wedding.views

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputEditText
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import tn.esprit.wedding.ID
import tn.esprit.wedding.PREF_LOGIN
import tn.esprit.wedding.R
import tn.esprit.wedding.models.Guest
import tn.esprit.wedding.viewmodels.AddGuestViewModel
import java.util.ArrayList

class AddGuestActivity : AppCompatActivity() {

    var arrayAdapter: ArrayAdapter<String>? = null
    var arrayAdapter1: ArrayAdapter<String>? = null
    lateinit var listNote:ArrayList<String>
    lateinit var listSexe:ArrayList<String>
    lateinit var name : TextInputEditText
    lateinit var lastname : TextInputEditText
    lateinit var groupe : TextInputEditText
    lateinit var phone : TextInputEditText
    lateinit var email : TextInputEditText
    lateinit var adresse : TextInputEditText
    lateinit var txt_type_service : AutoCompleteTextView
    lateinit var genre : AutoCompleteTextView
    lateinit var ajouter : Button
    lateinit var addGuestViewModel: AddGuestViewModel
    lateinit var preferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_guest)

        //serviceTypeDropdown()
        name = findViewById(R.id.nameguest)
        lastname = findViewById(R.id.lastnameguest)
        genre = findViewById(R.id.sexe)
        groupe = findViewById(R.id.groupeguest)
        phone = findViewById(R.id.phoneguest)
        email = findViewById(R.id.emailguest)
        adresse = findViewById(R.id.adresseguest)
        txt_type_service = findViewById(R.id.txt_type_service)
        ajouter = findViewById(R.id.ajouterguest)
        preferences = this.getSharedPreferences(PREF_LOGIN,MODE_PRIVATE)
        val id = preferences.getString(ID,"").toString().trim().toRequestBody("plain/text".toMediaTypeOrNull())
        Log.i("idddddd",preferences.getString(ID,"").toString())
        addGuestViewModel = ViewModelProvider(this).get(AddGuestViewModel::class.java)

        serviceTypeDropdown()
        serviceTypeDropdown1()


        ajouter.setOnClickListener {
            addGuest(preferences.getString(ID,"").toString())
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
    private fun addGuest(user_id : String){
        val n = name.text.toString().trim()
        val l = lastname.text.toString().trim()
        val s = genre.text.toString().trim()
        val g = groupe.text.toString().trim()
        val p = Integer.parseInt(phone.text.toString().trim())
        val e = email.text.toString().trim()
        val a = adresse.text.toString().trim()
        val no = txt_type_service.text.toString().trim()
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