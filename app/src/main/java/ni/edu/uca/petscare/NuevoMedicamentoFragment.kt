package ni.edu.uca.petscare

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ni.edu.uca.petscare.dao.DaoMedicamento
import ni.edu.uca.petscare.databinding.FragmentNuevoMedicamentoBinding
import java.sql.Time
import java.time.LocalDate
import java.time.format.DateTimeFormatter

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [NuevoMedicamentoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NuevoMedicamentoFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var fbinding: FragmentNuevoMedicamentoBinding
    private val args: NuevoMedicamentoFragmentArgs by navArgs()
    private lateinit var daoMedicamentos: DaoMedicamento
    private var idMascota = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fbinding = FragmentNuevoMedicamentoBinding.inflate(layoutInflater)
        daoMedicamentos = args.daoMedicamento
        idMascota = args.idMascota

        var navController = findNavController()
        /* Devolver daoMedicamentos a MostrarMedicamentosFragment */
        navController.previousBackStackEntry?.savedStateHandle?.set("NuevoMedicamento", daoMedicamentos)

        iniciar()
        return fbinding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun iniciar() {
        fbinding.editTextDate.setOnClickListener{showDatePickerDialog()}
        fbinding.etHoraPrimNuevMed.setOnClickListener{showTimePickerDialog()}
        fbinding.btnGuardarNuevMed.setOnClickListener {
            save()
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun save() {
        try{
            val medicamento = fbinding.etNuevoMedicamento.text.toString()
            val intervalo = fbinding.etIntervaloNuevoMed.text.toString().toInt()
            val horaInicial = fbinding.etHoraPrimNuevMed.text.toString()
            val fechaFin = fbinding.editTextDate.text.toString()
            Log.wtf("NUEVO_MEDICAMENTO", ">>>>>>>>>>>$horaInicial")

            val date = LocalDate.parse(fechaFin, DateTimeFormatter.ISO_LOCAL_DATE)
            if(daoMedicamentos.agregarMedic(idMascota, medicamento, intervalo, horaInicial, date)){
                Toast.makeText(activity,"Se a guardado exitosamente", Toast.LENGTH_SHORT).show()
            }

        }catch (ex:Exception){
            Toast.makeText(activity, "Los campos deben de ser rellenados", Toast.LENGTH_SHORT).show()
        }
    }

    /* Funciones de gestion de tiempo*/
    private fun showTimePickerDialog() {
        val timePicker = TimePickerFragment {onTimeSelected(it)}
        timePicker.show(parentFragmentManager, "time")
    }

    private fun onTimeSelected(time:String){
        fbinding.etHoraPrimNuevMed.setText("$time")
    }

    /* Funciones de gestion de fecha */
    private fun showDatePickerDialog() {
        val datePicker = DatePickerFragment {day, month, year -> onDateSelected(day, month, year)}
        datePicker.show(parentFragmentManager, "datePicker" )
    }
    fun onDateSelected(day: Int, month: Int, year: Int){
        if (day >= 1 && day <= 9 && month > 9) {
            fbinding.editTextDate.setText("$year-$month-0$day")

        }

        if (day >= 1 && day <= 9 && month >= 1 && month <= 9) {
            fbinding.editTextDate.setText("$year-0$month-0$day")

        }
        if (month >= 1 && month <= 9 && day >= 10) {
            fbinding.editTextDate.setText("$year-0$month-$day")
        }
        if (month > 9 && day > 9) {
            fbinding.editTextDate.setText("$year-$month-$day")
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment NuevoMedicamentoFragment.
         */
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            NuevoMedicamentoFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}