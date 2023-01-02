package tn.esprit.wedding

import android.content.Intent
import android.content.IntentSender.OnFinished
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.appbar.MaterialToolbar
import tn.esprit.wedding.models.Budget
import tn.esprit.wedding.models.Checklist
import tn.esprit.wedding.models.Guest
import tn.esprit.wedding.models.Wedding
import tn.esprit.wedding.viewmodels.BudgetViewModel
import tn.esprit.wedding.viewmodels.ChecklistViewModel
import tn.esprit.wedding.viewmodels.GuestViewModel
import tn.esprit.wedding.viewmodels.WeddingViewModel
import tn.esprit.wedding.views.AddGuestActivity
import tn.esprit.wedding.views.UpdateGuestActivity
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList


class HomeFragment : Fragment() {
    lateinit var tasknb : TextView
    lateinit var listChecklist: MutableList<Checklist>
    lateinit var listMarriage: MutableList<Wedding>
    lateinit var listGuest: MutableList<Guest>
    lateinit var listBudget: MutableList<Budget>
    lateinit var checklistViewModel: ChecklistViewModel
    lateinit var guestViewModel: GuestViewModel
    lateinit var mariageViewModel: WeddingViewModel
    lateinit var budgetViewModel: BudgetViewModel
    lateinit var budget : TextView
    lateinit var guesttv : TextView
    lateinit var nmpt : TextView
    lateinit var datem : TextView
    lateinit var nbcomp : TextView
    lateinit var used : TextView
    lateinit var confnb : TextView
    lateinit var menubtn : ImageView
    lateinit var toolbar : Toolbar
    lateinit var timer : TextView
    val myFormat = "dd-MM-yyyy"
    val sdf = SimpleDateFormat(myFormat, Locale.getDefault())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_home, container, false)
        checklistViewModel = ViewModelProvider(this).get(ChecklistViewModel::class.java)
        mariageViewModel = ViewModelProvider(this).get(WeddingViewModel::class.java)
        guestViewModel = ViewModelProvider(this).get(GuestViewModel::class.java)
        budgetViewModel = ViewModelProvider(this).get(BudgetViewModel::class.java)
        budget = v.findViewById(R.id.budget)
        tasknb = v.findViewById(R.id.tasknb)
        guesttv = v.findViewById(R.id.guesttv)
        nmpt = v.findViewById(R.id.nmpt)
        datem = v.findViewById(R.id.datem)
        nbcomp = v.findViewById(R.id.nbcomp)
        used = v.findViewById(R.id.used)
        confnb = v.findViewById(R.id.confnb)
        timer = v.findViewById(R.id.timer)
        listChecklist = ArrayList()
        listMarriage = ArrayList()
        listGuest = ArrayList()
        listBudget = ArrayList()

       toolbar = v.findViewById<MaterialToolbar>(R.id.toolbarmain)
        toolbar.setTitle("Home")
        toolbar.setCollapseIcon(R.drawable.ic_baseline_add_24)
        toolbar.setNavigationOnClickListener {
        }
        toolbar.setOnMenuItemClickListener {
            val intent = Intent(requireContext(),AddGuestActivity::class.java)
            startActivity(intent)
            true
        }






        Log.i("budget marriage",listMarriage.toString())
        budgetViewModel.getAllBudgetByIdUser(requireContext().getSharedPreferences(PREF_LOGIN, AppCompatActivity.MODE_PRIVATE).getString(ID,"")!!)
        budgetViewModel._budgetLiveData.observe(viewLifecycleOwner, Observer<MutableList<Budget>?>{
            if (it.size>0){
                listBudget=it
                //Log.i("",listChecklist.size.toString())
                var tot : Int = 0
                for (budget in listBudget){
                    tot = tot+budget.montant

                }
                used.text = tot.toString() + '$'

            }

        })


        checklistViewModel.getAllChecklistByIdUser(requireContext().getSharedPreferences(PREF_LOGIN, AppCompatActivity.MODE_PRIVATE).getString(ID,"")!!)
        checklistViewModel._checklistLiveData.observe(viewLifecycleOwner, Observer<MutableList<Checklist>?>{
            if (it.size>0){
                listChecklist=it
                //Log.i("",listChecklist.size.toString())
                tasknb.text = listChecklist.size.toString()
                var nb : Int = 0
                for(checklist in listChecklist)
                {

                if(checklist.status == "Completed"){
                     nb++
                    nbcomp.text = nb.toString()

                }else{
                    nb
                    nbcomp.text = nb.toString()
                }
                }
            }

        })

        guestViewModel.getAllGuestByIdUser(requireContext().getSharedPreferences(PREF_LOGIN, AppCompatActivity.MODE_PRIVATE).getString(ID,"")!!)
        guestViewModel._guestMutableLiveData.observe(viewLifecycleOwner, Observer<MutableList<Guest>?>{
            if (it.size>0){
                listGuest=it
                //Log.i("",listChecklist.size.toString())
                guesttv.text = listGuest.size.toString()
                var nb : Int = 0
                for(guest in listGuest)
                {

                    if(guest.note == "Confirmed"){
                        nb++
                        confnb.text = nb.toString()

                    }else{
                        nb
                        confnb.text = nb.toString()
                    }
                }
            }

        })

        mariageViewModel.getWeddingByIdUser(requireContext().getSharedPreferences(PREF_LOGIN, AppCompatActivity.MODE_PRIVATE).getString(ID,"")!!)
        mariageViewModel._weddingLiveData.observe(viewLifecycleOwner, Observer<MutableList<Wedding>?>{
            if (it.size>0){
                var listwed : MutableList<Wedding> = it.toMutableList()
                val budgetnb = it.get(0).budget
                val nom = it.get(0).fullname
                val nomp = it.get(0).partner_fullname
                val date = it.get(0).date_ceremonie
                Log.i("buddddgettt",budgetnb.toString())
                budget.text = budgetnb.toString() + '$'
                nmpt.text = nom + ' '+'&' + ' '+nomp
                datem.setText(sdf.format(date))
                var date_cere : Long = 12
                var sysdate : Long = 1
                //var duration : Long = TimeUnit.DAYS.toHours()
                countdown()
                //(Integer.parseInt(sdf.format(it.get(0).date_ceremonie!!).toString())-Integer.parseInt(sdf.format(Calendar.getInstance().time).toString())).toLong()

            }

        })



        return v
    }


    fun countdown() {
        var duration: Long = TimeUnit.DAYS.toSeconds(2)
        Log.i("duration",duration.toString())


        object : CountDownTimer(duration, 10) {
            override fun onTick(secondsUntilFinished: Long) {
                var sDuration: String = String.format(
                    Locale.ENGLISH,
                    "%02d:%02d:%02d:%02d",
                    TimeUnit.MILLISECONDS.toDays( duration )%3600,
                    TimeUnit.SECONDS.toHours( duration )%60,
                    TimeUnit.SECONDS.toMinutes(duration )%60,
                    TimeUnit.MILLISECONDS.toSeconds(duration)
                )

                timer.text = sDuration

            }

            override fun onFinish() {
                timer.setText("Time up!");
                timer.setVisibility(View.VISIBLE);


            }
        }
            .start()
    }
}