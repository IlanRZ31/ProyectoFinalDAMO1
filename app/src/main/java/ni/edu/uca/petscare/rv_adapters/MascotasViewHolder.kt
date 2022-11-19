package ni.edu.uca.petscare.rv_adapters

import android.content.Context
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
import ni.edu.uca.petscare.R
import ni.edu.uca.petscare.entidades.Mascota

class MascotasViewHolder(view: View): RecyclerView.ViewHolder(view) {
    /**
     * Aqui estamos definiendo variables de las vistas del fragmento que queremos usar
     */
    val mascotaName = view.findViewById<TextView>(R.id.tvMascotaNombre)
    val mascotaTipo = view.findViewById<TextView>(R.id.tvMascotaTipo)
    val mascotaRaza = view.findViewById<TextView>(R.id.tvMascotaRaza)
    var mascotaImage = view.findViewById<ShapeableImageView>(R.id.ivPhoto)
    val fragRecyclerMascotas = view.findViewById<ConstraintLayout>(R.id.frag_mascotas)
    /**
     * se manda la estructura de datos de mascota (una posicion)
     */
    fun load(mascota: Mascota, view: View){
        mascotaName.text = mascota.nombre
        mascotaTipo.text = mascota.tipo
        mascotaRaza.text = mascota.raza
        mascotaImage = mascota.image
        fragRecyclerMascotas.setOnClickListener{
            Navigation.findNavController(view).navigate(R.id.acMostrarMascotasVistaMascota)
        }
    }
}