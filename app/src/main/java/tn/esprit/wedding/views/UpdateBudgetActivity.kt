package tn.esprit.wedding.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputEditText
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import tn.esprit.wedding.R
import tn.esprit.wedding.models.Budget
import tn.esprit.wedding.viewmodels.UpdateBudgetViewModel

class UpdateBudgetActivity : AppCompatActivity() {
    lateinit var nombudget : TextInputEditText
    lateinit var catbudget : TextInputEditText
    lateinit var montantbudget : TextInputEditText
    lateinit var notebudget : TextInputEditText
    lateinit var updatebtn : Button
    lateinit var updateBudgetViewModel: UpdateBudgetViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_budget)
        nombudget = findViewById(R.id.nombudget)
        catbudget = findViewById(R.id.catbudget)
        montantbudget = findViewById(R.id.montantbudget)
        notebudget = findViewById(R.id.notebudget)
        updatebtn = findViewById(R.id.addBtn)
        updateBudgetViewModel = ViewModelProvider(this).get(UpdateBudgetViewModel::class.java)

        var id = intent.getStringExtra("id")
        var nom = intent.getStringExtra("nom")
        nombudget.setText(nom)
        var cat = intent.getStringExtra("categorie")
        catbudget.setText(cat)
        var mon = intent.getStringExtra("montant")
        montantbudget.setText(mon)
        var note = intent.getStringExtra("note")
        notebudget.setText(note)

        updatebtn.setOnClickListener {
            UpdateBudget(id!!)
            finish()
        }

    }
    private fun UpdateBudget (id : String){
        val nom = nombudget.text.toString().trim()
        val categorie = catbudget.text.toString().trim()
        val montant = Integer.parseInt(montantbudget.text.toString().trim())
        val note = notebudget.text.toString().trim()
        val budget = Budget(id,nom,categorie,montant,note,"",0)
        updateBudgetViewModel.updateBudget(id,budget)

    }
}