package tn.esprit.wedding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class HomeActivity : AppCompatActivity() {

    //lateinit var toggle: ActionBarDrawerToggle
    private lateinit var navController : NavController
    private lateinit var toolbar: Toolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        //setSupportActionBar(findViewById(R.id.toolbar))



        val navHostFragment = supportFragmentManager.findFragmentById(R.id.mainContainer) as NavHostFragment
        navController = navHostFragment.navController
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        NavigationUI.setupWithNavController(bottomNavigationView, navController)

        val navigationView =findViewById<NavigationView>(R.id.nav_view)
        NavigationUI.setupWithNavController(navigationView,navController)

    }
    //override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    //  menuInflater.inflate(R.menu.nav_menu,menu)
    //return true
    //}
}