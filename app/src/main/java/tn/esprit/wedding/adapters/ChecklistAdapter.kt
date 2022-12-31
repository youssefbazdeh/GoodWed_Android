package tn.esprit.wedding.adapters

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.wedding.HomeActivity
import tn.esprit.wedding.R
import tn.esprit.wedding.models.Checklist
import tn.esprit.wedding.retrofit.ApiClient
import tn.esprit.wedding.services.ChecklistService
import tn.esprit.wedding.views.DetailChecklistActivity
import tn.esprit.wedding.views.UpdateChecklistActivity


class ChecklistAdapter(val context: Context, private val listChecklists: MutableList<Checklist>) :
    RecyclerView.Adapter<ChecklistAdapter.ChecklistViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChecklistViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_checklist, parent, false)
        return ChecklistViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChecklistViewHolder, position: Int) {
        val checklist = listChecklists[position]
        holder.cknom.setText(checklist.nom)
        Picasso.get().load(checklist.image).into(holder.status)
        holder.cktype.setText(checklist.type)
        holder.ckdate.setText(checklist.date.toString())

        holder.ckdate.setOnClickListener {
            val intent = Intent(context,DetailChecklistActivity::class.java)
            intent.putExtra("id",checklist._id)
            context.startActivity(intent)
        }

        val experiencePopoupMenu= PopupMenu(context, holder.delete)
        experiencePopoupMenu.inflate(R.menu.popup_menu)


        experiencePopoupMenu.setOnMenuItemClickListener {
            when(it.itemId){

                R.id.delete->{
                    alertDialog(checklist._id)
                    notifyItemRemoved(position)
                    true
                }
                else->true
            }
        }
        holder.delete.setOnClickListener {
            try {
                val popup=PopupMenu::class.java.getDeclaredField("experiencePopup")
                popup.isAccessible=true
                val menu=popup.get(experiencePopoupMenu)
                menu.javaClass.getDeclaredMethod("Show menu",Boolean::class.java).invoke(menu,true)
            }catch (e:Exception){
                e.printStackTrace()
            }
            finally {
                experiencePopoupMenu.show()
            }
        }


    }

    override fun getItemCount(): Int {
        return listChecklists.size
    }
    fun amine(): Int{
        return listChecklists.size
    }

        class ChecklistViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            var status: ImageView
            var cknom: TextView
            var cktype: TextView
            var ckdate: TextView
            var delete : ImageView



            init {
                delete = itemView.findViewById(R.id.delete)
                status= itemView.findViewById(R.id.status)
                cknom = itemView.findViewById(R.id.cknom)
                cktype = itemView.findViewById(R.id.cktype)
                ckdate = itemView.findViewById(R.id.ckdate)

            }
        }


    fun deleteChecklistById(idtask:String){
        val retrofit= ApiClient.getApiClient()!!.create(ChecklistService::class.java)
        val addUser=retrofit.deleteTaskById(idtask)
        addUser.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful){
                    //   listExperiences.re
                    //    notifyItemRemoved()
                }else{
                    Log.i("errorBody",  response.errorBody()!!.string())

                }

            }

            override fun onFailure(call: Call<String>, t: Throwable) {

                Log.i("failure",  t.message.toString())
            }

        })

    }



    fun alertDialog(idtask:String) {

        //Setting message manually and performing action on button click
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder.setMessage("Voulez-vous vraiment supprimer cet exeperience ?")
            .setCancelable(false)
            .setPositiveButton("Oui") { dialog, id ->
                deleteChecklistById(idtask)
            }
            .setNegativeButton("Non") { dialog, id -> //  Action for 'NO' Button
                dialog.cancel()

            }
        //Creating dialog box
        val alert = builder.create()
        //Setting the title manually
        alert.setTitle("Deleted Task")
        alert.show()

    }
}