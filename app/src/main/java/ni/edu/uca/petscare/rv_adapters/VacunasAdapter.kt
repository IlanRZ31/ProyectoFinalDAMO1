package ni.edu.uca.petscare.rv_adapters

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import ni.edu.uca.petscare.*
import ni.edu.uca.petscare.dao.DaoMascota
import ni.edu.uca.petscare.dao.DaoVacuna
import ni.edu.uca.petscare.entidades.Vacuna

// TODO: Cambiar list<String> a la estructura de datos que se vaya a usar
class VacunasAdapter(
    var daoVacuna: DaoVacuna,
    val vacunasList: ArrayList<Vacuna>,
    private val currentView: View
) :
    RecyclerView.Adapter<VacunasAdapter.VacunasViewHolder>() {
    private lateinit var currentContext: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VacunasViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        currentContext = parent.context
        return VacunasViewHolder(layoutInflater.inflate(R.layout.recycler_vacunas, parent, false))
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: VacunasViewHolder, position: Int) {
        val vacuna: Vacuna = vacunasList[position]
        holder.load(vacuna, currentView, daoVacuna)
    }

    override fun getItemCount(): Int = vacunasList.size


    inner class VacunasViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private var nombreVacuna = view.findViewById<TextView>(R.id.tvNombreVacunaRecycler)
        private var fechaProgramada = view.findViewById<TextView>(R.id.tvFechaProgramadaRV)
        private var estadoVacuna = view.findViewById<TextView>(R.id.tvEstadoVacunaRV)
        private var fragRecyclerVacuna = view.findViewById<ConstraintLayout>(R.id.frag_vacunas)

        @SuppressLint("SetTextI18n")
        @RequiresApi(Build.VERSION_CODES.O)
        fun load(vacuna: Vacuna, view: View, daoVacuna: DaoVacuna) {
            nombreVacuna.text = "Vacuna: ${vacuna.nombreVacuna}"

            var fecha = "${vacuna.fechaVacunacion.dayOfMonth}/${vacuna.fechaVacunacion.month.value}/${vacuna.fechaVacunacion.year}"
            Log.wtf(
                "VACUNAS_ADAPTER",
                ">>>>>>>>>>$fecha")
            fechaProgramada.text = "Fecha Programada para: $fecha"

            estadoVacuna.text = "Estado: ${vacuna.estado}"

            if(vacuna.estado.equals("Atrasado")){
                fragRecyclerVacuna.setBackgroundResource(R.drawable.rv_bg_vacuna_retradasa)
            }else if(vacuna.estado.equals("Aplicado")){
                fragRecyclerVacuna.setBackgroundResource(R.drawable.rv_bg_vacuna_aplicada)
            }else if(vacuna.estado.equals("Programado")){
                fragRecyclerVacuna.setBackgroundResource(R.drawable.rv_bg_vacuna_programada)
            }

            fragRecyclerVacuna.setOnClickListener {
                val action = MostrarVacunasFragmentDirections.acMostrarVacunaEditarVacuna(
                    vacuna.idVacuna,
                    daoVacuna
                )
                Navigation.findNavController(view).navigate(action)
            }
        }

    }


}