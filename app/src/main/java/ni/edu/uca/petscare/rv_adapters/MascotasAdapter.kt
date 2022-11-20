package ni.edu.uca.petscare.rv_adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.get
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
import ni.edu.uca.petscare.MostrarMascotasFragmentDirections
import ni.edu.uca.petscare.MostrarMascotasFragmentDirections.Companion.acMostrarMascotasVistaMascota
import ni.edu.uca.petscare.R
import ni.edu.uca.petscare.dao.DaoMascota
import ni.edu.uca.petscare.dao.DaoVacuna
import ni.edu.uca.petscare.entidades.Mascota
import ni.edu.uca.petscare.databinding.RecyclerMascotasBinding

class MascotasAdapter(private var daoVacuna: DaoVacuna, var daoMascota: DaoMascota, private val mascotaList: ArrayList<Mascota>, private val currentView: View) :
    RecyclerView.Adapter<MascotasAdapter.MascotasViewHolder>() {
    private lateinit var currentContext: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MascotasViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        currentContext = parent.context
        return MascotasViewHolder(layoutInflater.inflate(R.layout.recycler_mascotas, parent, false))
    }

    override fun onBindViewHolder(holder: MascotasViewHolder, position: Int) {
        val mascota: Mascota = mascotaList[position]
        holder.load(mascota, currentView, daoMascota)
    }

    override fun getItemCount(): Int = mascotaList.size

    inner class MascotasViewHolder(view: View): RecyclerView.ViewHolder(view) {
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
        fun load(mascota: Mascota, view: View, daoMascota: DaoMascota){
            mascotaName.text = mascota.nombre
            mascotaTipo.text = mascota.tipo
            mascotaRaza.text = mascota.raza
            mascotaImage.setImageDrawable(mascota.image.drawable)
            fragRecyclerMascotas.setOnClickListener{
                val action = MostrarMascotasFragmentDirections.acMostrarMascotasVistaMascota(mascota.idMascota, daoMascota, daoVacuna)
                Navigation.findNavController(view).navigate(action)
            }
        }
    }
}

