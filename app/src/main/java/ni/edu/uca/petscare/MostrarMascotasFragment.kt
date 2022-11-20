package ni.edu.uca.petscare

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import ni.edu.uca.petscare.dao.DaoMascota
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
        fbinding = FragmentMostrarMascotasBinding.inflate(layoutInflater)
        val navController = findNavController()
        /* Codigo que toma los valores del siguiente fragmento */
        navController.currentBackStackEntry?.savedStateHandle?.getLiveData<DaoMascota>("NuevaMascota")
            ?.observe(viewLifecycleOwner) {result -> mascotas = result}

        navController.currentBackStackEntry?.savedStateHandle?.getLiveData<DaoMascota>("VistaMascota")
            ?.observe(viewLifecycleOwner) {result -> mascotas = result}
        iniciar()
        return fbinding.root
    }

    private fun iniciar() {
        /* Navegar al fragmento "Nuevas mascotas" */
        fbinding.btnNuevaMascota.setOnClickListener {
            val action = MostrarMascotasFragmentDirections.acMostrarMacotasNuevaMascota(mascotas) // might fail, so far so good
            Navigation.findNavController(fbinding.root).navigate(action)
        }
        initRecyclerView()
    }

    override fun onResume() {
        super.onResume()
        Log.wtf("MOSTRAR_MASCOTAS", ">>>>>>>>>>>>> SE A LLAMADO A ESTE METODO")
        for(x in mascotas.listMascota){
            Log.wtf("MOSTRAR_MASCOTAS", ">>>>>>>>>>>>>> ${x.idMascota}\n" +
                    "${x.nombre} \n" +
                    "${x.tipo}\n" +
                    "${x.raza}\n" +
                    "${x.peso}")
        }
        initRecyclerView()
    }

    private fun initRecyclerView() {
        val recyclerView = fbinding.rvMascotas
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = MascotasAdapter( mascotas, mascotas.mostrarMascotas(), fbinding.root)
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