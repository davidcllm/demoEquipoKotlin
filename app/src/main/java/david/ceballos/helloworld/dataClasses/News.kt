package david.ceballos.helloworld.dataClasses

import android.app.appsearch.SearchResults
import androidx.appcompat.widget.DialogTitle
import com.google.gson.annotations.SerializedName
// mapea el nombre de una llave en un archivo JSON con una variable en el codigo
// ej: source_name -> sourceName

/*
Estos datos serán cambiados de acuerdo a la información del API de noticias.
Por el momento, esta información es provisional únicamente para visualizar el
recyclerview.
 */

// objeto que recibe la respuesta de la búsqueda
data class News (
    val status: String,
    val totalResults: Int,
    val articles: List<Article>
)


// el artículo
data class Article(
    @SerializedName("source") val source: Source,
    val author: String?,
    val title: String,
    val description: String?,
    val urlval : String,
    val urlToImage: String?,
    @SerializedName ("publishedAt") val publishedAt: String,
    val content: String?

)

// fuente del artículo
data class Source(
    val id: String?,
    val name: String
)