package david.ceballos.helloworld.scenes.list.worker

import android.content.Context
import android.util.Log
import com.crepalatchi.mx.Networking.Models.Encoding
import com.crepalatchi.mx.Networking.Models.HTTPMethod
import com.crepalatchi.mx.Networking.RequestListener
import com.crepalatchi.mx.Networking.RequestManager
import com.google.gson.Gson
import david.ceballos.helloworld.Networking.APIConstants

import david.ceballos.helloworld.dataClasses.News
import david.ceballos.helloworld.dataClasses.Article
import david.ceballos.helloworld.dataClasses.GetPokemon
import david.ceballos.helloworld.dataClasses.Pokemon
import david.ceballos.helloworld.dataClasses.User
import nombre.apellido.helloworld.Networking.Models.AnahuacAPI
import org.json.JSONObject

class ListWorker(var context: Context) {
    private val TAG = this::class.java.simpleName
    private val requestManager = RequestManager(context)
    private val gson = Gson()

    fun getNews(onSuccess: (response: List<Article>) -> Unit, onError: (error: String) -> Unit) {

        // API KEY DE newsapi.org
        val apiKey = "718e135712194c6e990e984f5c1976ab"
        //              718e135712194c6e990e984f5c1976ab
        val query = "Deportes" // parametro de busqueda provisional
        // url completa del endpoint 'everything'
        val urlString = "https://newsapi.org/v2/everything?q=$query&from=2026-02-19&sortBy=publishedAt&apiKey=$apiKey"
        // https://newsapi.org/v2/everything?q=Deportes&from=2026-02-19&sortBy=publishedAt&apiKey=718e135712194c6e990e984f5c1976ab
        val target = AnahuacAPI(
            url = urlString,
            method = HTTPMethod.GET,
            encoding = Encoding.JSON,
            parameters = null
        )
        requestManager.request(target, false, object : RequestListener {
            override fun onResponse(response: String) {
                Log.d(TAG, "Respuesta cruda del servidor: $response")
                try {
                    // convertir 'json' a objeto 'News'
                    val newsResponse = gson.fromJson(response, News::class.java)

                    // NewsAPI siempre devuelve un status http ('ok' o 'error')
                    if (newsResponse.status == "ok") {
                        val list = newsResponse.articles ?: emptyList()
                        Log.i(TAG, "Noticias encontradas: ${list.size}")

                        if (list.isNotEmpty()) {
                            onSuccess(list)
                        } else {
                            onError("No se encontraron noticias con ese parámetro")
                        }
                    } else {
                        onError("El API devolvió un estado incorrecto")
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

    fun examplePOST(request: User, onSuccess: (response: String) -> Unit, onError: (error: String) -> Unit) {
        val target = AnahuacAPI(
            url = APIConstants.MAIN_SERVER + APIConstants.EndPoints.GET_POKEMONS,
            method = HTTPMethod.POST,
            encoding = Encoding.JSON,
            parameters = JSONObject(gson.toJson(request))
        )
        requestManager.request(target, true, object : RequestListener {
            override fun onResponse(response: String) {
                try {
                    val response = gson.fromJson(response, GetPokemon::class.java)
                    // do something
                }
                catch (e: Exception) {
                    onError(e.toString())
                }
            }

            override fun onError(error: String) {
                onError(error)
            }
        })
    }
}