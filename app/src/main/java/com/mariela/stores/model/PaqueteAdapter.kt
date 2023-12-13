import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mariela.stores.R
import com.mariela.stores.model.Paquete

// PaqueteAdapter.kt
class PaqueteAdapter : RecyclerView.Adapter<PaqueteAdapter.PaqueteViewHolder>() {

    private var paquetes: List<Paquete> = emptyList()

    fun setPaquetes(paquetes: List<Paquete>) {
        this.paquetes = paquetes
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaqueteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_paquete, parent, false)
        return PaqueteViewHolder(view)
    }

    override fun onBindViewHolder(holder: PaqueteViewHolder, position: Int) {
        val paquete = paquetes[position]
        holder.bind(paquete)
    }

    override fun getItemCount(): Int {
        return paquetes.size
    }

    class PaqueteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(paquete: Paquete) {
            itemView.findViewById<TextView>(R.id.textViewNombre).text = paquete.nombrep
            itemView.findViewById<TextView>(R.id.textViewDescripcion).text = paquete.descripcion

            // Cargar imagen con Glide
            Glide.with(itemView.context)
                .load(paquete.imagen)
                .into(itemView.findViewById<ImageView>(R.id.imageViewImagen))
        }
    }
}
