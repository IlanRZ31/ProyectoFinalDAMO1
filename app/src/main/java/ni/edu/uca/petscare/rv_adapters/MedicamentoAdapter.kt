package ni.edu.uca.petscare.rv_adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import ni.edu.uca.petscare.R

// TODO: Cambiar list<String> a la estructura de datos que se vaya a usar
class MedicamentoAdapter(private val medicamentoList: List<String>, currentView: View) :
    RecyclerView.Adapter<MedicamentoAdapter.MediacamenoHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediacamenoHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return MediacamenoHolder(
            layoutInflater.inflate(
                R.layout.recycler_medicamentos,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MediacamenoHolder, position: Int) {
        var item = medicamentoList[position]
        holder.load(item)
    }

    override fun getItemCount(): Int = medicamentoList.size

    inner class MediacamenoHolder(private var view: View) : RecyclerView.ViewHolder(view) {
        private var tvMeddicina = view.findViewById<TextView>(R.id.tvNombreMedicamentoRecycler)
        private var fragMedicamento = view.findViewById<ConstraintLayout>(R.id.frag_medicamentos)

        fun load(medicina: String) {
            tvMeddicina.text = medicina
            fragMedicamento.setOnClickListener {
                Navigation.findNavController(view)
                    .navigate(R.id.acMostrarMedicamentosEditarMedicamentos)
            }
        }
    }

}