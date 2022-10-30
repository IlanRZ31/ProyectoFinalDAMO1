package ni.edu.uca.petscare.rv_adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import ni.edu.uca.petscare.R

// TODO: Cambiar list<String> a la estructura de datos que se vaya a usar
class VacunasAdapter(private val vacunasList: List<String>, private val currentView: View) :
    RecyclerView.Adapter<VacunasAdapter.VacunasViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VacunasViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return VacunasViewHolder(layoutInflater.inflate(R.layout.recycler_vacunas, parent, false))
    }

    override fun onBindViewHolder(holder: VacunasViewHolder, position: Int) {
        val item = vacunasList[position]
        holder.load(item, currentView)
    }

    override fun getItemCount(): Int = vacunasList.size


    inner class VacunasViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private var nombreVacuna = view.findViewById<TextView>(R.id.tvNombreVacunaRecycler)
        private var fragRecyclerVacuna = view.findViewById<ConstraintLayout>(R.id.frag_vacunas)
        fun load(vacuna: String, view: View) {
            nombreVacuna.text = vacuna
            fragRecyclerVacuna.setOnClickListener {
                Navigation.findNavController(view).navigate(R.id.acMostrarVacunaEditarVacuna)
            }
        }

    }


}