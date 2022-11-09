package ni.edu.uca.petscare.rv_adapters

import android.content.Context
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import ni.edu.uca.petscare.R

class MascotasViewHolder(view: View): RecyclerView.ViewHolder(view) {
    /**
     * Aqui estamos definiendo variables de las vistas del fragmento que queremos usar
     */
    val mascotaName = view.findViewById<TextView>(R.id.tvMascotaNombre)
    val fragRecyclerMascotas = view.findViewById<ConstraintLayout>(R.id.frag_mascotas)

    /**
     * se manda la estructura de datos de mascota (una posicion)
     */
    fun load(mascota: String, view: View){
        mascotaName.text = mascota
        fragRecyclerMascotas.setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.acMostrarMascotasVistaMascota)
        }
    }
}