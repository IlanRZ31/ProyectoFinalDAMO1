package ni.edu.uca.petscare

import android.app.AlertDialog
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
import ni.edu.uca.petscare.dao.DaoMedicamento
import ni.edu.uca.petscare.databinding.FragmentEditarMascotaBinding
import ni.edu.uca.petscare.databinding.FragmentEditarMedicamentoBinding
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [EditarMedicamentoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EditarMedicamentoFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var fbinding: FragmentEditarMedicamentoBinding
    private val args: EditarMedicamentoFragmentArgs by navArgs()
    private var idMedicamento = 0
    private lateinit var daoMedicamento: DaoMedicamento
    private var medicamentoEliminado: Boolean = false

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
        fbinding = FragmentEditarMedicamentoBinding.inflate(layoutInflater)
        daoMedicamento = args.daoMedic
        idMedicamento = args.idMedicamento

        val navController = findNavController()
        /* Devolver daoMedicamento a MostrarMedicamentoFragment */
        navController.previousBackStackEntry?.savedStateHandle?.set(
            "EditarMedicamento",
            daoMedicamento
        )

        iniciar()
        return fbinding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun iniciar() {

        val medicamento = daoMedicamento.buscarMedicamento(idMedicamento)
        var hourOfDay: Int? = medicamento?.horaInicial?.hours
        var minute: Int? = medicamento?.horaInicial?.minutes
        if (hourOfDay != null && minute != null) {
            if (hourOfDay >= 0 && hourOfDay < 10 && minute > 9){
                var tiempo = "0${medicamento?.horaInicial?.hours}:${medicamento?.horaInicial?.minutes}"
                fbinding.etEditMedicamento.setText(medicamento?.nombreMedicamento)
                fbinding.etIntervaloEditMed.setText(medicamento?.intervaloTiempo.toString())
                fbinding.etHoraPrimeraEditar.setText(tiempo)
                fbinding.etFechaFinEdit.setText(daoMedicamento.obtenerFechaFin(medicamento!!))

                fbinding.etFechaFinEdit.setOnClickListener { showDatePickerDialog() }
                fbinding.etHoraPrimeraEditar.setOnClickListener { showTimePickerDialog() }
                fbinding.btnGuardarEditMed.setOnClickListener {
                    if (!medicamentoEliminado) {
                        save()
                    } else {
                        Toast.makeText(
                            activity,
                            "No se puede guardar porque \n el medicamento a sido eliminado",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                fbinding.btnEliminarEditMed.setOnClickListener {
                    if (!medicamentoEliminado) {
                        try {
                            val confirmarAlert = AlertDialog.Builder(context)
                                .setTitle("ELIMINAR MEDICAMENTO")
                                .setMessage("??Esta seguro que desea este medicamento?")
                                .setIcon(R.drawable.ic_warning)
                                .setPositiveButton("Si") { _, _ ->
                                    eliminar()
                                }
                                .setNegativeButton("No") { _, _ ->
                                    Toast.makeText(
                                        context,
                                        "Abortado",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }.create()
                            confirmarAlert.show()
                        } catch (ex: Exception) {
                            ex.printStackTrace()
                            Toast.makeText(activity, "Error al borrar medicamento", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }else {
                        Toast.makeText(
                            activity,
                            "No se puede eliminar porque \n el medicamento ya sido eliminado",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }else if(hourOfDay >= 0 && hourOfDay < 10 && minute < 10){
                var tiempo = "0${medicamento?.horaInicial?.hours}:0${medicamento?.horaInicial?.minutes}"
                fbinding.etEditMedicamento.setText(medicamento?.nombreMedicamento)
                fbinding.etIntervaloEditMed.setText(medicamento?.intervaloTiempo.toString())
                fbinding.etHoraPrimeraEditar.setText(tiempo)
                fbinding.etFechaFinEdit.setText(daoMedicamento.obtenerFechaFin(medicamento!!))

                fbinding.etFechaFinEdit.setOnClickListener { showDatePickerDialog() }
                fbinding.etHoraPrimeraEditar.setOnClickListener { showTimePickerDialog() }
                fbinding.btnGuardarEditMed.setOnClickListener {
                    if (!medicamentoEliminado) {
                        save()
                    } else {
                        Toast.makeText(
                            activity,
                            "No se puede guardar porque \n el medicamento a sido eliminado",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                fbinding.btnEliminarEditMed.setOnClickListener {
                    if (!medicamentoEliminado) {
                        try {
                            val confirmarAlert = AlertDialog.Builder(context)
                                .setTitle("ELIMINAR MEDICAMENTO")
                                .setMessage("??Esta seguro que desea este medicamento?")
                                .setIcon(R.drawable.ic_warning)
                                .setPositiveButton("Si") { _, _ ->
                                    eliminar()
                                }
                                .setNegativeButton("No") { _, _ ->
                                    Toast.makeText(
                                        context,
                                        "Abortado",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }.create()
                            confirmarAlert.show()
                        } catch (ex: Exception) {
                            ex.printStackTrace()
                            Toast.makeText(activity, "Error al borrar medicamento", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }else {
                        Toast.makeText(
                            activity,
                            "No se puede eliminar porque \n el medicamento ya sido eliminado",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }else if(hourOfDay > 9 && minute < 10){
                var tiempo = "${medicamento?.horaInicial?.hours}:0${medicamento?.horaInicial?.minutes}"
                fbinding.etEditMedicamento.setText(medicamento?.nombreMedicamento)
                fbinding.etIntervaloEditMed.setText(medicamento?.intervaloTiempo.toString())
                fbinding.etHoraPrimeraEditar.setText(tiempo)
                fbinding.etFechaFinEdit.setText(daoMedicamento.obtenerFechaFin(medicamento!!))

                fbinding.etFechaFinEdit.setOnClickListener { showDatePickerDialog() }
                fbinding.etHoraPrimeraEditar.setOnClickListener { showTimePickerDialog() }
                fbinding.btnGuardarEditMed.setOnClickListener {
                    if (!medicamentoEliminado) {
                        save()
                    } else {
                        Toast.makeText(
                            activity,
                            "No se puede guardar porque \n el medicamento a sido eliminado",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                fbinding.btnEliminarEditMed.setOnClickListener {
                    if (!medicamentoEliminado) {
                        try {
                            val confirmarAlert = AlertDialog.Builder(context)
                                .setTitle("ELIMINAR MEDICAMENTO")
                                .setMessage("??Esta seguro que desea este medicamento?")
                                .setIcon(R.drawable.ic_warning)
                                .setPositiveButton("Si") { _, _ ->
                                    eliminar()
                                }
                                .setNegativeButton("No") { _, _ ->
                                    Toast.makeText(
                                        context,
                                        "Abortado",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }.create()
                            confirmarAlert.show()
                        } catch (ex: Exception) {
                            ex.printStackTrace()
                            Toast.makeText(activity, "Error al borrar medicamento", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }else {
                        Toast.makeText(
                            activity,
                            "No se puede eliminar porque \n el medicamento ya sido eliminado",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }else{
                var tiempo = "${medicamento?.horaInicial?.hours}:${medicamento?.horaInicial?.minutes}"
                fbinding.etEditMedicamento.setText(medicamento?.nombreMedicamento)
                fbinding.etIntervaloEditMed.setText(medicamento?.intervaloTiempo.toString())
                fbinding.etHoraPrimeraEditar.setText(tiempo)
                fbinding.etFechaFinEdit.setText(daoMedicamento.obtenerFechaFin(medicamento!!))

                fbinding.etFechaFinEdit.setOnClickListener { showDatePickerDialog() }
                fbinding.etHoraPrimeraEditar.setOnClickListener { showTimePickerDialog() }
                fbinding.btnGuardarEditMed.setOnClickListener {
                    if (!medicamentoEliminado) {
                        save()
                    } else {
                        Toast.makeText(
                            activity,
                            "No se puede guardar porque \n el medicamento a sido eliminado",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                fbinding.btnEliminarEditMed.setOnClickListener {
                    if (!medicamentoEliminado) {
                        try {
                            val confirmarAlert = AlertDialog.Builder(context)
                                .setTitle("ELIMINAR MEDICAMENTO")
                                .setMessage("??Esta seguro que desea este medicamento?")
                                .setIcon(R.drawable.ic_warning)
                                .setPositiveButton("Si") { _, _ ->
                                    eliminar()
                                }
                                .setNegativeButton("No") { _, _ ->
                                    Toast.makeText(
                                        context,
                                        "Abortado",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }.create()
                            confirmarAlert.show()
                        } catch (ex: Exception) {
                            ex.printStackTrace()
                            Toast.makeText(activity, "Error al borrar medicamento", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }else {
                        Toast.makeText(
                            activity,
                            "No se puede eliminar porque \n el medicamento ya sido eliminado",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }

        }
    }

    private fun eliminar() {
        if (daoMedicamento.eliminarMedic(idMedicamento)) {
            Toast.makeText(
                activity,
                "El medicamento a sido eliminado",
                Toast.LENGTH_SHORT
            )
                .show()
            medicamentoEliminado = true
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun save() {
        try {
            val name = fbinding.etEditMedicamento.text.toString()
            val intervalo = fbinding.etIntervaloEditMed.text.toString().toInt()
            val horaInicial = fbinding.etHoraPrimeraEditar.text.toString()
            val fechaFin = fbinding.etFechaFinEdit.text.toString()
            val siguienteDosis = segundaDosis(horaInicial, intervalo)
            val date = LocalDate.parse(fechaFin, DateTimeFormatter.ISO_LOCAL_DATE)
            if (daoMedicamento.editarMedic(idMedicamento, name, intervalo, horaInicial, date, siguienteDosis)) {
                Toast.makeText(activity, "Se a guardado exitosamente", Toast.LENGTH_SHORT).show()
            }

        } catch (ex: Exception) {
            Toast.makeText(activity, "Los campos deben de ser rellenados", Toast.LENGTH_SHORT)
                .show()
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
            return "${result}:${localTime.minute}"
        }


    }

    private fun showTimePickerDialog() {
        val timePicker = TimePickerFragment { onTimeSelected(it) }
        timePicker.show(parentFragmentManager, "time")
    }

    private fun onTimeSelected(time: String) {
        fbinding.etHoraPrimeraEditar.setText("$time")
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
            fbinding.etFechaFinEdit.setText("$year-$month-0$day")

        }else if (day >= 1 && day <= 9 && month >= 1 && month <= 9 && validacion == true) {

            fbinding.etFechaFinEdit.setText("$year-0$month-0$day")

        } else if (month >= 1 && month <= 9 && day >= 10 && validacion == true) {
            fbinding.etFechaFinEdit.setText("$year-0$month-$day")
        } else if (month > 9 && day > 9 && validacion == true) {
            fbinding.etFechaFinEdit.setText("$year-$month-$day")
        } else if (validacion == false) {
            fbinding.etFechaFinEdit.setText("")
            Toast.makeText(
                activity,
                "La fecha seleccionada es menor a la actual",
                Toast.LENGTH_LONG
            ).show()
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
         * @return A new instance of fragment EditarMedicamentoFragment.
         */
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