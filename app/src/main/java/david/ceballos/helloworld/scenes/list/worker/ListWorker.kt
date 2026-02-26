package david.ceballos.helloworld.scenes.list.worker

import android.content.Context
import android.util.Log
import com.crepalatchi.mx.Networking.Models.Encoding
import com.crepalatchi.mx.Networking.Models.HTTPMethod
import com.crepalatchi.mx.Networking.RequestListener
import com.crepalatchi.mx.Networking.RequestManager
import com.google.gson.Gson
import david.ceballos.helloworld.Networking.APIConstants
import david.ceballos.helloworld.dataClasses.GetPokemon
import david.ceballos.helloworld.dataClasses.Pokemon
import david.ceballos.helloworld.dataClasses.User
import nombre.apellido.helloworld.Networking.Models.AnahuacAPI
import org.json.JSONObject

class ListWorker(var context: Context) {
    private val TAG = this::class.java.simpleName
    private val requestManager = RequestManager(context)
    private val gson = Gson()

    fun exampleGET(onSuccess: (response: List<Pokemon>) -> Unit, onError: (error: String) -> Unit) {
        val target = AnahuacAPI(
            url = APIConstants.MAIN_SERVER + APIConstants.EndPoints.GET_POKEMONS,
            method = HTTPMethod.GET,
            encoding = Encoding.JSON,
            parameters = null
        )
        requestManager.request(target, false, object : RequestListener {
            override fun onResponse(response: String) {
                try {
                    val response = gson.fromJson(response, GetPokemon::class.java)
                    val list = response.results ?: emptyList()
                    Log.i(TAG, response.toString())
                    if (list.isNotEmpty())
                        onSuccess(list)
                    else
                        onError("Lista vacia")
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