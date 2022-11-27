package com.example.trabajadorapp

import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.trabajadorapp.Adapters.PublicacionAdapter
import com.example.trabajadorapp.Models.Interfaces.IOnClickListener
import com.example.trabajadorapp.Models.LibroEntity
import com.example.trabajadorapp.Models.RevistaEntity
import com.example.trabajadorapp.databinding.ActivityMostrarListaBinding
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread


class MostrarListaActivity : AppCompatActivity(), IOnClickListener {

    // Variable para configurar viewBinding
    private lateinit var binding: ActivityMostrarListaBinding

        // Variables necesarias para configurar el recyclerview
        private lateinit var recyclerView: RecyclerView
    private lateinit var publicacionAdapter: PublicacionAdapter
    private val llmanager = LinearLayoutManager(this)
    private lateinit var linearLayoutManager: LinearLayoutManager
    private var tipoPublicacion: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Configuracion de viewBinding
        binding = ActivityMostrarListaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        // Habilitar action bar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        // Titulo para la actividad
        title = "Mostrar Lista"

        tipoPublicacion = intent.extras!!.getInt("tipoPublicacion")

        binding.btnAdd.setOnClickListener {
            // Abrir pantalla seleccionar publicacion
            startActivity(Intent(this, SeleccionarPublicacionActivity::class.java))
        }

        // Validar si la lista esta vacia
        /*if (publicacionRepository.get().size == 0) {
            AlertDialog.Builder(this)

                .setTitle(this.resources.getString(R.string.titulo_lista_vacia))

                .setMessage(this.resources.getString(R.string.msg_lista_vacia))
                .setPositiveButton(android.R.string.ok,
                    DialogInterface.OnClickListener
                    { dialogInterface, i ->
                        finish()
                    }).show()
        } else {
            // Configurar SwipeRefreshLayout
            configSwipe()
            // Configurar RecyclerView
            configRecyclerView()
        }*/
        // Configurar SwipeRefreshLayout
        configSwipe()
        // Configurar RecyclerView
        configRecyclerView()
    }

    private fun configSwipe() {
        binding.refreshLayout.setColorSchemeResources(R.color.teal_700, R.color.purple_200)
        binding.refreshLayout.setProgressBackgroundColorSchemeColor(
            ContextCompat.getColor(
                this,
                R.color.black
            )
        )
        binding.refreshLayout.setOnRefreshListener {
            Log.i("Gerardo", "FUNIONA")
            Handler(Looper.getMainLooper()).postDelayed({
                binding.refreshLayout.isRefreshing = false
            }, 2000)
            binding.refreshLayout.setRefreshing(false)
            //your code on swipe refresh
            //we are checking networking connectivity
            val connection = isNetworkAvailable()
            if (connection) {
                // Configurar RecyclerView
                configRecyclerView()
            }
        }
    }

    fun isNetworkAvailable(): Boolean {
        val connectivityManager =
            this.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return connectivityManager.activeNetworkInfo != null
    }

    private fun getLibros() {
        doAsync {
            val libros = BibliotecaApplication.database.libroDao().getAll()
            uiThread {
                publicacionAdapter.setLibros(libros)
            }
        }
    }

    private fun getRevistas() {
        doAsync {
            val revistas = BibliotecaApplication.database.revistaDao().getAll()
            uiThread {
                publicacionAdapter.setRevistas(revistas)
            }
        }
    }

    // Método que configura el recyclerview
    private fun configRecyclerView() {
        publicacionAdapter = PublicacionAdapter(
            lstPublicaciones = mutableListOf()/*publicacionRepository.get()*/,
            this
        )

        if (tipoPublicacion == 1) {
            // Libros
            getLibros()
        } else {
            // Revistas
            getRevistas()
        }

        binding.rcPublicaciones.setHasFixedSize(true)
        binding.rcPublicaciones.layoutManager = llmanager
        binding.rcPublicaciones.adapter = publicacionAdapter

//        recyclerView = binding.rcPublicaciones
//        linearLayoutManager = LinearLayoutManager(this)
//        recyclerView.apply {
//            recyclerView.setHasFixedSize(true)
//            recyclerView.layoutManager = linearLayoutManager
//            recyclerView.adapter = publicacionAdapter
//        }
    }

    // Método que configura el action bar
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                // Finaliza la actividad
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun notifyItemChange(position: Int) {
        publicacionAdapter.notifyItemChanged(position)
    }

//    override fun onClickListener(libro: LibroEntity, position: Int) {
//        if (libro.Prestado()) {
//            // Si el libro esta prestado, ejecutar devolucion
//            libro.devolver()
//        } else {
//            // El libro se encuentra disponible para ser prestado
//            libro.prestar()
//        }
//        notifyItemChange(position)
//    }

    override fun onClickListener(libro: LibroEntity, position: Int) {
        if (libro.Prestado()) {
            // Si el libroEntity esta prestado, ejecutar devolucion
            doAsync {
                libro.devolver()
                BibliotecaApplication.database.libroDao().updateLibro(libro)
                uiThread {
                    publicacionAdapter.updateLibro(libro)
                }
            }
        } else {
            // El libroEntity se encuentra disponible para ser prestado
            doAsync {
                libro.prestar()
                BibliotecaApplication.database.libroDao().updateLibro(libro)
                uiThread {
                    publicacionAdapter.updateLibro(libro)
                }
            }
        }
    }

    override fun onDeleteLibro(libroEntity: LibroEntity, position: Int) {
        AlertDialog.Builder(this)
            .setTitle(this.resources.getString(R.string.titulo_eliminar))
            .setMessage(this.resources.getString(R.string.msg_eliminar))
            .setPositiveButton(android.R.string.ok) { _, _ ->
                doAsync {
                    BibliotecaApplication.database.libroDao().deleteLibro(libroEntity)
                    uiThread {
                        publicacionAdapter.deleteLibro(libroEntity)
                    }
                }
            }.show()
    }

    override fun onDeleteRevista(revistaEntity: RevistaEntity, position: Int) {
        AlertDialog.Builder(this)
            .setTitle(this.resources.getString(R.string.titulo_eliminar))
            .setMessage(this.resources.getString(R.string.msg_eliminar))
            .setPositiveButton(android.R.string.ok) { _, _ ->
                doAsync {
                    BibliotecaApplication.database.revistaDao().deleteRevista(revistaEntity)
                    uiThread {
                        publicacionAdapter.deleteRevista(revistaEntity)
                    }
                }
            }.show()
    }
}