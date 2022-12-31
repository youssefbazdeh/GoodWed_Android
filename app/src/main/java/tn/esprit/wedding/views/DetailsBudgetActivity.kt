package tn.esprit.wedding.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import tn.esprit.wedding.R
import tn.esprit.wedding.models.Budget
import tn.esprit.wedding.models.Checklist
import tn.esprit.wedding.models.ResponseChecklist
import tn.esprit.wedding.viewmodels.BudgetViewModel
import tn.esprit.wedding.viewmodels.ChecklistViewModel

class DetailsBudgetActivity : AppCompatActivity() {

    lateinit var listBudget: MutableList<Budget>
    lateinit var budgetViewModel: BudgetViewModel
    lateinit var nom : TextView
    lateinit var categorie : TextView
    lateinit var montant : TextView
    lateinit var note : TextView
    lateinit var updateBtn : ImageView
    lateinit var idbudget : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_budget)

        nom = findViewById(R.id.nombudget2)
        categorie = findViewById(R.id.categorie2)
        montant = findViewById(R.id.montant2)
        note = findViewById(R.id.note2)
        updateBtn = findViewById(R.id.updateBtn)
        idbudget = findViewById(R.id.idbudget)

        budgetViewModel = ViewModelProvider(this).get(BudgetViewModel::class.java)
        listBudget = ArrayList()


        updateBtn.setOnClickListener {

            var intent = Intent(this,UpdateBudgetActivity::class.java)
            val  x = idbudget.text.toString()
            Log.i("aaaaaaaaaaaaaa",x)
            intent.putExtra("id",x)

            val nom = nom.text.toString()
            intent.putExtra("nom",nom)

            val categorie = categorie.text.toString()
            intent.putExtra("categorie",categorie)

            val montant = montant.text.toString()
            intent.putExtra("montant",montant)

            val note = note.text.toString()
            intent.putExtra("note",note)

            startActivity(intent)
        }


        val id = intent.getStringExtra("id")
        Log.i("aaaaaaaaaaaa",id.toString())
        getBudgetByID(id!!)


    }
    fun getBudgetByID(id: String)  {

        budgetViewModel.getBudgetById(id)
        budgetViewModel._budgetLiveData1.observe(this, Observer<Budget?>{
            if (it!=null){

                idbudget.text = it._id
                nom.text = it.nom.toString()
                categorie.text = it.categorie.toString()
                montant.text = it.montant.toString()
                note.text = it.note.toString()

            }
        })

    }


}