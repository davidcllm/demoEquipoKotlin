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
import nombre.apellido.helloworld.Networking.Models.AnahuacAPI
import org.json.JSONObject


// worker de la pantalla de lista de noticias
// hace las llamadas al API, procesa los datos y los entrega al fragment
class ListWorker(var context: Context) {
    private val TAG = this::class.java.simpleName
    private val requestManager = RequestManager(context)
    private val gson = Gson()

    private val categoryKeywords = setOf("tendencias", "deportes", "entretenimiento", "politica")

    private fun resolveKeyword(query: String): String {
        if (query !in categoryKeywords) return query   // búsqueda libre del usuario
        return when (query) {
            "tendencias"      -> "noticias"
            "deportes"        -> "deportes"
            "entretenimiento" -> "entretenimiento"
            "politica"        -> "política"
            else              -> "noticias"
        }
    }

    // obtiene una lista de articulso del API de la categoría especificada
    // onSuccess: se ejecuta con la lista de artículos si la llamada fue exitosa
    // onError: mensaje de error si algo sale mal
    fun getNews(query: String,
                onSuccess: (response: List<Article>) -> Unit,
                onError: (error: String) -> Unit)

    {
        // API KEY DE 'newsapi.ai' desde 'local.properties' a través de BuildConfig
        val apiKey = BuildConfig.API_KEY

        // Si el query coincide con una categoría predefinida, usa su keyword mapeado
        // Si no, usa el query directamente como búsqueda libre del usuario
        val keyword = resolveKeyword(query)

        val body = JSONObject().apply {
            put(APIConstants.BodyKeys.ACTION, APIConstants.Actions.GET_ARTICLES)
            put(APIConstants.BodyKeys.KEYWORD, keyword)
            put(APIConstants.BodyKeys.LANG, APIConstants.DEFAULT_LANGUAGE)
            put(APIConstants.BodyKeys.ARTICLES_PAGE, APIConstants.DEFAULT_PAGE)
            put(APIConstants.BodyKeys.ARTICLES_COUNT, APIConstants.DEFAULT_PAGE_SIZE)
            put(APIConstants.BodyKeys.ARTICLES_SORT_BY, APIConstants.DEFAULT_SORT_BY)
            put(APIConstants.BodyKeys.API_KEY, apiKey)
        }
        val target = AnahuacAPI(
            url = APIConstants.MAIN_SERVER + APIConstants.EndPoints.GET_ARTICLES,
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

                    // los articulos vienen dentro de articles.results
                    val list = newsResponse.articleResponse?.results ?: emptyList()
                    Log.i(TAG, "Noticias encontradas: ${list.size}")

                    if (list.isNotEmpty()) {
                        onSuccess(list)
                    } else {
                        onError("No se encontraron noticias con ese parámetro")
                    }
                }
                catch (e: Exception){
                    Log.e(TAG, "Error parseando datos: ${e.message}")
                    e.printStackTrace()
                    onError("Error procesando noticias")
                }
            }

            override fun onError(error: String) {
                onError(error)
            }
        })

    }

}