package tn.esprit.wedding

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import tn.esprit.wedding.adapters.ChecklistAdapter
import tn.esprit.wedding.models.Checklist
import tn.esprit.wedding.viewmodels.ChecklistViewModel
import tn.esprit.wedding.views.TaskActivity


class ChecklistFragment : Fragment() {
    lateinit var rvChecklist : RecyclerView
    lateinit var listChecklist: MutableList<Checklist>
    lateinit var checklistViewModel: ChecklistViewModel
    lateinit var addBtn : ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val v = inflater.inflate(R.layout.fragment_checklist, container, false)
        addBtn = v.findViewById(R.id.addBtn)
        checklistViewModel = ViewModelProvider(this).get(ChecklistViewModel::class.java)
        rvChecklist = v.findViewById(R.id.rv_checklist)
        listChecklist = ArrayList()
        addBtn.setOnClickListener {
            startActivity(Intent(requireContext(),TaskActivity::class.java))
        }

        getAllChecklistByIdUser(requireContext().getSharedPreferences(PREF_LOGIN, AppCompatActivity.MODE_PRIVATE).getString(ID,"")!!)

        return v
    }

    private fun getAllChecklistByIdUser(user_id: String) {

        checklistViewModel.getAllChecklistByIdUser(user_id)
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


}


