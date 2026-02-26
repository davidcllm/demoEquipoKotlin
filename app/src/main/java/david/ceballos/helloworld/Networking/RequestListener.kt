package com.crepalatchi.mx.Networking

/**
 * Interface para las peticiones
 * @property onResponse: Función que se ejecuta cuando se recibe una respuesta
 * @property onError: Función que se ejecuta cuando se recibe un error
 */
interface RequestListener {
    fun onResponse(response: String)
    fun onError(error: String)
}
