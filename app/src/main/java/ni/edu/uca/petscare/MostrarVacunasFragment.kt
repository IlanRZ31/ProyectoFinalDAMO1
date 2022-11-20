package ni.edu.uca.petscare

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import ni.edu.uca.petscare.dao.DaoVacuna
import ni.edu.uca.petscare.databinding.FragmentMostrarVacunasBinding
import ni.edu.uca.petscare.rv_adapters.VacunasAdapter

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MostrarVacunasFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MostrarVacunasFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var fbinding: FragmentMostrarVacunasBinding
    private val args: MostrarVacunasFragmentArgs by navArgs()
    private lateinit var daoVacunas: DaoVacuna
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
        fbinding = FragmentMostrarVacunasBinding.inflate(layoutInflater)
        daoVacunas = args.daoVacuna
        idMascota = args.idMascota
        val navControllers = findNavController()

        /* Devolver DaoVacunas a VistaMascotaFragment */
        navControllers.previousBackStackEntry?.savedStateHandle?.set("MostrarVacuna", daoVacunas)

        /* Obtener DaoVacuna de CrearVacunaFragment */
        navControllers.currentBackStackEntry?.savedStateHandle?.getLiveData<DaoVacuna>("CrearVacuna")
            ?.observe(viewLifecycleOwner){result -> daoVacunas = result}

        /* Obtener DaoVacuna de EditarVacunaFragment */
        navControllers.currentBackStackEntry?.savedStateHandle?.getLiveData<DaoVacuna>("EditarVacuna")
            ?.observe(viewLifecycleOwner) {result -> daoVacunas = result}

        iniciar()
        return fbinding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun iniciar() {
        fbinding.btnNuevaVacuna.setOnClickListener {
            val action = MostrarVacunasFragmentDirections.acMostrarVacunasNuevaVacuna(daoVacunas, idMascota)
            Navigation.findNavController(fbinding.root).navigate(action)
        }
        initRecyclerView()

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun initRecyclerView() {
        fbinding.rvVacunas.layoutManager = LinearLayoutManager(context)
        fbinding.rvVacunas.setHasFixedSize(true)
        fbinding.rvVacunas.adapter =
            VacunasAdapter(daoVacunas, daoVacunas.mostrarVacuna(idMascota), fbinding.root)

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onResume() {
        super.onResume()
        initRecyclerView()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MostrarVacunasFragment.
         */
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MostrarVacunasFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}