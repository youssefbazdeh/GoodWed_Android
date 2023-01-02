package tn.esprit.wedding

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.media.Image
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import tn.esprit.wedding.R
import tn.esprit.wedding.models.Wedding
import tn.esprit.wedding.viewmodels.WeddingViewModel
import tn.esprit.wedding.views.UpdateChecklistActivity
import tn.esprit.wedding.views.UpdateProfileActivity
import java.text.SimpleDateFormat
import java.util.*


class MenuFragment : Fragment() {
    lateinit var logoutbtn : ImageView
    lateinit var user_name : TextView
    lateinit var user_email : TextView
    lateinit var wed_name : TextView
    lateinit var wed_date : TextView
    lateinit var prefs: SharedPreferences
    lateinit var profil : ConstraintLayout
    lateinit var toolbar: Toolbar
    lateinit var user_image : ImageView
    lateinit var imagewed : ImageView
    lateinit var weddingViewModel: WeddingViewModel
    val myFormat = "dd-MM-yyyy"
    val sdf = SimpleDateFormat(myFormat, Locale.getDefault())


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_menu, container, false)
        toolbar = v.findViewById(R.id.toolbarmain)
        toolbar.setTitle("Menu")
        logoutbtn = v.findViewById(R.id.logoutbtn)
        user_name = v.findViewById(R.id.user_name)
        user_email = v.findViewById(R.id.user_email)
        user_image = v.findViewById(R.id.user_image)
        imagewed = v.findViewById(R.id.imagewed)
        wed_name = v.findViewById(R.id.wed_name)
        wed_date = v.findViewById(R.id.wed_date)
        profil = v.findViewById(R.id.profil)
        prefs = requireContext().getSharedPreferences(PREF_LOGIN, AppCompatActivity.MODE_PRIVATE)

        profil.setOnClickListener {
            var intent = Intent(context, UpdateProfileActivity::class.java)


            val nom = user_name.text.toString()
            intent.putExtra("nom",nom)
            val type = user_email.text.toString()
            intent.putExtra("type",type)
            startActivity(intent)

        }
        weddingViewModel = ViewModelProvider(this).get(WeddingViewModel::class.java)
        weddingViewModel.getWeddingByIdUser(requireContext().getSharedPreferences(PREF_LOGIN, AppCompatActivity.MODE_PRIVATE).getString(ID,"")!!)
        weddingViewModel._weddingLiveData.observe(viewLifecycleOwner, Observer<MutableList<Wedding>?>{
            if(it.size>0){
                Picasso.get().load(it.get(0).image).into(imagewed)
                wed_name.setText(it.get(0).wedding_name)
                wed_date.setText(sdf.format(it.get(0).date_ceremonie!!).toString())
            }
        })

        val nom = prefs.getString(FULLNAME,"")
        user_name.setText(nom)

        val email = prefs.getString(EMAIL,"")
        user_email.setText(email)


        logoutbtn.setOnClickListener {
            val preferences: SharedPreferences = this.requireActivity().getSharedPreferences(
                PREF_LOGIN, Context.MODE_PRIVATE)
            val editor:SharedPreferences.Editor=preferences.edit()
            editor.clear()
            editor.apply()
            startActivity(Intent(requireContext(),LoginActivity::class.java))
        }
        return v
    }

}