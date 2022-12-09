package tn.esprit.wedding



import android.app.*
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.view.Gravity
import android.view.ViewGroup
import android.view.Window
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import coil.load
import coil.transform.CircleCropTransformation
import com.google.android.material.textfield.TextInputEditText
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.karumi.dexter.listener.single.PermissionListener
import me.relex.circleindicator.CircleIndicator3
import tn.esprit.wedding.models.Wedding
import tn.esprit.wedding.viewmodels.WeddingViewModel
import java.lang.Integer.parseInt
import java.text.SimpleDateFormat
import java.util.*



class MarriageActivity : AppCompatActivity() {


    //private lateinit var binding: ActivityMainBinding
    private val CAMERA_REQUEST_CODE = 1
    private val GALLERY_REQUEST_CODE = 2
    //private  lateinit var imgUri: Uri

    lateinit var view_pager2: ViewPager2
    private var titlesList = mutableListOf<String>()
    private var imagesList = mutableListOf<Int>()
    lateinit var btn_cre: Button
    lateinit var btn_rej: Button
    lateinit var dateTv: TextView
    lateinit var pickDateBtn: Button
    lateinit var pickHourBtn: Button
    lateinit var heureTv: TextView
    lateinit var  addBtn: Button
    lateinit var fullnameinputedit: TextInputEditText
    lateinit var conjoitinputedit: TextInputEditText
    lateinit var emailinputedit: TextInputEditText
    lateinit var weddingViewModel : WeddingViewModel
    lateinit var sexe: RadioGroup
    lateinit var sexep: RadioGroup
    lateinit var homme: RadioButton
    lateinit var femme: RadioButton
    lateinit var autre: RadioButton
    lateinit var hommep: RadioButton
    lateinit var femmep: RadioButton
    lateinit var autrep: RadioButton
    lateinit var budinputedit: TextInputEditText
    lateinit var wedinputedit: TextInputEditText
    lateinit var picbtn : Button
    lateinit var image : ImageView




    @RequiresApi(Build.VERSION_CODES.O)
    val myFormatTime = "HH:mm"
    @RequiresApi(Build.VERSION_CODES.O)
    val sdft = SimpleDateFormat(myFormatTime, Locale.getDefault())

