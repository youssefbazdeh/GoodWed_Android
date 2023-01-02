package tn.esprit.wedding

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButtonToggleGroup
import tn.esprit.wedding.adapters.BudgetAdapter
import tn.esprit.wedding.adapters.ChecklistAdapter
import tn.esprit.wedding.adapters.GuestAdapter
import tn.esprit.wedding.models.Budget
import tn.esprit.wedding.models.Checklist
import tn.esprit.wedding.models.Guest
import tn.esprit.wedding.viewmodels.GuestViewModel
import tn.esprit.wedding.views.AddGuestActivity


class GuestsFragment : Fragment() {
    lateinit var toolbar : Toolbar
    lateinit var addBtn : ImageView
    lateinit var rvGuest : RecyclerView
    lateinit var listGuest : MutableList<Guest>
    lateinit var guestViewModel: GuestViewModel
    lateinit var comp : Button
    lateinit var prog : Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_guests, container, false)
        toolbar = v.findViewById<MaterialToolbar>(R.id.toolbarmain)
        toolbar.setTitle("Guest")
        toolbar.setNavigationOnClickListener {
        }
        toolbar.setOnMenuItemClickListener {
            val intent = Intent(requireContext(),AddGuestActivity::class.java)
            startActivity(intent)
            true
        }
        comp = v.findViewById(R.id.button_completed)
        prog = v.findViewById(R.id.button_prog)
        guestViewModel = ViewModelProvider(this).get(GuestViewModel::class.java)
        rvGuest = v.findViewById(R.id.rv_guest)
        listGuest = ArrayList()


        val toggleButtonGroup=v.findViewById<MaterialButtonToggleGroup>(R.id.toggleButtonGroup)
        toggleButtonGroup.check(R.id.button_completed)
        getAllGuestByNote(requireContext().getSharedPreferences(PREF_LOGIN, AppCompatActivity.MODE_PRIVATE).getString(ID,"")!!,"Confirmed")

        toggleButtonGroup.addOnButtonCheckedListener { toggleButtonGroup, checkedId, isChecked ->
            if(isChecked){
                when(checkedId){
                    R.id.button_completed->  getAllGuestByNote(requireContext().getSharedPreferences(PREF_LOGIN, AppCompatActivity.MODE_PRIVATE).getString(ID,"")!!,"Confirmed")
                    R.id.button_prog->  getAllGuestByNote(requireContext().getSharedPreferences(PREF_LOGIN, AppCompatActivity.MODE_PRIVATE).getString(ID,"")!!,"Not Confirmed")
                }
            }else{

                Toast.makeText(requireContext(),"Nothing", Toast.LENGTH_SHORT).show()

            }
        }

        getAllGuestByIdUser(requireContext().getSharedPreferences(PREF_LOGIN, AppCompatActivity.MODE_PRIVATE).getString(ID,"")!!)
        // Inflate the layout for this fragment
    return v
    }
    fun getAllGuestByIdUser(user_id: String) {

        guestViewModel.getAllGuestByIdUser(user_id,)
        guestViewModel._guestMutableLiveData.observe(viewLifecycleOwner, Observer<MutableList<Guest>?>{
            if (it.size>0){

                listGuest=it
                val layoutManagerBudget = LinearLayoutManager(requireContext(),RecyclerView.VERTICAL,false)
                rvGuest.layoutManager = layoutManagerBudget
                val guesttAdapter = GuestAdapter(requireContext(),listGuest)
                rvGuest.adapter = guesttAdapter
                guesttAdapter.notifyItemChanged(listGuest.size+1)
            }
        })


    }

    fun getAllGuestByNote(user_id: String,note : String) {

        guestViewModel.getAllGuestByNote(user_id,note)
        guestViewModel._guestnoteLiveData.observe(viewLifecycleOwner, Observer<MutableList<Guest>?>{
            if (it.size>0){

                listGuest=it
                val layoutManagerCompetences= LinearLayoutManager(requireContext(),RecyclerView.VERTICAL,false)
                rvGuest.layoutManager=layoutManagerCompetences
                val checklistAdapter = GuestAdapter(requireContext(),listGuest)
                rvGuest.adapter =checklistAdapter
                checklistAdapter.notifyItemChanged(listGuest.size+1)
            }
        })


    }


}