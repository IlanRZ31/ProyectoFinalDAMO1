package ni.edu.uca.petscare

import android.content.Intent
import android.icu.text.SimpleDateFormat
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
import java.time.LocalDateTime
import java.time.LocalTime
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

    fun limpiar(){
        with(fbinding){
            etNuevoMedicamento.setText("")
            etHoraPrimNuevMed.setText("")
            etIntervaloNuevoMed.setText("")
            editTextDate.setText("")
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun segundaDosis(hora: String, intervalo: Int): String{
        var localTime = LocalTime.parse(hora, DateTimeFormatter.ISO_LOCAL_TIME)
        var result = localTime.hour + intervalo
        var test = result
        if (result >= 24){
            test -= 24
            return ("${0 + test}:${localTime.minute}")
        }else{

            return ("${result}:${localTime.minute}")
        }
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
            val siguienteDosis = segundaDosis(horaInicial, intervalo)
            Log.wtf("NUEVO_MEDICAMENTO", ">>>>>>>>>>>$horaInicial")

            val date = LocalDate.parse(fechaFin, DateTimeFormatter.ISO_LOCAL_DATE)
            if(daoMedicamentos.agregarMedic(idMascota, medicamento, intervalo, horaInicial, date, siguienteDosis)){
                Toast.makeText(activity,"Se a guardado exitosamente", Toast.LENGTH_SHORT).show()
            }
            limpiar()
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
    @RequiresApi(Build.VERSION_CODES.O)
    private fun showDatePickerDialog() {
        val datePicker = DatePickerFragment {day, month, year -> onDateSelected(day, month, year)}
        datePicker.show(parentFragmentManager, "datePicker" )
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun onDateSelected(day: Int, month: Int, year: Int){
        val validacion = validarFecha(day, month, year)
        if (day >= 1 && day <= 9 && month > 9 && validacion == true) {
            fbinding.editTextDate.setText("$year-$month-0$day")

        }else if (day >= 1 && day <= 9 && month >= 1 && month <= 9 && validacion == true) {
            fbinding.editTextDate.setText("$year-0$month-0$day")

        }else if (month >= 1 && month <= 9 && day >= 10 && validacion == true) {
            fbinding.editTextDate.setText("$year-0$month-$day")
        }else if (month > 9 && day > 9 && validacion == true) {
            fbinding.editTextDate.setText("$year-$month-$day")
        }else if(validacion ==false){
            fbinding.editTextDate.setText("")
            Toast.makeText(activity, "La fecha seleccionada es menor a la actual", Toast.LENGTH_LONG).show()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun validarFecha(day: Int, month: Int, year: Int): Boolean {
        val dateTime = LocalDate.now()

        if (year >= dateTime.year && month == dateTime.month.value && day >= dateTime.dayOfMonth) {
            return true

        } else if (year >= dateTime.year && month > dateTime.month.value) {
            return true
        } else if(year > dateTime.year && month < dateTime.month.value){
            return true
        } else {
            return false
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