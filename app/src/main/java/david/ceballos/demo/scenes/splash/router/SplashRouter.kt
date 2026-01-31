package david.ceballos.demo.scenes.splash.router

import android.content.Context
import android.content.Intent
import david.ceballos.demo.scenes.main.view.MainActivity
import david.ceballos.demo.scenes.splash.view.SplashActivity

class SplashRouter (val context: Context, val activity: SplashActivity) {
    private val TAG = SplashRouter::class.java.simpleName

    /*
        Navega al login de la aplicacion
        El intent permite la comunicacion entre modulos
     */
    fun routeToMainView() {
        //Levanta otra actividad
        this.context.startActivity(Intent(this.context, MainActivity::class.java))

        // Finaliza la actividad actual para no poder retroceder
        // Si no se coloca el usuatio puede retroceder a la actividad anterior
        this.activity.finish() // Esta linea previene que el usuario pueda regresar al splash
    }

}