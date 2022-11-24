package ni.edu.uca.petscare

import android.app.AlertDialog
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ni.edu.uca.petscare.dao.DaoVacuna
import ni.edu.uca.petscare.databinding.FragmentEditarMedicamentoBinding
import ni.edu.uca.petscare.databinding.FragmentEditarVacunaBinding
import java.time.LocalDate
import java.time.format.DateTimeFormatter


// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


/**
 * A simple [Fragment] subclass.
 * Use the [EditarVacunaFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EditarVacunaFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var fbinding: FragmentEditarVacunaBinding
    private lateinit var daoVacuna: DaoVacuna
    private val args: EditarVacunaFragmentArgs by navArgs()
    private var idVacuna = 0
    private var vacunaEliminada: Boolean = false

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
        fbinding = FragmentEditarVacunaBinding.inflate(layoutInflater)
        daoVacuna = args.daoVacuna
        idVacuna = args.idVacuna
        var navController = findNavController()
        /* Devolver DaoVacunas a MostrarVacunasFragment */
        navController.previousBackStackEntry?.savedStateHandle?.set("EditarVacuna", daoVacuna)
        iniciar()
        return fbinding.root

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun iniciar() {
        val vacuna = daoVacuna.buscarVacunaID(idVacuna)
        fbinding.etVacunaEditar.setText(vacuna?.nombreVacuna)
        fbinding.etClinicaEditar.setText(vacuna?.clinica)
        fbinding.etFechaProgEditar.setText(daoVacuna.obtenerFechaVacunacion(vacuna!!))

        fbinding.etFechaProgEditar.setOnClickListener { showDatePickerDialog() }
        fbinding.btnGuardarEditVac.setOnClickListener {
            if (!vacunaEliminada) {
                save()
            } else {
                Toast.makeText(
                    activity,
                    "No se puede guardar porque \n la vacuna a sido eliminada",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        fbinding.btnEliminarEditVacuna.setOnClickListener {
            if(!vacunaEliminada) {
                try {
                    val confirmarAlert = AlertDialog.Builder(context)
                        .setTitle("ELIMINAR VACUNA")
                        .setMessage("¿Esta seguro que desea esta vacuna?")
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
                }
            }else {
                Toast.makeText(
                    activity,
                    "No se puede eliminar porque \n la vacuna ya sido eliminada",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun eliminar() {
        if (daoVacuna.eliminarVacuna(idVacuna)) {
            Toast.makeText(activity, "Se a eliminado la vacuna", Toast.LENGTH_SHORT)
                .show()
            vacunaEliminada = true
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun save() {
        try {
            val name = fbinding.etVacunaEditar.text.toString()
            val fechaProgramada = fbinding.etFechaProgEditar.text.toString()
            val clinica = fbinding.etClinicaEditar.text.toString()
            val estado = fbinding.cbEstadoEditar.selectedItem.toString()
            val date = LocalDate.parse(fechaProgramada, DateTimeFormatter.ISO_LOCAL_DATE)
            //año programado tiene que ser mayor al actual
            if (daoVacuna.editarVacuna(idVacuna, name, date, clinica, estado)) {
                Toast.makeText(activity, "Se han guardado los cambios", Toast.LENGTH_SHORT).show()
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            Toast.makeText(activity, "Los campos deben de ser rellenados", Toast.LENGTH_SHORT)
                .show()
        }
        limpiar()
    }

    private fun limpiar() {
        with(fbinding) {
            etVacunaEditar.setText("")
            etFechaProgEditar.setText("")
            etClinicaEditar.setText("")
        }
    }

    private fun showDatePickerDialog() {
        val datePicker = DatePickerFragment { day, month, year -> onDateSelected(day, month, year) }
        datePicker.show(parentFragmentManager, "datePicker")
    }

    fun onDateSelected(day: Int, month: Int, year: Int) {
        if (day >= 1 && day <= 9 && month > 9) {
            fbinding.etFechaProgEditar.setText("$year-$month-0$day")
        }
        if (day >= 1 && day <= 9 && month >= 1 && month <= 9) {
            fbinding.etFechaProgEditar.setText("$year-0$month-0$day")
        }
        if (month >= 1 && month <= 9 && day >= 10) {
            fbinding.etFechaProgEditar.setText("$year-0$month-$day")
        }
        if (month > 9 && day > 9) {
            fbinding.etFechaProgEditar.setText("$year-$month-$day")
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment EditarVacunaFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            EditarVacunaFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}