package ni.edu.uca.petscare

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import ni.edu.uca.petscare.databinding.FragmentMostrarMascotasBinding
import ni.edu.uca.petscare.rv_adapters.MascotasAdapter

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
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
    private lateinit var mascotas: MutableList<String>
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
        iniciar()
        return fbinding.root
    }

    private fun iniciar() {
        /**
         * Navegar al fragmento "Nuevas mascotas"
         */
        mascotas = arrayListOf()
        fbinding.btnNuevaMascota.setOnClickListener {
            Navigation.findNavController(fbinding.root).navigate(R.id.acMostrarMacotasNuevaMascota)
        }
        initRecyclerView()
    }

    private fun initRecyclerView() {
        val recyclerView = fbinding.rvMascotas
        recyclerView.layoutManager = LinearLayoutManager(activity)
        mascotas.add("pepe")
        mascotas.add("el mero")
        mascotas.add("xd")
        recyclerView.adapter = MascotasAdapter(mascotas)
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
        // TODO: Rename and change types and number of parameters
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