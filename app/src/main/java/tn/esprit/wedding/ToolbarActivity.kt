package tn.esprit.wedding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.ImageView
import android.widget.Toolbar
import androidx.core.graphics.drawable.toDrawable
import tn.esprit.wedding.views.UpdateGuestActivity

class ToolbarActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_toolbar)
        setSupportActionBar(findViewById(R.id.toolbarmain))

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.nav_menu,menu)
        menu!!.findItem(R.id.toolbarmain).icon = R.drawable.ic_baseline_add_24.toDrawable()
        return true
    }
}