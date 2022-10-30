package ni.edu.uca.petscare.rv_adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.get
import androidx.recyclerview.widget.RecyclerView
import ni.edu.uca.petscare.R
// TODO: Cambiar list<String> a la estructura de datos que se vaya a usar
class MascotasAdapter(private val mascotaList: List<String>, private val currentView: View) :
    RecyclerView.Adapter<MascotasViewHolder>() {
    private lateinit var currentContext: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MascotasViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        currentContext = parent.context
        return MascotasViewHolder(layoutInflater.inflate(R.layout.recycler_mascotas, parent, false))
    }

    override fun onBindViewHolder(holder: MascotasViewHolder, position: Int) {
        val item = mascotaList[position]
        holder.load(item, currentView)
    }

    override fun getItemCount(): Int = mascotaList.size

}