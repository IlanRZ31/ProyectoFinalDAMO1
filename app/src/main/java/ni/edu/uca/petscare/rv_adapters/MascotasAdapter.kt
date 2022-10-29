package ni.edu.uca.petscare.rv_adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ni.edu.uca.petscare.R

class MascotasAdapter(private val mascotaList: List<String>): RecyclerView.Adapter<MascotasViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MascotasViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return MascotasViewHolder(layoutInflater.inflate(R.layout.recycler_mascotas, parent, false))
    }

    override fun onBindViewHolder(holder: MascotasViewHolder, position: Int) {
        val item = mascotaList[position]
        holder.load(item)
    }

    override fun getItemCount(): Int = mascotaList.size

}