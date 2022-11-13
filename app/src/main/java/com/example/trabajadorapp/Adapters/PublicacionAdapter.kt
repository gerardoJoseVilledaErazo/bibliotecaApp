package com.example.trabajadorapp.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.trabajadorapp.Models.Interfaces.IOnClickListener
import com.example.trabajadorapp.Models.LibroEntity
import com.example.trabajadorapp.Models.Publicacion
import com.example.trabajadorapp.Models.RevistaEntity
import com.example.trabajadorapp.R
import com.example.trabajadorapp.databinding.ItemPublicacionListBinding

class PublicacionAdapter(
    private var lstPublicaciones: MutableList<Publicacion>, private val listener: IOnClickListener
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
                // LibroEntity
                // A continuación se realiza un parceo o casteo
                val libro: LibroEntity = item as LibroEntity
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
                // RevistaEntity
                val revista: RevistaEntity = item as RevistaEntity
                setListenerRevista(revista, position)
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

    fun setLibros(libros: MutableList<LibroEntity>) {
        this.lstPublicaciones = libros.toMutableList()
        notifyDataSetChanged()
    }

    fun setRevistas(revistas: MutableList<RevistaEntity>) {
        this.lstPublicaciones = revistas.toMutableList()
        notifyDataSetChanged()
    }

    fun updateLibro(libroEntity: LibroEntity) {
        val index = lstPublicaciones.indexOf(libroEntity)
        if (index != -1) {
            lstPublicaciones[index] = libroEntity
            notifyItemChanged(index)
        }
    }

    fun deleteLibro(libroEntity: LibroEntity) {
        val index = lstPublicaciones.indexOf(libroEntity)
        if (index != -1) {
            lstPublicaciones.removeAt(index)
            notifyItemRemoved(index)
        }
    }

    fun deleteRevista(revistaEntity: RevistaEntity) {
        val index = lstPublicaciones.indexOf(revistaEntity)
        if (index != -1) {
            lstPublicaciones.removeAt(index)
            notifyItemRemoved(index)
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemPublicacionListBinding.bind(view)

        fun setListener(libroEntity: LibroEntity, position: Int) {
            with(binding.root) {
                setOnClickListener {
                    listener.onClickListener(libroEntity, position)
                }
                setOnLongClickListener {
                    listener.onDeleteLibro(libroEntity, position)
                    true
                }
            }
        }

        fun setListenerRevista(revistaEntity: RevistaEntity, position: Int) {
            with(binding.root) {
                setOnLongClickListener {
                    listener.onDeleteRevista(revistaEntity, position)
                    true
                }
            }
        }
    }

//    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
//        val binding = ItemPublicacionListBinding.bind(view)
//
//        fun setListener(libro: LibroEntity, position: Int) {
//            binding.root.setOnClickListener {
//                listener.onClickListener(libro, position)
//            }
//        }
////        fun bind(publicacion: Publicacion, context: Context, position: Int) {
////            // Es necesario identificar el tipo de publicacion.
////            if (publicacion.getTipoPublicacion() == 1) {
////                // Libro
////                // A continuación se realiza un parceo o casteo
////                val libro: Libro = publicacion as Libro
////                // Configurando contenido del cardview en base al objeto casteado
////                binding.txvcodigo.text = libro.getCode().toString()
////                binding.txvTitulo.text = libro.getTitle()
////                binding.txvAnio.text = libro.getYearPub().toString()
////                binding.txvTipoPublicacion.text =
////                    context.resources.getString(R.string.libro_tag)
////                // Se evalua el estado del libro
////                if (libro.Prestado()) {
////                    binding.txvEstado.text =
////                        context.resources.getString(R.string.estado_tag)
////                } else {
////                    binding.txvEstado.text =
////                        context.resources.getString(R.string.estado_disp_tag)
////                }
////                // Cambiar de estado al libro
////                itemView.setOnClickListener {
////                    // Validar si el libro ya fue prestado
////                    if (libro.Prestado()) {
////                        // Si el libro esta prestado, ejecutar devolucion
////                        libro.devolver()
////                        updateState(position)
////                    } else {
////                        // El libro se encuentra disponible para ser prestado
////                        libro.prestar()
////                        updateState(position)
////                    }
////                }
////            } else {
////                // Revista
////                val revista: Revista = publicacion as Revista
////                // Configurando contenido del cardview en base al objeto casteado
////                binding.txvcodigo.text = revista.getCode().toString()
////                binding.txvTitulo.text = revista.getTitle()
////                binding.txvAnio.text = revista.getYearPub().toString()
////                binding.txvTipoPublicacion.text =
////                    context.resources.getString(R.string.revista_tag)
////                // Se carga el numero de revista
////                binding.txvEstado.text = revista.getNumRev().toString()
////            }
////        }
//    }
}