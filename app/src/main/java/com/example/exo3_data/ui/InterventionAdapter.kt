package com.example.exo3_data.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.exo3_data.R
import com.example.exo3_data.db.Intervention
import kotlinx.android.synthetic.main.intervention_layout.view.*

class InterventionAdapter(private val interventions:List<Intervention>):RecyclerView.Adapter<InterventionAdapter.InterventionViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InterventionAdapter.InterventionViewHolder
    {
        return InterventionViewHolder(
            LayoutInflater.from(parent.context)
               .inflate(R.layout.intervention_layout,parent,false)
        )
    }

    override fun getItemCount() = interventions.size

    override fun onBindViewHolder(holder: InterventionViewHolder, position: Int)
    {
        //holder.view.textView_nomPlombier.text
        //textView_typeIntervention
        //textView_dateIntervention
        holder.view.textView_nomPlombier.text = interventions[position].nomPlombier
        holder.view.textView_typeIntervention.text = interventions[position].typeIntervention
        holder.view.textView_dateIntervention.text = interventions[position].interventionDate

        holder.view.setOnClickListener {
            val action = HomeFragmentDirections.actionAddIntervention()
            action.intervention = interventions[position]
            Navigation.findNavController(it).navigate(action)
        }
    }

    class InterventionViewHolder(val view: View):RecyclerView.ViewHolder(view)
    {

    }
}