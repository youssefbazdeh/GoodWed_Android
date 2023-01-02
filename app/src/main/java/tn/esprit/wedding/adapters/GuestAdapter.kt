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
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.wedding.R
import tn.esprit.wedding.models.Guest
import tn.esprit.wedding.retrofit.ApiClient
import tn.esprit.wedding.services.GuestService
import tn.esprit.wedding.views.DetailsGuestActivity

class GuestAdapter(val context: Context, private val listGuests: MutableList<Guest>) :
    RecyclerView.Adapter<GuestAdapter.GuestViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GuestViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_guest, parent, false)
        return GuestViewHolder(view)
    }

    override fun onBindViewHolder(holder: GuestViewHolder, position: Int) {
        val guest = listGuests[position]
        holder.guestnom.setText(guest.name)
        holder.guestlastname.setText(guest.lastname)

        holder.itemView.setOnClickListener {
            val intent = Intent( context , DetailsGuestActivity::class.java)
            intent.putExtra("id",guest._id)
            context.startActivity(intent)
        }
        val experiencePopoupMenu= PopupMenu(context, holder.delete)
        experiencePopoupMenu.inflate(R.menu.popup_menu)


        experiencePopoupMenu.setOnMenuItemClickListener {
            when(it.itemId){

                R.id.delete->{
                    alertDialog(guest._id)
                    notifyItemRemoved(position)
                    true
                }
                else->true
            }
        }
        holder.delete.setOnClickListener {
            try {
                val popup= PopupMenu::class.java.getDeclaredField("experiencePopup")
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
        return listGuests.size
    }

    class GuestViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var pic: ImageView
        var guestnom: TextView
        var guestlastname: TextView
        var delete : ImageView



        init {
            delete = itemView.findViewById(R.id.delete)
            pic= itemView.findViewById(R.id.pic)
            guestlastname = itemView.findViewById(R.id.guestlastname)
            guestnom = itemView.findViewById(R.id.guestnom)


        }
    }
    fun deleteGuestById(idguest:String){
        val retrofit= ApiClient.getApiClient()!!.create(GuestService::class.java)
        val addUser=retrofit.deleteGuestById(idguest)
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
    fun alertDialog(idguest:String) {

        //Setting message manually and performing action on button click
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder.setMessage("Voulez-vous vraiment supprimer cet exeperience ?")
            .setCancelable(false)
            .setPositiveButton("Oui") { dialog, id ->
                deleteGuestById(idguest)
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