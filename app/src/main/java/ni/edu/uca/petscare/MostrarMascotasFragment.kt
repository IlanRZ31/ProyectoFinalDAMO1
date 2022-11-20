package ni.edu.uca.petscare

import android.app.AlertDialog
import android.os.Build
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.*
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import ni.edu.uca.petscare.dao.DaoMascota
import ni.edu.uca.petscare.dao.DaoMedicamento
import ni.edu.uca.petscare.dao.DaoVacuna
import ni.edu.uca.petscare.databinding.FragmentMostrarMascotasBinding
import ni.edu.uca.petscare.rv_adapters.MascotasAdapter

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [mostrarMascotasFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MostrarMascotasFragment : Fragment() {
    private lateinit var fbinding: FragmentMostrarMascotasBinding
    private var param1: String? = null
    private var param2: String? = null
    private var mascotas = DaoMascota()
    private var vacunas = DaoVacuna()
    private var medicamentos = DaoMedicamento()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        setHasOptionsMenu(true)
    }

    /**
     * Crear menu en el action bar de cambezera
     * */
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_mascotas_filter, menu)
    }

    /**
     * Definir acciones en caso de que un boton sea selecionado
     * */
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.iSinFiltros -> initRecyclerView()
            R.id.iOrdenRaza -> {
                val recyclerView = fbinding.rvMascotas
                recyclerView.layoutManager = LinearLayoutManager(context)
                recyclerView.setHasFixedSize(true)
                recyclerView.adapter =
                    MascotasAdapter(medicamentos,vacunas, mascotas, mascotas.ordenarMascotaRaza(), fbinding.root)
            }
            R.id.iOrdenEspecie -> {
                for (x in mascotas.ordenarMascotaEspecie()) {
                    Log.wtf("MOSTRAR_MASCOTAS", ">>>>>>>>> ${x.tipo}")
                }
                val recyclerView = fbinding.rvMascotas
                recyclerView.layoutManager = LinearLayoutManager(context)
                recyclerView.setHasFixedSize(true)
                recyclerView.adapter =
                    MascotasAdapter(medicamentos,vacunas, mascotas, mascotas.ordenarMascotaEspecie(), fbinding.root)
            }
            R.id.iOrdenEdad -> {
                val recyclerView = fbinding.rvMascotas
                recyclerView.layoutManager = LinearLayoutManager(context)
                recyclerView.setHasFixedSize(true)
                recyclerView.adapter =
                    MascotasAdapter(medicamentos,vacunas, mascotas, mascotas.ordernarMascotaEdad(), fbinding.root)
            }
            R.id.iBuscarNombre -> {
                val mascota: EditText = EditText(context)
                mascota.inputType = InputType.TYPE_CLASS_TEXT

                val confirmarAlert = AlertDialog.Builder(context)
                    .setTitle("Buscar Mascotas")
                    .setMessage("Ingrese el nombre de las mascotas que desee buscar")
                    .setView(mascota)
                    .setPositiveButton("Ok") { _, _ ->
                        try {
                            val recyclerView = fbinding.rvMascotas
                            val nombre = mascota.text.toString()
                            recyclerView.layoutManager = LinearLayoutManager(context)
                            recyclerView.setHasFixedSize(true)
                            recyclerView.adapter =
                                MascotasAdapter(
                                    medicamentos,
                                    vacunas,
                                    mascotas,
                                    mascotas.buscarMascotaNombre(nombre),
                                    fbinding.root
                                )
                        } catch (ex: Exception) {
                            ex.printStackTrace()
                            Toast.makeText(
                                context,
                                "Ocurrio un error...",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                    .create()
                confirmarAlert.show()
            }
        }
        return true
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fbinding = FragmentMostrarMascotasBinding.inflate(layoutInflater)
        val navController = findNavController()
        /* Codigo que toma los valores del siguiente fragmento */
        /* MASCOTA: */
        navController.currentBackStackEntry?.savedStateHandle?.getLiveData<DaoMascota>("NuevaMascota")
            ?.observe(viewLifecycleOwner) { result -> mascotas = result }

        navController.currentBackStackEntry?.savedStateHandle?.getLiveData<DaoMascota>("VistaMascota")
            ?.observe(viewLifecycleOwner) { result -> mascotas = result }

        /* VACUNA: */
        navController.currentBackStackEntry?.savedStateHandle?.getLiveData<DaoVacuna>("VistaMascota_Vacuna")
            ?.observe(viewLifecycleOwner){ result -> vacunas = result}

        /* MEDICAMENTO */
        navController.currentBackStackEntry?.savedStateHandle?.getLiveData<DaoMedicamento>("VistaMascota_Medicamento")
            ?.observe(viewLifecycleOwner) { result -> medicamentos = result}
        iniciar()
        return fbinding.root
    }

    private fun iniciar() {
        /* Navegar al fragmento "Nuevas mascotas" */
        fbinding.btnNuevaMascota.setOnClickListener {
            val action =
                MostrarMascotasFragmentDirections.acMostrarMacotasNuevaMascota(mascotas) // might fail, so far so good
            Navigation.findNavController(fbinding.root).navigate(action)
        }
        initRecyclerView()
    }

    override fun onResume() {
        super.onResume()
        initRecyclerView()
    }

    private fun initRecyclerView() {
        val recyclerView = fbinding.rvMascotas
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)

        recyclerView.adapter = MascotasAdapter(medicamentos,vacunas, mascotas, mascotas.mostrarMascotas(), fbinding.root)

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment mostrarMascotasFragment.
         */

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MostrarMascotasFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}