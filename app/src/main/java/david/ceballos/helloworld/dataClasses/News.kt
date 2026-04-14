package david.ceballos.helloworld.dataClasses

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
    val articles: ArticleWrapper
    // Contiene un objeto "articles" con la lista de resultados

)

// lista de artículos
data class ArticleWrapper(
    val results: List<Article>, // lista de artículos devueltos
    val totalResults: Int       // total de artículos que coinciden con la búsqueda
)



// el artículo individual
// @SerializedName mapea el nombre de una llave en el JSON con una variable en el código
// "image" del JSON se guarda en la variable "urlToImage"
data class Article(
    val source: Source?,
    val author: String?,
    val title: String,
    val description: String?,
    val url: String?,
    @SerializedName ("image") val urltoImage: String?,  // URL de la imagen (en el JSON viene como "image")
    @SerializedName ("dateTime") val publishedAt: String,  // lo mismo que arriba "dateTime" a "publishedAt"
    val content: String?
)

// fuente del artículo
data class Source(
    val id: String?,
    @SerializedName ("title") val name: String,

    )