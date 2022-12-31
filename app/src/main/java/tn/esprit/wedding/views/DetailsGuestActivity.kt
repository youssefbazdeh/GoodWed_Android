package tn.esprit.wedding.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import org.w3c.dom.Text
import tn.esprit.wedding.R
import tn.esprit.wedding.models.Budget
import tn.esprit.wedding.models.Guest
import tn.esprit.wedding.viewmodels.GuestViewModel

class DetailsGuestActivity : AppCompatActivity() {
    lateinit var idguest : TextView
    lateinit var updateBtn : ImageView
    lateinit var nameguest : TextView
    lateinit var lastnameguest : TextView
    lateinit var sexeguest : TextView
    lateinit var groupeguest : TextView
    lateinit var phoneguest : TextView
    lateinit var emailguest : TextView
    lateinit var adresseguest : TextView
    lateinit var noteguest : TextView
    lateinit var listGuest : MutableList<Guest>
    lateinit var guestViewModel: GuestViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_guest)

        idguest = findViewById(R.id.idguest)
        nameguest = findViewById(R.id.nameguest2)
        lastnameguest = findViewById(R.id.lastnameguest2)
        sexeguest = findViewById(R.id.sexeguest2)
        groupeguest = findViewById(R.id.groupeguest2)
        phoneguest = findViewById(R.id.phoneguest2)
        emailguest = findViewById(R.id.emailguest2)
        adresseguest = findViewById(R.id.adresseguest2)
        noteguest = findViewById(R.id.noteguest2)
        updateBtn = findViewById(R.id.updateBtn)
        guestViewModel = ViewModelProvider(this).get(GuestViewModel::class.java)
        listGuest = ArrayList()


        updateBtn.setOnClickListener {

            var intent = Intent(this,UpdateGuestActivity::class.java)
            val  x = idguest.text.toString()
            Log.i("aaaaaaaaaaaaaa",x)
            intent.putExtra("id",x)

            val nom = nameguest.text.toString()
            intent.putExtra("nom",nom)

            val last = lastnameguest.text.toString()
            intent.putExtra("lastname",last)

            val sexe = sexeguest.text.toString()
            intent.putExtra("sexe",sexe)

            val groupe = groupeguest.text.toString()
            intent.putExtra("groupe",groupe)

            val phone = phoneguest.text.toString()
            intent.putExtra("phone",phone)

            val email = emailguest.text.toString()
            intent.putExtra("email",email)

            val adresse = adresseguest.text.toString()
            intent.putExtra("adresse",adresse)

            val note = noteguest.text.toString()
            intent.putExtra("note",note)

            startActivity(intent)
        }

        val id = intent.getStringExtra("id")
        Log.i("aaaaaaaaaaaa",id.toString())
        getGuestByID(id!!)

    }
    fun getGuestByID(id: String)  {

        guestViewModel.getGuesttById(id)
        guestViewModel._guestLiveData.observe(this, Observer<Guest?>{
            if (it!=null){

                idguest.text = it._id
                nameguest.text = it.name.toString()
                lastnameguest.text = it.lastname.toString()
                sexeguest.text = it.sexe.toString()
                groupeguest.text = it.groupe.toString()
                phoneguest.text = it.phone.toString()
                emailguest.text = it.email.toString()
                adresseguest.text = it.adresse.toString()
                noteguest.text = it.note.toString()

            }
        })

    }
}