    val myFormat = "dd-MM-yyyy"
    val sdf = SimpleDateFormat(myFormat, Locale.getDefault())



    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_marriage)


        postToList()

        btn_cre = findViewById(R.id.btn_cre)
        btn_rej = findViewById(R.id.btn_rej)

        btn_cre.setOnClickListener {
            showDialog()
        }
        btn_rej.setOnClickListener {

            startActivity(Intent(this,ForgotPasswword::class.java))
        }

        view_pager2 = findViewById<ViewPager2>(R.id.view_pager2)
        view_pager2.adapter = ViewPagerAdapter(titlesList,imagesList)
        view_pager2.orientation = ViewPager2.ORIENTATION_HORIZONTAL
       val indicator = findViewById<CircleIndicator3>(R.id.ind)
       indicator.setViewPager(view_pager2)

    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun showDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.create_wed)
        val myCalendar = Calendar.getInstance()

        val datePicker = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, month)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateLabel(myCalendar)
        }
        val timePicker = TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
            myCalendar.set(Calendar.HOUR, hourOfDay)
            myCalendar.set(Calendar.MINUTE, minute)
            updateLabelTime(myCalendar)
        }
        image = dialog.findViewById<ImageView>(R.id.image)
        picbtn = dialog.findViewById<Button>(R.id.picbtn)
        sexe = dialog.findViewById<RadioGroup>(R.id.sexe)
        sexep = dialog.findViewById<RadioGroup>(R.id.sexep)
        homme = dialog.findViewById<RadioButton>(R.id.homme)
        femme = dialog.findViewById<RadioButton>(R.id.femme)
        autre = dialog.findViewById<RadioButton>(R.id.autre)
        hommep = dialog.findViewById<RadioButton>(R.id.hommep)
        femmep = dialog.findViewById<RadioButton>(R.id.femmep)
        autrep = dialog.findViewById<RadioButton>(R.id.autrep)
        pickDateBtn = dialog.findViewById<Button>(R.id.pickDateBtn)
        dateTv = dialog.findViewById<TextView>(R.id.dateTv)
        pickHourBtn = dialog.findViewById<Button>(R.id.pickHourBtn)
        heureTv = dialog.findViewById<TextView>(R.id.heureTv)
        budinputedit = dialog.findViewById(R.id.budinputedit)
        fullnameinputedit = dialog.findViewById(R.id.fullnameinputedit)
        conjoitinputedit = dialog.findViewById(R.id.conjoitinputedit)
        emailinputedit = dialog.findViewById(R.id.emailinputedit)
        wedinputedit = dialog.findViewById(R.id.wedinputedit)



        image.setOnClickListener {
            val pictureDialog = AlertDialog.Builder(this)
            pictureDialog.setTitle("Select Action")
            val pictureDialogItem = arrayOf("Select photo from Gallery",
                "Capture photo from Camera")
            pictureDialog.setItems(pictureDialogItem) { dialog, which ->

                when (which) {
                    0 -> gallery()
                    1 -> camera()
                }
            }

            pictureDialog.show()
        }
       picbtn.setOnClickListener {
            cameraCheckPermission()
            galleryCheckPermission()
        }


        pickHourBtn.setOnClickListener {
            TimePickerDialog(this,timePicker,myCalendar.get(Calendar.HOUR), myCalendar.get(Calendar.MINUTE),false).show()

        }
        pickDateBtn.setOnClickListener {
            DatePickerDialog(this,datePicker, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show()
        }
        addBtn = dialog.findViewById<Button>(R.id.addBtn)
        addBtn.setOnClickListener {
            addWed()
            dialog.dismiss()

        }

        dialog.show()
        dialog.window!!.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window!!.attributes.windowAnimations = R.style.DialogAnimation
        dialog.window!!.setGravity(Gravity.BOTTOM)

        /*image.setOnClickListener {
            if (ContextCompat.checkSelfPermission(applicationContext,
                    Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                openGallery()
            } else {
                requestStoragePermission();
            }

        }*/



    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun addWed() {
        val dd = sdf.parse(dateTv.text.toString())
        val tt = sdft.parse(heureTv.text.toString())
        val bb: Int = parseInt(budinputedit.text.toString(),10)
        val test : String
        if(homme.isChecked) {

            test = "Homme"
        }    else if(femme.isChecked) {
                test="Femme"
            }else {
                test="Autre"
            }
        val test2 : String
        if(hommep.isChecked) {

            test2 = "Homme"
        }    else if(femmep.isChecked) {
            test2="Femme"
        }else {
            test2="Autre"
        }


        val wedding = Wedding (
            "",
            fullnameinputedit.text.toString().trim(),
            conjoitinputedit.text.toString().trim(),
            test,
            test2,
            emailinputedit.text.toString().trim(),
            wedinputedit.text.toString().trim(),
            dd,
            tt,
            bb,
            0
        )

        weddingViewModel = ViewModelProvider(this).get(WeddingViewModel::class.java)
        weddingViewModel.addWed(wedding)
        weddingViewModel._addWeddingLiveData.observe(this, androidx.lifecycle.Observer<Wedding>{
            if (it!=null){
                Toast.makeText(applicationContext, "ajout succes !", Toast.LENGTH_LONG).show()
                finish()
                startActivity(Intent(this, HomeActivity::class.java))

            }else{
                Toast.makeText(applicationContext, "Login failed !", Toast.LENGTH_LONG).show()
            }
        })
    }


    private fun updateLabel(myCalendar: Calendar) {

        dateTv.setText(sdf.format(myCalendar.time))


    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun updateLabelTime(myCalendar: Calendar) {

        heureTv.setText(sdft.format(myCalendar.time))


    }



    private fun addToList(title: String, image: Int) {
        titlesList.add(title)
        imagesList.add(image)
    }
    private fun postToList(){

            addToList("GoodWed est une app de planification de marriage unique",R.drawable.asset1)
            addToList("Gérer la liste d'invités, suivi des tâches, contrôle des coûts",R.drawable.asset2)
            addToList("Invitez votre conjoint et organisez votre mariage ensemble",R.drawable.asset3)

    }
    private fun galleryCheckPermission() {

        Dexter.withContext(this).withPermission(
            android.Manifest.permission.READ_EXTERNAL_STORAGE
        ).withListener(object : PermissionListener {
            override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
                gallery()
            }

            override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
                Toast.makeText(
                    this@MarriageActivity,
                    "You have denied the storage permission to select image",
                    Toast.LENGTH_SHORT
                ).show()
                showRotationalDialogForPermission()
            }

            override fun onPermissionRationaleShouldBeShown(
                p0: PermissionRequest?, p1: PermissionToken?) {
                showRotationalDialogForPermission()
            }
        }).onSameThread().check()
    }

    private fun gallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, GALLERY_REQUEST_CODE)
    }


    private fun cameraCheckPermission() {

        Dexter.withContext(this)
            .withPermissions(
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.CAMERA).withListener(

                object : MultiplePermissionsListener {
                    override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                        report?.let {

                            if (report.areAllPermissionsGranted()) {
                                camera()
                            }

                        }
                    }

                    override fun onPermissionRationaleShouldBeShown(
                        p0: MutableList<PermissionRequest>?,
                        p1: PermissionToken?) {
                        showRotationalDialogForPermission()
                    }

                }
            ).onSameThread().check()
    }


    private fun camera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, CAMERA_REQUEST_CODE)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {

            when (requestCode) {

                CAMERA_REQUEST_CODE -> {

                    val bitmap = data?.extras?.get("data") as Bitmap

                    //we are using coroutine image loader (coil)
                    image.load(bitmap) {
                        crossfade(true)
                        crossfade(1000)
                        transformations(CircleCropTransformation())
                    }
                }

                GALLERY_REQUEST_CODE -> {

                    image.load(data?.data) {
                        crossfade(true)
                        crossfade(1000)
                        transformations(CircleCropTransformation())
                    }

                }
            }

        }

    }


    private fun showRotationalDialogForPermission() {
        AlertDialog.Builder(this)
            .setMessage("It looks like you have turned off permissions"
                    + "required for this feature. It can be enable under App settings!!!")

            .setPositiveButton("Go TO SETTINGS") { _, _ ->

                try {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts("package", packageName, null)
                    intent.data = uri
                    startActivity(intent)

                } catch (e: ActivityNotFoundException) {
                    e.printStackTrace()
                }
            }

            .setNegativeButton("CANCEL") { dialog, _ ->
                dialog.dismiss()
            }.show()
    }



}




