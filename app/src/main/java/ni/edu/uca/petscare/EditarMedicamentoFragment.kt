package ni.edu.uca.petscare

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ni.edu.uca.petscare.databinding.FragmentEditarMascotaBinding
import ni.edu.uca.petscare.databinding.FragmentEditarMedicamentoBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [EditarMedicamentoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EditarMedicamentoFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var fbinding: FragmentEditarMedicamentoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fbinding = FragmentEditarMedicamentoBinding.inflate(layoutInflater)
        iniciar()
        return fbinding.root
    }
    private fun iniciar() {
        fbinding.etFechaFinEdit.setOnClickListener{showDatePickerDialog()}
        fbinding.etHoraPrimeraEditar.setOnClickListener{showTimePickerDialog()}
    }
    private fun showTimePickerDialog() {
        val timePicker = TimePickerFragment {onTimeSelected(it)}
        timePicker.show(parentFragmentManager, "time")
    }

    private fun onTimeSelected(time:String){
        fbinding.etHoraPrimeraEditar.setText("$time")
    }

    private fun showDatePickerDialog() {
        val datePicker = DatePickerFragment {day, month, year -> onDateSelected(day, month, year)}
        datePicker.show(parentFragmentManager, "datePicker" )
    }
    fun onDateSelected(day: Int, month: Int, year: Int){
        if (day >= 1 && day <= 9 && month > 9) {
            fbinding.etFechaFinEdit.setText("$year-$month-0$day")

        }

        if (day >= 1 && day <= 9 && month >= 1 && month <= 9) {
            fbinding.etFechaFinEdit.setText("$year-0$month-0$day")

        }
        if (month >= 1 && month <= 9 && day >= 10) {
            fbinding.etFechaFinEdit.setText("$year-0$month-$day")
        }
        if (month > 9 && day > 9) {
            fbinding.etFechaFinEdit.setText("$year-$month-$day")
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment EditarMedicamentoFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            EditarMedicamentoFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}