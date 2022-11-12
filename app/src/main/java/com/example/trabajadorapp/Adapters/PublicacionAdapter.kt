package com.example.trabajadorapp.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.trabajadorapp.Models.Interfaces.IOnClickListener
import com.example.trabajadorapp.Models.Libro
import com.example.trabajadorapp.Models.Publicacion
import com.example.trabajadorapp.Models.Revista
import com.example.trabajadorapp.R
import com.example.trabajadorapp.databinding.ItemPublicacionListBinding

class PublicacionAdapter(
    private val lstPublicaciones: MutableList<Publicacion>, private val listener: IOnClickListener
) : RecyclerView.Adapter<PublicacionAdapter.ViewHolder>() {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            ViewHolder {
        context = parent.context
        val layout = LayoutInflater.from(parent.context)
        return ViewHolder(
            layout.inflate(
                R.layout.item_publicacion_list,
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = lstPublicaciones[position]
        // Uso de funcion de alcance para agregar acciones al objeto en un mismo bloque
        with(holder) {
            // Es necesario identificar el tipo de publicacion.
            if (item.getTipoPublicacion() == 1) {
                // Libro
                // A continuación se realiza un parceo o casteo
                val libro: Libro = item as Libro
                setListener(libro, position)
                // Configurando contenido del cardview en base al objeto casteado
                binding.txvcodigo.text = libro.getCode().toString()
                binding.txvTitulo.text = libro.getTitle()
                binding.txvAnio.text = libro.getYearPub().toString()
                binding.txvTipoPublicacion.text =
                    context.resources.getString(R.string.libro_tag)
                // Se evalua el estado del libro
                if (libro.Prestado()) {
                    binding.txvEstado.text =
                        context.resources.getString(R.string.estado_tag)
                } else {
                    binding.txvEstado.text =
                        context.resources.getString(R.string.estado_disp_tag)
                }
//                // Cambiar de estado al libro
//                itemView.setOnClickListener {
//                    // Validar si el libro ya fue prestado
//                    if (libro.Prestado()) {
//                        // Si el libro esta prestado, ejecutar devolucion
//                        libro.devolver()
//                        updateState(position)
//                    } else {
//                        // El libro se encuentra disponible para ser prestado
//                        libro.prestar()
//                        updateState(position)
//                    }
//                }
            } else {
                // Revista
                val revista: Revista = item as Revista
                // Configurando contenido del cardview en base al objeto casteado
                binding.txvcodigo.text = revista.getCode().toString()
                binding.txvTitulo.text = revista.getTitle()
                binding.txvAnio.text = revista.getYearPub().toString()
                binding.txvTipoPublicacion.text =
                    context.resources.getString(R.string.revista_tag)
                // Se carga el numero de revista
                binding.txvEstado.text = revista.getNumRev().toString()
            }

        }
//        holder.bind(item, context, position)
    }

    // Cambiar de estado al libro
    fun updateState(position: Int) {
        notifyItemChanged(position)
    }

    override fun getItemCount(): Int = lstPublicaciones.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemPublicacionListBinding.bind(view)

        fun setListener(libro: Libro, position: Int) {
            binding.root.setOnClickListener {
                listener.onClickListener(libro, position)
            }
        }
//        fun bind(publicacion: Publicacion, context: Context, position: Int) {
//            // Es necesario identificar el tipo de publicacion.
//            if (publicacion.getTipoPublicacion() == 1) {
//                // Libro
//                // A continuación se realiza un parceo o casteo
//                val libro: Libro = publicacion as Libro
//                // Configurando contenido del cardview en base al objeto casteado
//                binding.txvcodigo.text = libro.getCode().toString()
//                binding.txvTitulo.text = libro.getTitle()
//                binding.txvAnio.text = libro.getYearPub().toString()
//                binding.txvTipoPublicacion.text =
//                    context.resources.getString(R.string.libro_tag)
//                // Se evalua el estado del libro
//                if (libro.Prestado()) {
//                    binding.txvEstado.text =
//                        context.resources.getString(R.string.estado_tag)
//                } else {
//                    binding.txvEstado.text =
//                        context.resources.getString(R.string.estado_disp_tag)
//                }
//                // Cambiar de estado al libro
//                itemView.setOnClickListener {
//                    // Validar si el libro ya fue prestado
//                    if (libro.Prestado()) {
//                        // Si el libro esta prestado, ejecutar devolucion
//                        libro.devolver()
//                        updateState(position)
//                    } else {
//                        // El libro se encuentra disponible para ser prestado
//                        libro.prestar()
//                        updateState(position)
//                    }
//                }
//            } else {
//                // Revista
//                val revista: Revista = publicacion as Revista
//                // Configurando contenido del cardview en base al objeto casteado
//                binding.txvcodigo.text = revista.getCode().toString()
//                binding.txvTitulo.text = revista.getTitle()
//                binding.txvAnio.text = revista.getYearPub().toString()
//                binding.txvTipoPublicacion.text =
//                    context.resources.getString(R.string.revista_tag)
//                // Se carga el numero de revista
//                binding.txvEstado.text = revista.getNumRev().toString()
//            }
//        }
    }
}