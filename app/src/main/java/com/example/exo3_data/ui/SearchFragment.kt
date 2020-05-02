package com.example.exo3_data.ui


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.exo3_data.R
import com.example.exo3_data.db.InterventionDataBase
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 */
class SearchFragment : BaseFragment() {

    private var dateIntervention:String? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recycler_view_interventions!!.setHasFixedSize(true)
        recycler_view_interventions!!.layoutManager = LinearLayoutManager(requireContext(),
            LinearLayoutManager.VERTICAL,false)

        arguments?.let {
            dateIntervention = SearchFragmentArgs.fromBundle(it).dateIntervention
        }

        launch {
            context?.let{
                val interventions = InterventionDataBase(it).getInterventionDao().getInterventionByDate(dateIntervention!!)
                recycler_view_interventions!!.adapter = InterventionAdapter(interventions)
            }
        }
    }


}
