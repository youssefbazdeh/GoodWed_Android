package tn.esprit.wedding.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputEditText
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import tn.esprit.wedding.ID
import tn.esprit.wedding.PREF_LOGIN
import tn.esprit.wedding.R
import tn.esprit.wedding.models.Budget
import tn.esprit.wedding.viewmodels.AddBudgetViewModel


class AddBudgetActivity : AppCompatActivity() {


    lateinit var nombudget : TextInputEditText
    lateinit var catbudget : TextInputEditText
    lateinit var montantbudget : TextInputEditText
    lateinit var notebudget : TextInputEditText
    lateinit var btn : Button
    lateinit var addBudgetViewModel: AddBudgetViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_budget)

        nombudget = findViewById(R.id.nombudget)
        catbudget = findViewById(R.id.catbudget)
        montantbudget = findViewById(R.id.montantbudget)
        notebudget = findViewById(R.id.notebudget)
        btn = findViewById(R.id.addBtn)

        btn.setOnClickListener {
            addBudgetByIdUser(this.getSharedPreferences(PREF_LOGIN, AppCompatActivity.MODE_PRIVATE).getString(
                ID,"").toString().trim())

        }


    }

    private fun addBudgetByIdUser(user_id: String) {
        addBudgetViewModel = ViewModelProvider(this).get(AddBudgetViewModel::class.java)

        val nom = nombudget.text.toString().trim()
        val cat = catbudget.text.toString().trim()
        val montant = Integer.parseInt(montantbudget.text.toString().trim())
        val note = notebudget.text.toString().trim()
        val budget = Budget("",nom,cat,montant,note,user_id,0)


        addBudgetViewModel.addBudget(budget)
        addBudgetViewModel._addBudgetLiveData.observe(this, Observer<Budget?>{
            if (it!=null){
                Toast.makeText(applicationContext, "ajout succes !", Toast.LENGTH_LONG).show()
                finish()

            }else{
                Toast.makeText(applicationContext, "Login failed !", Toast.LENGTH_LONG).show()
            }
        })

    }
}