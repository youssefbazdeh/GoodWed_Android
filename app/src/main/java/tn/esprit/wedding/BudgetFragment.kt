package tn.esprit.wedding

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import tn.esprit.wedding.adapters.BudgetAdapter
import tn.esprit.wedding.models.Budget
import tn.esprit.wedding.viewmodels.BudgetViewModel
import tn.esprit.wedding.views.AddBudgetActivity
import tn.esprit.wedding.views.AddGuestActivity
import tn.esprit.wedding.views.TaskActivity


class BudgetFragment : Fragment() {
    lateinit var toolbar : Toolbar
    lateinit var rvBudget : RecyclerView
    lateinit var listBudget: MutableList<Budget>
    lateinit var budgetViewModel: BudgetViewModel
    lateinit var addBtn : ImageView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_budget, container, false)

        toolbar = v.findViewById<MaterialToolbar>(R.id.toolbarmain)
        toolbar.setTitle("Budget")
        toolbar.setNavigationOnClickListener {
        }
        toolbar.setOnMenuItemClickListener {
            val intent = Intent(requireContext(), AddBudgetActivity::class.java)
            startActivity(intent)
            true
        }

        budgetViewModel = ViewModelProvider(this).get(BudgetViewModel::class.java)
        rvBudget = v.findViewById(R.id.rv_budget)
        listBudget = ArrayList()


        getAllBudgetByIdUser(requireContext().getSharedPreferences(PREF_LOGIN, AppCompatActivity.MODE_PRIVATE).getString(ID,"")!!)

        return v
    }
    fun getAllBudgetByIdUser(user_id: String) {

        budgetViewModel.getAllBudgetByIdUser(user_id,)
        budgetViewModel._budgetLiveData.observe(viewLifecycleOwner, Observer<MutableList<Budget>?>{
            if (it.size>0){

                listBudget=it
                val layoutManagerBudget = LinearLayoutManager(requireContext(),RecyclerView.VERTICAL,false)
                rvBudget.layoutManager = layoutManagerBudget
                val budgetAdapter = BudgetAdapter(requireContext(),listBudget)
                rvBudget.adapter =budgetAdapter
                budgetAdapter.notifyItemChanged(listBudget.size+1)
            }
        })


    }

}