package david.ceballos.helloworld.scenes.list.worker

import android.content.Context
import android.util.Log
import david.ceballos.demo.BuildConfig

import com.crepalatchi.mx.Networking.Models.Encoding
import com.crepalatchi.mx.Networking.Models.HTTPMethod
import com.crepalatchi.mx.Networking.RequestListener
import com.crepalatchi.mx.Networking.RequestManager
import com.google.gson.Gson
import david.ceballos.helloworld.Networking.APIConstants


import david.ceballos.helloworld.dataClasses.News
import david.ceballos.helloworld.dataClasses.Article
import david.ceballos.helloworld.dataClasses.GetPokemon
import david.ceballos.helloworld.dataClasses.User
import nombre.apellido.helloworld.Networking.Models.AnahuacAPI
import org.json.JSONObject


// worker de la pantalla de lista de noticias
// hace las llamadas al API, procesa los datos y los entrega al fragment
class ListWorker(var context: Context) {
    private val TAG = this::class.java.simpleName
    private val requestManager = RequestManager(context)
    private val gson = Gson()

    // convierte el nombre de la categoría en un keyword para la búsqueda en el API
    private fun categoryToKeyword(category: String): String = when (category){
        "tendencias"      -> "noticias"
        "deportes"        -> "deportes"
        "entretenimiento" -> "entretenimiento"
        "politica"        -> "política"
        else              -> "noticias"
    }

    // obtiene una lista de articulso del API de la categoría especificada
    // onSuccess: se ejecuta con la lista de artículos si la llamada fue exitosa
    // onError: mensaje de error si algo sale mal
    fun getNews(category: String = "tendencias",
                onSuccess: (response: List<Article>) -> Unit,
                onError: (error: String) -> Unit)

    {
        // API KEY DE 'newsapi.ai' desde 'local.properties' a través de BuildConfig
        val apiKey = BuildConfig.API_KEY
        //Log.d(TAG, "API KEY: $apiKey")

        // Cuerpo de la petición POST en formato JSON
        val body = JSONObject().apply {
            put("action", "getArticles")
            put("keyword", categoryToKeyword(category))  //  keyword según la categoría seleccionada
            put("lang", "spa")     // filtra articulos en español
            put("articlesPage", 1)  // 1 página de resultados
            put("articlesCount", 20)    // 20 artículos por página
            put("articlesSortBy", "date")   // ordena por fecha
            put("apiKey", apiKey)
        }

        // POST a la URL del API de newsapi.ai
        // newsapi.ai usa POST con JSON
        val target = AnahuacAPI(
            url = "https://eventregistry.org/api/v1/article/getArticles",
            method = HTTPMethod.POST,
            encoding = Encoding.JSON,
            parameters = body
        )
        requestManager.request(target, false, object : RequestListener {
            override fun onResponse(response: String) {
                Log.d(TAG, "Respuesta cruda del servidor: $response")
                try {
                    // convierte el JSON de respuesta al modelo de datos News
                    val newsResponse = gson.fromJson(response, News::class.java)

                    // los articuslo vienen dentro de articles.results
                    val list = newsResponse.articles?.results ?: emptyList()
                    Log.i(TAG, "Noticias encontradas: ${list.size}")

                    if (list.isNotEmpty()) {
                        onSuccess(list)
                    } else {
                        onError("No se encontraron noticias con ese parámetro")
                    }
                }
                catch (e: Exception){
                    Log.e(TAG, "Error parseando datos: ${e.message}")
                    onError("Error procesando noticias")
                }
            }

            override fun onError(error: String) {
                onError(error)
            }
        })

    }

}