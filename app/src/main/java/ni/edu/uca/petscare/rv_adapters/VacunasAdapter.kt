package ni.edu.uca.petscare.rv_adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import ni.edu.uca.petscare.*
import ni.edu.uca.petscare.dao.DaoMascota
import ni.edu.uca.petscare.dao.DaoVacuna
import ni.edu.uca.petscare.entidades.Vacuna

// TODO: Cambiar list<String> a la estructura de datos que se vaya a usar
class VacunasAdapter(var daoVacuna: DaoVacuna,  val vacunasList: List<Vacuna>, private val currentView: View) :
    RecyclerView.Adapter<VacunasAdapter.VacunasViewHolder>() {
    private lateinit var currentContext: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VacunasViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        currentContext = parent.context
        return VacunasViewHolder(layoutInflater.inflate(R.layout.recycler_vacunas, parent, false))
    }

    override fun onBindViewHolder(holder: VacunasViewHolder, position: Int) {
        val vacuna: Vacuna = vacunasList[position]
        holder.load(vacuna, currentView, daoVacuna)
    }

    override fun getItemCount(): Int = vacunasList.size


    inner class VacunasViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private var nombreVacuna = view.findViewById<TextView>(R.id.tvNombreVacunaRecycler)
        private var fechaProgramada = view.findViewById<TextView>(R.id.tvFechaProgramada)
        private var estadoVacuna = view.findViewById<TextView>(R.id.tvEstadoVacunaRV)
        private var fragRecyclerVacuna = view.findViewById<ConstraintLayout>(R.id.frag_vacunas)

        fun load(vacuna: Vacuna, view: View, daoVacuna: DaoVacuna) {
            nombreVacuna.text = vacuna.nombreVacuna
            fechaProgramada.text = vacuna.fechaVacunacion.toString()
            estadoVacuna.text = vacuna.estado

            fragRecyclerVacuna.setOnClickListener {
                val action = MostrarVacunasFragmentDirections.acMostrarVacunaEditarVacuna(vacuna.idVacuna, daoVacuna)
                Navigation.findNavController(view).navigate(action)
            }
        }

    }


}