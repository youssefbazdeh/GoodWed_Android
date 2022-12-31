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
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.esprit.wedding.R
import tn.esprit.wedding.models.Budget
import tn.esprit.wedding.retrofit.ApiClient
import tn.esprit.wedding.services.BudgetService
import tn.esprit.wedding.views.DetailsBudgetActivity
import tn.esprit.wedding.views.DetailsGuestActivity

class BudgetAdapter(val context: Context, private val listBudgets: MutableList<Budget>) :
    RecyclerView.Adapter<BudgetAdapter.BudgetViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BudgetViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_budget, parent, false)
        return BudgetViewHolder(view)
    }


    override fun onBindViewHolder(holder: BudgetViewHolder, position: Int) {
        val budget = listBudgets[position]
        holder.budgetnom.setText(budget.nom)
        holder.budgetmontant.setText(budget.montant.toString())

        holder.itemView.setOnClickListener {
            val intent = Intent(context, DetailsBudgetActivity::class.java)
            intent.putExtra("id",budget._id)
            context.startActivity(intent)
        }
        val experiencePopoupMenu= PopupMenu(context, holder.delete)
        experiencePopoupMenu.inflate(R.menu.popup_menu)


        experiencePopoupMenu.setOnMenuItemClickListener {
            when(it.itemId){

                R.id.delete->{
                    alertDialog(budget._id)
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
        return listBudgets.size
    }
    class BudgetViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
         var budgetnom : TextView
         var budgetmontant : TextView
         var delete : ImageView

        init {
        budgetnom = itemView.findViewById(R.id.budgetnom)
        budgetmontant = itemView.findViewById(R.id.budgetmontant)
        delete = itemView.findViewById(R.id.delete)
        }
    }
    fun deleteBudgetById(idbudget:String){
        val retrofit= ApiClient.getApiClient()!!.create(BudgetService::class.java)
        val addUser=retrofit.deleteBudgetById(idbudget)
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
    fun alertDialog(idbudget: String) {

        //Setting message manually and performing action on button click
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder.setMessage("Voulez-vous vraiment supprimer cet exeperience ?")
            .setCancelable(false)
            .setPositiveButton("Oui") { dialog, id ->
                deleteBudgetById(idbudget)
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