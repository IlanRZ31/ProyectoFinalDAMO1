package ni.edu.uca.petscare.rv_adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.get
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
import ni.edu.uca.petscare.R
import ni.edu.uca.petscare.entidades.Mascota
import ni.edu.uca.petscare.databinding.RecyclerMascotasBinding

class MascotasAdapter(private val mascotaList: ArrayList<Mascota>, private val currentView: View) :
    RecyclerView.Adapter<MascotasViewHolder>() {
    private lateinit var currentContext: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MascotasViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        currentContext = parent.context
        return MascotasViewHolder(layoutInflater.inflate(R.layout.recycler_mascotas, parent, false))
    }

    override fun onBindViewHolder(holder: MascotasViewHolder, position: Int) {
        val mascota: Mascota = mascotaList[position]
        holder.load(mascota, currentView)
    }

    override fun getItemCount(): Int = mascotaList.size
}

