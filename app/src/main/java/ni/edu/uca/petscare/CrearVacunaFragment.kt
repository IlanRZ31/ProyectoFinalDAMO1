package ni.edu.uca.petscare

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ni.edu.uca.petscare.dao.DaoVacuna
import ni.edu.uca.petscare.databinding.FragmentNuevaVacunaBinding
import ni.edu.uca.petscare.databinding.FragmentNuevoMedicamentoBinding
import java.time.LocalDate
import java.time.format.DateTimeFormatter

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CrearVacunaFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NuevaVacunaFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var fbinding: FragmentNuevaVacunaBinding
    private val args: NuevaVacunaFragmentArgs by navArgs()
    private lateinit var daoVacuna: DaoVacuna
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
        fbinding = FragmentNuevaVacunaBinding.inflate(layoutInflater)
        daoVacuna = args.daoVacuna
        idMascota = args.idMascota
        var navController = findNavController()

        /* Devolver daoVacuna a MostrarVacunaFragment*/
        navController.previousBackStackEntry?.savedStateHandle?.set("CrearVacuna", daoVacuna)

        iniciar()
        return fbinding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun iniciar() {
        fbinding.etFechaProgramada.setOnClickListener { showDatePickerDialog() }
        fbinding.btnGuardar.setOnClickListener {
            save()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun save() {
        try {
            val name = fbinding.etVacuna.text.toString()
            val fechaProgramada = fbinding.etFechaProgramada.text.toString()
            val clinica = fbinding.etClinica.text.toString()
            val date = LocalDate.parse(fechaProgramada, DateTimeFormatter.ISO_LOCAL_DATE)
            if(daoVacuna.agregarVacuna(idMascota, name, date, clinica, "Programado")){
                Toast.makeText(activity, "Se a guardado exitosamente", Toast.LENGTH_SHORT).show()
            }

        } catch (ex: Exception) {
            ex.printStackTrace()
            Toast.makeText(activity, "Todos los campos deben de ser rellenados", Toast.LENGTH_SHORT)
                .show()
            return
        }
        limpiar()

    }
    private fun limpiar() {
        with(fbinding){
            etVacuna.setText("")
            etClinica.setText("")
            etFechaProgramada.setText("")
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun showDatePickerDialog() {
        val datePicker = DatePickerFragment { day, month, year -> onDateSelected(day, month, year) }
        datePicker.show(parentFragmentManager, "datePicker")
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun onDateSelected(day: Int, month: Int, year: Int) {
        val validacion = validarFecha(day, month, year)
        if (day >= 1 && day <= 9 && month > 9 && validacion == true) {
            fbinding.etFechaProgramada.setText("$year-$month-0$day")
        }else if (day >= 1 && day <= 9 && month >= 1 && month <= 9 && validacion == true) {
            fbinding.etFechaProgramada.setText("$year-0$month-0$day")
        }else if (month >= 1 && month <= 9 && day >= 10 && validacion == true) {
            fbinding.etFechaProgramada.setText("$year-0$month-$day")
        }else if (month > 9 && day > 9 && validacion == true) {
            fbinding.etFechaProgramada.setText("$year-$month-$day")
        }else if(validacion ==false){
            fbinding.etFechaProgramada.setText("")
            Toast.makeText(activity, "La fecha seleccionada es menor o igual a la actual", Toast.LENGTH_LONG).show()
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun validarFecha(day: Int, month: Int, year: Int): Boolean {
        val dateTime = LocalDate.now()

        if (year >= dateTime.year && month == dateTime.month.value && day > dateTime.dayOfMonth) {
            return true

        } else if (year >= dateTime.year && month > dateTime.month.value) {
            return true
        } else if(year > dateTime.year && month < dateTime.month.value){
            return true
        }else {
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
         * @return A new instance of fragment CrearVacunaFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            NuevaVacunaFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}