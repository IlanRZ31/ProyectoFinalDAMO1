package ni.edu.uca.petscare.rv_adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import ni.edu.uca.petscare.MostrarMedicamentosFragment
import ni.edu.uca.petscare.MostrarMedicamentosFragmentDirections
import ni.edu.uca.petscare.R
import ni.edu.uca.petscare.dao.DaoMedicamento
import ni.edu.uca.petscare.entidades.Medicamento

class MedicamentoAdapter(var daoMedic: DaoMedicamento, val medicList: ArrayList<Medicamento>, private val currentView: View) :
    RecyclerView.Adapter<MedicamentoAdapter.MediacamenoHolder>() {
    private lateinit var currentContext: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediacamenoHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        currentContext = parent.context
        return MediacamenoHolder(
            layoutInflater.inflate(
                R.layout.recycler_medicamentos,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MediacamenoHolder, position: Int) {
        var medic: Medicamento = medicList[position]
        holder.load(medic, currentView, daoMedic)
    }

    override fun getItemCount(): Int = medicList.size

    inner class MediacamenoHolder(view: View) : RecyclerView.ViewHolder(view) {
        private var tvMeddicina = view.findViewById<TextView>(R.id.tvNombreMedicamentoRecycler)
        private var tvHora = view.findViewById<TextView>(R.id.tvHoraDosisPendienteRV)
        private var fragMedicamento = view.findViewById<ConstraintLayout>(R.id.frag_medicamentos)

        fun load(medic: Medicamento, view: View, daoMedic: DaoMedicamento) {
            tvMeddicina.text = medic.nombreMedicamento
            tvHora.text = "Hora: ${medic.horaInicial.hours.toString()}:${medic.horaInicial.minutes.toString()}"
            fragMedicamento.setOnClickListener {
                val action = MostrarMedicamentosFragmentDirections.acMostrarMedicamentosEditarMedicamentos(medic.idMedicamento, daoMedic)
                Navigation.findNavController(view).navigate(action)
            }
        }
    }

}