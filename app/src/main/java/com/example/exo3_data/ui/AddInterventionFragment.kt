package com.example.exo3_data.ui


import android.app.AlertDialog
import android.app.DatePickerDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.navigation.Navigation

import com.example.exo3_data.R
import com.example.exo3_data.db.Intervention
import com.example.exo3_data.db.InterventionDataBase
import kotlinx.android.synthetic.main.fragment_add_intervention.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*


class AddInterventionFragment : BaseFragment() {

    private var intervention:Intervention? = null

    lateinit var plombier: String
    lateinit var typeIntervention: String
    lateinit var dateIntervention: String
    lateinit var mDateListener: DatePickerDialog.OnDateSetListener

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_intervention, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val plombiers_list: Array<String> = resources.getStringArray(R.array.plombiers_array)
        val interventions_list: Array<String> =
            resources.getStringArray(R.array.interventions_array)


        val adapterPlombier =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, plombiers_list)
        plombier_spinner.adapter = adapterPlombier


        val adapterIntervention =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, interventions_list)
        type_spinner.adapter = adapterIntervention

        plombier_spinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                //  Toast.makeText(requireContext(),plombiers_list[position],Toast.LENGTH_LONG)
                //Log.i("name",plombiers_list[position])
                plombier = plombiers_list[position]
//                Log.i("name", plombier)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

        type_spinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                // Log.i("type intervention",interventions_list[position])
                typeIntervention = interventions_list[position]
//                Log.i("type intervention", typeIntervention)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

        arguments?.let {
            intervention = AddInterventionFragmentArgs.fromBundle(it).intervention
            plombier_spinner.setSelection(plombiers_list.indexOf(intervention?.nomPlombier))
            type_spinner.setSelection(interventions_list.indexOf(intervention?.typeIntervention))
        }

        btn_date.setOnClickListener {
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
        }

        mDateListener = DatePickerDialog.OnDateSetListener { datePicker, year, month, day ->
            var cal = Calendar.getInstance()
            cal.set(year, month, day)
            val myFormat = "dd/MM/yyyy" // mention the format you need
            val sdf = SimpleDateFormat(myFormat, Locale.US)
            //Log.i("time",sdf.format(cal.time))
            dateIntervention = sdf.format(cal.time)
//            Log.i("dateIntervention", dateIntervention)
        }

        btn_save.setOnClickListener {
            try {
                if ((plombier == "Choisir un plombier") || (typeIntervention == "Type intervention") || (dateIntervention.isEmpty())) {
                    requireContext().toast("should entre your input correctely")
                } else {

                    launch {

                        context?.let {
                            val mIntervention = Intervention(dateIntervention,plombier,typeIntervention)
                            if (intervention==null){
                                InterventionDataBase(it).getInterventionDao().addIntervention(mIntervention)
                                it.toast("Intervention Saved")
                            } else {
                                mIntervention.id = intervention!!.id
                                InterventionDataBase(it).getInterventionDao().updateIntervention(mIntervention)
                                it.toast("Intervention Updated")
                            }


                            val action = AddInterventionFragmentDirections.actionSaveIntervention()
                            Navigation.findNavController(requireView()).navigate(action)
                        }
                    }

                }
            } catch (e : Exception) {
                requireContext().toast("should entre your input correctely")
            }
        }


    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    private fun deleteIntervention()
    {
        AlertDialog.Builder(context).apply {
            setTitle("Are you sure ?")
            setMessage("You cannot undo this operation")
            setPositiveButton("yes"){ _ , _ ->
                launch {
                    InterventionDataBase(context).getInterventionDao().deleteIntervention(intervention!!)
                    val action = AddInterventionFragmentDirections.actionSaveIntervention()
                    Navigation.findNavController(requireView()).navigate(action)
                }

            }
            setNegativeButton("No"){_,_ ->

            }
        }.create().show()

    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.delete -> if (intervention != null) deleteIntervention() else context?.toast("Cannot Delete")
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_edit,menu)
    }
}