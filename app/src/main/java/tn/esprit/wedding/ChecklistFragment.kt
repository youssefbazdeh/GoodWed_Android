package tn.esprit.wedding

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButtonToggleGroup
import tn.esprit.wedding.adapters.ChecklistAdapter
import tn.esprit.wedding.models.Checklist
import tn.esprit.wedding.viewmodels.ChecklistViewModel
import tn.esprit.wedding.views.TaskActivity
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class ChecklistFragment : Fragment() {
    lateinit var toolbar : Toolbar
    lateinit var rvChecklist : RecyclerView
    lateinit var listChecklist: MutableList<Checklist>
    lateinit var checklistViewModel: ChecklistViewModel
    lateinit var addBtn : ImageView
    lateinit var comp : Button
    lateinit var prog : Button
    val myFormat = "dd-MM-yyyy"
    val sdf = SimpleDateFormat(myFormat, Locale.getDefault())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val v = inflater.inflate(R.layout.fragment_checklist, container, false)

        toolbar = v.findViewById<MaterialToolbar>(R.id.toolbarmain)
        toolbar.setTitle("Checklist")

        toolbar.setOnMenuItemClickListener {
            val intent = Intent(requireContext(), TaskActivity::class.java)
            startActivity(intent)
            true
        }

           comp = v.findViewById(R.id.button_completed)
           prog = v.findViewById(R.id.button_prog)
        checklistViewModel = ViewModelProvider(this).get(ChecklistViewModel::class.java)
        rvChecklist = v.findViewById(R.id.rv_checklist)
        listChecklist = ArrayList()

        val toggleButtonGroup=v.findViewById<MaterialButtonToggleGroup>(R.id.toggleButtonGroup)
        toggleButtonGroup.check(R.id.button_completed)
        getAllChecklistByStatus(requireContext().getSharedPreferences(PREF_LOGIN, AppCompatActivity.MODE_PRIVATE).getString(ID,"")!!,"Completed")

        toggleButtonGroup.addOnButtonCheckedListener { toggleButtonGroup, checkedId, isChecked ->
            if(isChecked){
                when(checkedId){
                    R.id.button_completed->  getAllChecklistByStatus(requireContext().getSharedPreferences(PREF_LOGIN, AppCompatActivity.MODE_PRIVATE).getString(ID,"")!!,"Completed")
                    R.id.button_prog->  getAllChecklistByStatus(requireContext().getSharedPreferences(PREF_LOGIN, AppCompatActivity.MODE_PRIVATE).getString(ID,"")!!,"In progress")
                }
            }else{

                Toast.makeText(requireContext(),"Nothing", Toast.LENGTH_SHORT).show()

            }
        }


        getAllChecklistByIdUser(requireContext().getSharedPreferences(PREF_LOGIN, AppCompatActivity.MODE_PRIVATE).getString(ID,"")!!)

        return v
    }

     fun getAllChecklistByIdUser(user_id: String) {

        checklistViewModel.getAllChecklistByIdUser(user_id,)
        checklistViewModel._checklistLiveData.observe(viewLifecycleOwner, Observer<MutableList<Checklist>?>{
            if (it.size>0){

                listChecklist=it
                val layoutManagerCompetences= LinearLayoutManager(requireContext(),RecyclerView.VERTICAL,false)
                rvChecklist.layoutManager=layoutManagerCompetences
                    val checklistAdapter = ChecklistAdapter(requireContext(),listChecklist)
                rvChecklist.adapter =checklistAdapter
                checklistAdapter.notifyItemChanged(listChecklist.size+1)
            }
        })


    }


    fun getAllChecklistByStatus(user_id: String,status : String) {

        checklistViewModel.getAllChecklistByStatus(user_id,status)
        checklistViewModel._checkliststatusLiveData.observe(viewLifecycleOwner, Observer<MutableList<Checklist>?>{
            if (it.size>0){

                listChecklist=it
                val layoutManagerCompetences= LinearLayoutManager(requireContext(),RecyclerView.VERTICAL,false)
                rvChecklist.layoutManager=layoutManagerCompetences
                val checklistAdapter = ChecklistAdapter(requireContext(),listChecklist)
                rvChecklist.adapter =checklistAdapter
                checklistAdapter.notifyItemChanged(listChecklist.size+1)
            }
        })


    }


}


