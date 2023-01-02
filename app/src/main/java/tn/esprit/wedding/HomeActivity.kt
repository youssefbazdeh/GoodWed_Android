package tn.esprit.wedding

import android.content.SharedPreferences
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.internal.NavigationSubMenu
import com.google.android.material.navigation.NavigationView
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import tn.esprit.wedding.models.Wedding
import tn.esprit.wedding.viewmodels.WeddingViewModel

class HomeActivity : AppCompatActivity() {

    //lateinit var toggle: ActionBarDrawerToggle
    private lateinit var navController : NavController
    private lateinit var toolbar: Toolbar
    //lateinit var user_name : TextView
    lateinit var weddingViewModel: WeddingViewModel
    lateinit var prefs: SharedPreferences

    lateinit var pdp : CircleImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        //setSupportActionBar(findViewById(R.id.toolbar))
        prefs = getSharedPreferences(PREF_LOGIN,MODE_PRIVATE)


        weddingViewModel = ViewModelProvider(this).get(WeddingViewModel::class.java)
        weddingViewModel.getWeddingByIdUser(applicationContext.getSharedPreferences(PREF_LOGIN, AppCompatActivity.MODE_PRIVATE).getString(ID,"")!!)
        weddingViewModel._weddingLiveData.observe(this,Observer<MutableList<Wedding>?>{
            if(it.size>0){
                Picasso.get().load(it.get(0).image).into(pdp)
            }
        })



        val navHostFragment = supportFragmentManager.findFragmentById(R.id.mainContainer) as NavHostFragment
        navController = navHostFragment.navController
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        NavigationUI.setupWithNavController(bottomNavigationView, navController)

        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        NavigationUI.setupWithNavController(navigationView,navController)

        val header: View = navigationView.getHeaderView(0)
        val user_name: TextView = header.findViewById(R.id.user_name)
        val user_email: TextView = header.findViewById(R.id.user_email)
        pdp = header.findViewById(R.id.pdp)


        val nom = prefs.getString(FULLNAME,"")
        user_name.setText(nom)

        val email = prefs.getString(EMAIL,"")
        user_email.setText(email)






    }
    //override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    //  menuInflater.inflate(R.menu.nav_menu,menu)
    //return true
    //}
}