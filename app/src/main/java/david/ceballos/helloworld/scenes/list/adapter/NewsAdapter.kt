package david.ceballos.helloworld.scenes.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import david.ceballos.demo.databinding.RecylerNewsBinding
import david.ceballos.helloworld.dataClasses.Article

// adaptador que conecta la lista de artículos con el RecyclerView
// recibe una lista de art. y los muestra en pantalla
class NewsAdapter(private val newsList: List<Article>) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    /*
    Esta es la clase "caja" que se usa para almacenar y mantener las
    referencias de los elementos de una sola fila (noticia).
    Se usa con binding para no tener que usar findViewById.
     */
    class NewsViewHolder(val binding: RecylerNewsBinding) : RecyclerView.ViewHolder(binding.root)

    /*
    Esta función o que hace es que crea la fila (la noticia la convierte en un xml (infla))
    y la mete dentro de NewsViewHolder
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding = RecylerNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(binding)
    }

    /*
    Esta función lo que hace es que da la información de cada elemento de fila
    con los nombres que tenga esa noticia en específico (titulo, autor, fecha, etc)
     */
    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val article = newsList[position]
        with(holder.binding) {
            txtTitleHeader.text = article.source?.name ?: "Fuente desconocida"
            // solo toma los primeros 10 caracteres del dateTime (la parte de la fecha, sin la hora)
            txtDate.text = article.publishedAt.take(10)
            txtNewsTitle.text = article.title
            txtSubtitle.text = article.author ?: "Autor no encontrado"
            // descripción corta, o mensaje por defecto si no viene en la respuesta
            txtDescription.text = article.description ?: "Descripción no disponible"

            //Esto es para poner la primera letra del noticiero en el ícono de la noticia
            txtInitial.text = article.source?.name?.take(1) ?: "?"
        }
    }

    //Le dice al RecyclerView cuántos elementos hay en total en la lista de noticias
    override fun getItemCount(): Int = newsList.size
}