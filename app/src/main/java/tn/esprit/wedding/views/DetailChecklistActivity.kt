package tn.esprit.wedding.views

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import tn.esprit.wedding.R
import tn.esprit.wedding.models.Checklist
import tn.esprit.wedding.models.ResponseChecklist
import tn.esprit.wedding.viewmodels.ChecklistViewModel

class DetailChecklistActivity : AppCompatActivity() {
    val CAMERA_REQUEST_CODE = 1
    val GALLERY_REQUEST_CODE = 2
    lateinit var imgUri: Uri

    lateinit var listChecklist: MutableList<Checklist>
    lateinit var checklistViewModel: ChecklistViewModel
    lateinit var tvnom2 : TextView
    lateinit var tvtype2 : TextView
    lateinit var tvnote2 : TextView
    lateinit var tvdate2 : TextView
    lateinit var tvstatus2 : TextView
    lateinit var ivimage : ImageView
    lateinit var updateBtn : ImageView
    lateinit var idtask : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_checklist2)
        tvnote2 = findViewById(R.id.tvnote2)
        tvnom2 = findViewById(R.id.tvnom2)
        tvtype2 = findViewById(R.id.tvtype2)
        tvdate2 = findViewById(R.id.tvdate2)
        tvstatus2 = findViewById(R.id.tvstatus2)
        ivimage = findViewById(R.id.ivimage)
        updateBtn = findViewById(R.id.updateBtn)
        idtask = findViewById(R.id.idtask)

        updateBtn.setOnClickListener {
        var intent = Intent(this,UpdateChecklistActivity::class.java)
            val  x = idtask.text.toString()
            Log.i("aaaaaaaaaaaaaa",x)
            intent.putExtra("id",x)
            val nom = tvnom2.text.toString()
            intent.putExtra("nom",nom)
            val type = tvtype2.text.toString()
            intent.putExtra("type",type)
            val note = tvnote2.text.toString()
            intent.putExtra("note",note)
            val date = tvdate2.text.toString()
            intent.putExtra("date",date)
            val status = tvstatus2.text.toString()
            intent.putExtra("status",status)
            startActivity(intent)
        }

        checklistViewModel = ViewModelProvider(this).get(ChecklistViewModel::class.java)
        listChecklist = ArrayList()


        val id = intent.getStringExtra("id")
        Log.i("aaaaaaaaaaaa",id.toString())
        getCheckListByID(id!!)



    }

    fun getCheckListByID(id: String)  {

        checklistViewModel.getCheckListById(id)
        checklistViewModel._checklistLiveData1.observe(this, Observer<ResponseChecklist?>{
            if (it!=null){

                idtask.text = it._id.toString()
                tvnom2.text = it.nom.toString()
                tvtype2.text = it.type.toString()
                tvnote2.text = it.note.toString()
                Picasso.get().load(it.image).into(ivimage)
                tvdate2.text = it.date.toString()
                tvstatus2.text = it.status.toString()

            }
        })

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {

            when (requestCode) {

                CAMERA_REQUEST_CODE -> {

                    if (data != null && data.data != null) {
                        if (Build.VERSION.SDK_INT >= 28) {
                            imgUri= data.data!!

                            ivimage.setImageURI(imgUri)

                        }
                    }
                }

                GALLERY_REQUEST_CODE -> {

                    if (data != null && data.data != null) {
                        if (Build.VERSION.SDK_INT >= 28) {
                            imgUri= data.data!!

                            ivimage.setImageURI(imgUri)

                        }
                    }
                }
            }

        }

    }

}