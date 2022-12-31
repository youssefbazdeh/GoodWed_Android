package tn.esprit.wedding.views

import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import coil.imageLoader
import com.google.android.material.textfield.TextInputEditText
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.karumi.dexter.listener.single.PermissionListener
import com.squareup.picasso.Picasso
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import tn.esprit.wedding.R
import tn.esprit.wedding.models.Checklist
import tn.esprit.wedding.viewmodels.UpdateChecklistViewModel
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*



class UpdateChecklistActivity : AppCompatActivity() {



    val CAMERA_REQUEST_CODE = 1
    val GALLERY_REQUEST_CODE = 2
    val IMAGE_GALLERY_REQUEST_CODE: Int = 2001
    lateinit var imgUri: Uri

    internal lateinit var nominputedit1  : TextInputEditText
    lateinit var typeinputedit1 : TextInputEditText
    lateinit var noteinputedit1 : TextInputEditText
    lateinit var statusinputedit1 : TextInputEditText
    lateinit var confBtn : Button
    lateinit var pickDateBtn1 : Button
    lateinit var dateTv1 : TextView
    lateinit var im : ImageView
    lateinit var updateChecklistViewModel: UpdateChecklistViewModel
    val myFormat = "dd-MM-yyyy"
    val sdf = SimpleDateFormat(myFormat, Locale.getDefault())

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_checklist)

        nominputedit1 = findViewById(R.id.nominputedit1)
        typeinputedit1 = findViewById(R.id.typeinputedit1)
        noteinputedit1 = findViewById(R.id.noteinputedit1)
        statusinputedit1 = findViewById(R.id.statusinputedit1)
        confBtn = findViewById(R.id.confBtn)
        pickDateBtn1 = findViewById(R.id.pickDateBtn1)
        dateTv1 = findViewById(R.id.dateTv1)
        im = findViewById(R.id.im)




        var id = intent.getStringExtra("id")
        var nom = intent.getStringExtra("nom")
        nominputedit1.setText(nom)
        var type = intent.getStringExtra("type")
        typeinputedit1.setText(type)
        var note = intent.getStringExtra("note")
        noteinputedit1.setText(note)
        var date = intent.getStringExtra("date")
        dateTv1.setText(date)
        var status = intent.getStringExtra("status")
        statusinputedit1.setText(status)




        val myCalendar = Calendar.getInstance()

        val datePicker = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, month)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateLabel(myCalendar)
        }

        pickDateBtn1.setOnClickListener {
            DatePickerDialog(this,datePicker, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show()
        }
        im.setOnClickListener {
            cameraCheckPermission()
            galleryCheckPermission()
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

        confBtn.setOnClickListener {
            UpdateChecklist(id!!)
            finish()
        }

    }

    private fun UpdateChecklist(id: String) {
        updateChecklistViewModel= ViewModelProvider(this).get(UpdateChecklistViewModel::class.java)
        val dd = sdf.parse(dateTv1.text.toString())
        var image:MultipartBody.Part?=null
        if (imgUri !=null){
            val fileDir=applicationContext.filesDir
            val file= File(fileDir,"image.jpg")
            val inputStream=contentResolver.openInputStream(imgUri!!)
            val outputStream= FileOutputStream(file)
            inputStream!!.copyTo(outputStream)
            val requestBody=file.asRequestBody("image/*".toMediaTypeOrNull())
            image = MultipartBody.Part.createFormData("image", file.name,requestBody)
        }

       // Toast.makeText(applicationContext,  image.toString(), Toast.LENGTH_LONG).show()


        val nom= nominputedit1.text.toString().trim().toRequestBody("text/plain".toMediaTypeOrNull())
        val type= typeinputedit1.text.toString().trim().toRequestBody("text/plain".toMediaTypeOrNull())
        val note = noteinputedit1.text.toString().trim().toRequestBody("text/plain".toMediaTypeOrNull())
        val status = statusinputedit1.text.toString().trim().toRequestBody("text/plain".toMediaTypeOrNull())
        updateChecklistViewModel.updateChecklist(id,nom,type,note,dd,image,status)

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
                    this@UpdateChecklistActivity,
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
    private fun gallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, GALLERY_REQUEST_CODE)
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


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {

            when (requestCode) {

                CAMERA_REQUEST_CODE -> {

                    if (data != null && data.data != null) {
                        if (Build.VERSION.SDK_INT >= 28) {
                            imgUri= data.data!!

                            im.setImageURI(imgUri)

                        }
                    }
                }

                GALLERY_REQUEST_CODE -> {

                    if (data != null && data.data != null) {
                        if (Build.VERSION.SDK_INT >= 28) {
                            imgUri= data.data!!

                            im.setImageURI(imgUri)

                        }
                    }
                }
            }

        }

    }

    private fun updateLabel(myCalendar: Calendar) {
        dateTv1.setText(sdf.format(myCalendar.time))

    }
}