package tn.esprit.wedding.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import tn.esprit.wedding.R
import tn.esprit.wedding.models.Checklist

class ChecklistAdapter(val context: Context, private val listChecklists: MutableList<Checklist>) : RecyclerView.Adapter<ChecklistAdapter.ChecklistViewHolder>() {

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

    }

    override fun getItemCount(): Int {
        return listChecklists.size
    }


    class ChecklistViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var status: ImageView
        var cknom: TextView
        var cktype: TextView
        var ckdate: TextView

        init {
            status=itemView.findViewById(R.id.status)
            cknom = itemView.findViewById(R.id.cknom)
            cktype = itemView.findViewById(R.id.cktype)
            ckdate = itemView.findViewById(R.id.ckdate)

        }
    }
}