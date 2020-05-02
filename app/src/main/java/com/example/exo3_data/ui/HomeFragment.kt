package com.example.exo3_data.ui


import android.app.DatePickerDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.LinearLayout
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

import com.example.exo3_data.R
import com.example.exo3_data.db.InterventionDataBase
import kotlinx.android.synthetic.main.fragment_add_intervention.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*


class HomeFragment : BaseFragment() {

    private var mDateListener: DatePickerDialog.OnDateSetListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        recycler_view_interventions!!.setHasFixedSize(true)
        recycler_view_interventions!!.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)

        launch {
            context?.let{
                val interventions = InterventionDataBase(it).getInterventionDao().getAllInterventions()
                recycler_view_interventions!!.adapter = InterventionAdapter(interventions)
            }
        }

        btn_add.setOnClickListener {
            val action = HomeFragmentDirections.actionAddIntervention()
            Navigation.findNavController(it).navigate(action)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.action_search -> showdateOfSearch()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun showdateOfSearch()
    {
        var calendar = Calendar.getInstance()
        var year = calendar.get(Calendar.YEAR)
        var month = calendar.get(Calendar.MONTH)
        var day = calendar.get(Calendar.DAY_OF_MONTH)

        val picker = DatePickerDialog(
            requireContext(),
            android.R.style.Theme_Holo_Light_Dialog_MinWidth,
            mDateListener, year, month, day
        )

        picker.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        picker.show()

        mDateListener = DatePickerDialog.OnDateSetListener { datePicker, year, month, day ->
            var cal = Calendar.getInstance()
            cal.set(year, month, day)
            val myFormat = "dd/MM/yyyy" // mention the format you need
            val sdf = SimpleDateFormat(myFormat, Locale.US)
            //Log.i("time",sdf.format(cal.time))
            val dateIntervention = sdf.format(cal.time)
            Log.i("dateIntervention", dateIntervention)

            val action = HomeFragmentDirections.actionSearchIntervention(dateIntervention)
            Navigation.findNavController(requireView()).navigate(action)
        }
    }




}
