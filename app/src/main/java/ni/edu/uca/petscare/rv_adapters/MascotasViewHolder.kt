package ni.edu.uca.petscare.rv_adapters

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ni.edu.uca.petscare.R

class MascotasViewHolder(view: View): RecyclerView.ViewHolder(view) {
    val mascotaName = view.findViewById<TextView>(R.id.tvMascotaNombre)
    fun load(mascota: String){
        mascotaName.text = mascota
    }
}