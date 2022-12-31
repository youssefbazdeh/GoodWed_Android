package tn.esprit.wedding

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
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
import tn.esprit.wedding.R
import tn.esprit.wedding.views.UpdateChecklistActivity
import tn.esprit.wedding.views.UpdateProfileActivity


class MenuFragment : Fragment() {
    lateinit var logoutbtn : ImageView
    lateinit var user_name : TextView
    lateinit var user_email : TextView
    lateinit var prefs: SharedPreferences
    lateinit var profil : LinearLayout


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_menu, container, false)
        logoutbtn = v.findViewById(R.id.logoutbtn)
        user_name = v.findViewById(R.id.user_name)
        user_email = v.findViewById(R.id.user_email)
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