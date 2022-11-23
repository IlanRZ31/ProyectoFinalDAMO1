package ni.edu.uca.petscare

import android.app.AlertDialog
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import ni.edu.uca.petscare.dao.DaoMascota
import ni.edu.uca.petscare.dao.DaoMedicamento
import ni.edu.uca.petscare.dao.DaoVacuna
import ni.edu.uca.petscare.databinding.FragmentVistaMascotaBinding

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [VistaMascotaFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class VistaMascotaFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var fbinding: FragmentVistaMascotaBinding
    private val args: VistaMascotaFragmentArgs by navArgs()
    private lateinit var daoVacuna: DaoVacuna
    private lateinit var daoMascota: DaoMascota
    private lateinit var daoMedicamento: DaoMedicamento
    private var idMascota: Int = 0
    private var mascotaEliminada: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        setHasOptionsMenu(true) //idc it's deprecated, it works
    }

    /**
     * Agregando funcionalidad para el menu de tipo "Kebab"
     */
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_mascotas, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.iEditar -> {
                if (!mascotaEliminada) {
                    val action = VistaMascotaFragmentDirections.acVistaMascotaEditarMascota(
                        idMascota,
                        daoMascota
                    )
                    Navigation.findNavController(fbinding.root)
                        .navigate(action)
                } else {
                    Toast.makeText(
                        activity,
                        "No se puede editar porque\n la mascota fue eliminada",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            R.id.iEliminar -> {
                if (!mascotaEliminada) {
                    val confirmarAlert = AlertDialog.Builder(context)
                        .setTitle("ELIMINAR MASCOTA")
                        .setMessage("¿Esta seguro que desea a esta mascota?")
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
                } else {
                    Toast.makeText(
                        activity,
                        "No se puede eliminar porque\n la mascota ya fue eliminada",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
        return true
    }

    private fun eliminar() {
        try {
            if (daoMascota.eliminarMascota(idMascota) &&
                daoMedicamento.eliminarMedicamentoWhere(idMascota) &&
                daoVacuna.eliminarVacunaWhere(idMascota)) {
                Toast.makeText(
                    context,
                    "Registro eliminado exitosamente",
                    Toast.LENGTH_SHORT
                ).show()
                mascotaEliminada = true
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            Toast.makeText(
                context,
                "ERROR: No se a podido eliminar la mascota",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fbinding = FragmentVistaMascotaBinding.inflate(layoutInflater)
        daoMascota = args.daoMascotas
        idMascota = args.idMascota

        daoVacuna = args.daoVacuna
        daoMedicamento = args.daoMedicamento

        /*Devolver data a MostrarMascotasFragment*/
        val navController = findNavController()
        navController.previousBackStackEntry?.savedStateHandle?.set("VistaMascota", daoMascota)

        /* Devolver daoMedicamento a MostrarMascotasFragment */
        navController.previousBackStackEntry?.savedStateHandle?.set(
            "VistaMascota_Medicamento",
            daoMedicamento
        )

        /* Devolver daoVacuna a MostrarMascotasFragment */
        navController.previousBackStackEntry?.savedStateHandle?.set(
            "VistaMascota_Vacuna",
            daoVacuna
        )
        /* Obtener data de EditarMascotaFragment*/
        val navController2 = findNavController()
        navController2.currentBackStackEntry?.savedStateHandle?.getLiveData<DaoMascota>("EditarMascota")
            ?.observe(viewLifecycleOwner) { result -> daoMascota = result }

        /* Obtener daoVacuna de MostrarVacunaFragment */
        navController.currentBackStackEntry?.savedStateHandle?.getLiveData<DaoVacuna>("MostrarVacuna")
            ?.observe(viewLifecycleOwner) { result -> daoVacuna = result }

        /* Obtener daoMedicamento de MostrarMedicamentoFragment */
        navController.currentBackStackEntry?.savedStateHandle?.getLiveData<DaoMedicamento>("MostrarMedicamento")
            ?.observe(viewLifecycleOwner) { result -> daoMedicamento = result }

        iniciar()
        return fbinding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun iniciar() {
        val mascota = daoMascota.buscarMascotaID(idMascota)
        fbinding.tvNombreMascotaDetallada.text = mascota?.nombre
        fbinding.textView.text = mascota?.raza
        fbinding.textView2.text = daoMascota.obtenerFechaNacimiento(mascota!!)
        fbinding.tvPesoVistaMascota.text = "${mascota.peso.toString()} kg"
        fbinding.tvTipoVistaMascota.text = mascota.tipo
        fbinding.imageView.setImageDrawable(mascota.image.drawable)
        fbinding.btnMenuVacunas.setOnClickListener {
            if (!mascotaEliminada) {
                val action =
                    VistaMascotaFragmentDirections.acVistaMascotaMostrarVacunas(
                        daoVacuna,
                        idMascota
                    )
                Navigation.findNavController(fbinding.root).navigate(action)
            } else {
                Toast.makeText(
                    activity,
                    "No se puede acceder a vacunas porque\n la mascota fue eliminada",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        fbinding.btnMenuTratamiento.setOnClickListener {
            if (!mascotaEliminada) {
                val action = VistaMascotaFragmentDirections.acVistaMascotaMostrarMedicamentos(
                    idMascota,
                    daoMedicamento
                )
                Navigation.findNavController(fbinding.root).navigate(action)
            } else {
                Toast.makeText(
                    activity,
                    "No se puede acceder a los medicamentos porque\n la mascota fue eliminada",
                    Toast.LENGTH_SHORT
                ).show()
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
         * @return A new instance of fragment VistaMascotaFragment.
         */
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            VistaMascotaFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}