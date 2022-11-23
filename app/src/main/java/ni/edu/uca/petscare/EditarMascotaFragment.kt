package ni.edu.uca.petscare

import android.content.Intent
import android.net.Uri
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
import ni.edu.uca.petscare.dao.DaoMascota
import ni.edu.uca.petscare.databinding.FragmentEditarMascotaBinding
import ni.edu.uca.petscare.entidades.Mascota
import java.time.LocalDate
import java.time.format.DateTimeFormatter

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [EditarMascotaFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EditarMascotaFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null
    private lateinit var fbinding: FragmentEditarMascotaBinding
    private val args: EditarMascotaFragmentArgs by navArgs()
    private var idMascota: Int = 0
    private lateinit var daoMascota: DaoMascota

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
        fbinding = FragmentEditarMascotaBinding.inflate(layoutInflater)
        idMascota = args.idMascota
        daoMascota = args.daoMascotas
        val navController = findNavController()
        navController.previousBackStackEntry?.savedStateHandle?.set("EditarMascota", daoMascota)
        iniciar()
        return fbinding.root
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun iniciar() {
        var mascota: Mascota? = daoMascota.buscarMascotaID(idMascota)
        fbinding.etENombre.setText(mascota?.nombre)
        fbinding.etERaza.setText(mascota?.raza)
        fbinding.etEPeso.setText(mascota?.peso.toString())
        fbinding.ivEImagen.setImageDrawable(mascota?.image?.drawable)

        fbinding.etEFechaNacimiento.setOnClickListener{showDatePickerDialog()}
        fbinding.ivEImagen.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_GET_CONTENT
            intent.type="image/*"
            startActivityForResult(intent, 100)
        }
        fbinding.btnEGuardarNuevMed.setOnClickListener {
            save()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun save() {
        try {
            val nombre = fbinding.etENombre.text.toString()
            val tipo = fbinding.spETipo.selectedItem.toString()
            val raza = fbinding.etERaza.text.toString()
            val fechaNacimiento = fbinding.etEFechaNacimiento.text.toString()
            val peso = fbinding.etEPeso.text.toString().toInt()
            val image = fbinding.ivEImagen
            val date = LocalDate.parse(fechaNacimiento, DateTimeFormatter.ISO_LOCAL_DATE)
            if(daoMascota.editarMascota(idMascota, nombre, tipo, raza, date, peso, image)){
                Toast.makeText(activity, "Se a guardado exitosamente", Toast.LENGTH_SHORT).show()
            }
        }catch (ex:Exception){
            ex.printStackTrace()
            Toast.makeText(
                activity, "Los campos deben de ser rellenados",
                Toast.LENGTH_LONG
            ).show()
            return
        }
        limpiar()
    }

    private fun limpiar() {
        with(fbinding) {
            etENombre.setText("")
            etERaza.setText("")
            etEFechaNacimiento.setText("")
            etEPeso.setText("")
            etENombre.requestFocus()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun showDatePickerDialog() {
        val datePicker = DatePickerFragment {day, month, year -> onDateSelected(day, month, year)}
        datePicker.show(parentFragmentManager, "datePicker" )
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun onDateSelected(day: Int, month: Int, year: Int){
        val validacion = validarFecha(day, month, year)
        if (day >= 1 && day <= 9 && month > 9 && validacion == true) {
            fbinding.etEFechaNacimiento.setText("$year-$month-0$day")
        }else if (day >= 1 && day <= 9 && month >= 1 && month <= 9 && validacion == true) {
            fbinding.etEFechaNacimiento.setText("$year-0$month-0$day")

        }else if (month >= 1 && month <= 9 && day >= 10 && validacion == true) {
            fbinding.etEFechaNacimiento.setText("$year-0$month-$day")
        }else if (month > 9 && day > 9 && validacion == true) {
            fbinding.etEFechaNacimiento.setText("$year-$month-$day")
        }else if(validacion ==false){
            fbinding.etEFechaNacimiento.setText("")
            Toast.makeText(activity, "La fecha seleccionada es mayor a la actual", Toast.LENGTH_LONG).show()
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun validarFecha(day: Int, month: Int, year: Int): Boolean {
        val dateTime = LocalDate.now()

        if (year <= dateTime.year && month == dateTime.month.value && day <= dateTime.dayOfMonth) {
            return true

        } else if (year <= dateTime.year && month < dateTime.month.value) {
            return true
        } else if(year < dateTime.year && month > dateTime.month.value){
            return true
        }else {
            return false
        }

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 100){
            if (data!=null){
                val uri: Uri = data.data!!
                fbinding.ivEImagen.setImageURI(uri)
            }
            else{
                Toast.makeText(activity, "No ha seleccionado ninguna imagen", Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment EditarMascotaFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            EditarMascotaFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}