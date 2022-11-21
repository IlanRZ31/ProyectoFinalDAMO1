package ni.edu.uca.petscare

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import ni.edu.uca.petscare.dao.DaoMedicamento
import ni.edu.uca.petscare.databinding.FragmentMostrarMascotasBinding
import ni.edu.uca.petscare.databinding.FragmentMostrarMedicamentosBinding
import ni.edu.uca.petscare.rv_adapters.MedicamentoAdapter

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MostrarMedicamentosFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MostrarMedicamentosFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var fbinding: FragmentMostrarMedicamentosBinding
    private val args: MostrarMedicamentosFragmentArgs by navArgs()
    private lateinit var daoMedicamento: DaoMedicamento
    private var idMascota = 0

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
        // Inflate the layout for this fragment
        fbinding = FragmentMostrarMedicamentosBinding.inflate(layoutInflater)
        daoMedicamento = args.daoMedicamento
        idMascota = args.idMascota

        var navController = findNavController()
        /* Devolver daoMedicamentos a VistaMascotaFragment */
        navController.previousBackStackEntry?.savedStateHandle?.set("MostrarMedicamento", daoMedicamento)

        /* Obtener daoMedicamentos de NuevoMedicamentoFragment*/
        navController.currentBackStackEntry?.savedStateHandle?.getLiveData<DaoMedicamento>("NuevoMedicamento")
            ?.observe(viewLifecycleOwner){result -> daoMedicamento = result}

        /* Obtener daoMedicamentos de EditarMedicamentoFragment */
        navController.currentBackStackEntry?.savedStateHandle?.getLiveData<DaoMedicamento>("EditarMedicamento")
            ?.observe(viewLifecycleOwner){result -> daoMedicamento = result}

        iniciar()
        return fbinding.root
    }

    private fun iniciar() {
        fbinding.btnNuevoMedicamento.setOnClickListener {
            val action = MostrarMedicamentosFragmentDirections.acMostrarMedicamentoNuevoMedicamento(idMascota, daoMedicamento)
            Navigation.findNavController(fbinding.root).navigate(action)
        }
        initRecyclerView()
    }

    private fun initRecyclerView() {

        fbinding.rvMedicamentos.layoutManager = LinearLayoutManager(context)
        fbinding.rvMedicamentos.setHasFixedSize(true)
        fbinding.rvMedicamentos.adapter = MedicamentoAdapter(daoMedicamento, daoMedicamento.mostrarMedic(idMascota), fbinding.root)
    }

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
         * @return A new instance of fragment MostrarMedicamentosFragment.
         */
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MostrarMedicamentosFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}