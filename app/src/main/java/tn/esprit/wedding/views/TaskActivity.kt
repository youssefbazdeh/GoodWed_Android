package tn.esprit.wedding.views

import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.ActivityNotFoundException
import android.content.ContentProvider
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputEditText
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.karumi.dexter.listener.single.PermissionListener
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import tn.esprit.wedding.*
import tn.esprit.wedding.models.Checklist
import tn.esprit.wedding.viewmodels.TaskViewModel
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

class TaskActivity : AppCompatActivity() {


    val CAMERA_REQUEST_CODE = 1
    val GALLERY_REQUEST_CODE = 2
    val IMAGE_GALLERY_REQUEST_CODE: Int = 2001

    lateinit var imgUri: Uri

    //Declaration
    lateinit var nominputedit : TextInputEditText
    lateinit var typeinputedit : TextInputEditText
    lateinit var noteinputedit : TextInputEditText
    lateinit var statusinputedit : TextInputEditText
    lateinit var pickDateBtn : Button
    lateinit var dateTv : TextView
    lateinit var image : ImageView
    lateinit var taskViewModel : TaskViewModel
    lateinit var addBtn : Button
    val myFormat = "dd-MM-yyyy"
    val sdf = SimpleDateFormat(myFormat, Locale.getDefault())





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task)

        nominputedit = findViewById(R.id.nominputedit)
        typeinputedit = findViewById(R.id.typeinputedit)
        noteinputedit = findViewById(R.id.noteinputedit)
        statusinputedit = findViewById(R.id.statusinputedit)
        pickDateBtn = findViewById(R.id.pickDateBtn)
        dateTv = findViewById(R.id.dateTv)
        image = findViewById(R.id.image)
        addBtn = findViewById(R.id.addBtn)


        val myCalendar = Calendar.getInstance()

        val datePicker = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, month)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateLabel(myCalendar)
        }

        pickDateBtn.setOnClickListener {
            DatePickerDialog(this,datePicker, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show()
        }


        image.setOnClickListener {
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

        addBtn.setOnClickListener {
            addTask(this.getSharedPreferences(PREF_LOGIN, AppCompatActivity.MODE_PRIVATE).getString(ID,"")!!.toString().trim().toRequestBody("text/plain".toMediaTypeOrNull()))
        }


    }

    private fun addTask (user_id : RequestBody) {
        val dd = sdf.parse(dateTv.text.toString())
        val fileDir=applicationContext.filesDir
        val file= File(fileDir,"image.jpg")
        val inputStream=contentResolver.openInputStream(imgUri)
        val outputStream= FileOutputStream(file)
        inputStream!!.copyTo(outputStream)
        val requestBody=file.asRequestBody("image/*".toMediaTypeOrNull())
        val image = MultipartBody.Part.createFormData("image", file.name,requestBody)
        val nom = nominputedit.text.toString().trim().toRequestBody("text/plain".toMediaTypeOrNull())
        val type = typeinputedit.text.toString().trim().toRequestBody("text/plain".toMediaTypeOrNull())
        val note = noteinputedit.text.toString().trim().toRequestBody("text/plain".toMediaTypeOrNull())
        val status = statusinputedit.text.toString().trim().toRequestBody("text/plain".toMediaTypeOrNull())

        taskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)
        taskViewModel.addChecklist(nom,type,note,image,dd,status,user_id)
        taskViewModel._addTaskLiveData.observe(this,androidx.lifecycle.Observer<Checklist?>{
            if (it!=null){
                Toast.makeText(applicationContext, "ajout succes !", Toast.LENGTH_LONG).show()
                finish()

            }else{
                Toast.makeText(applicationContext, "Login failed !", Toast.LENGTH_LONG).show()
            }
        })

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
                    this@TaskActivity,
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

                            image.setImageURI(imgUri)

                        }
                    }
                }

                GALLERY_REQUEST_CODE -> {

                    if (data != null && data.data != null) {
                        if (Build.VERSION.SDK_INT >= 28) {
                            imgUri= data.data!!

                            image.setImageURI(imgUri)

                        }
                    }
                }
            }

        }

    }

    private fun updateLabel(myCalendar: Calendar) {
        dateTv.setText(sdf.format(myCalendar.time))
    }
}