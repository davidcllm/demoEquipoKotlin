package david.ceballos.demo.scenes.splash.viewModel

import android.content.Context
import android.os.Handler
import android.util.Log
import androidx.lifecycle.ViewModel
import david.ceballos.demo.scenes.splash.router.SplashRouter
import david.ceballos.demo.scenes.splash.view.SplashActivity

class SplashViewModel (context: Context, activity: SplashActivity): ViewModel() {
    // Variables privadas
    private val TAG = SplashViewModel::class.java.simpleName
    private var router = SplashRouter(context, activity)

    /*
    Funcion que se ejecuta cuando se inicializa el ViewModel
     */
    init {
        countdown()
    }

    private fun countdown() {
        Log.i(TAG, "Inicia cuenta atrás")
        Handler().postDelayed({
            Log.i(TAG, "Navegamos al login")
            this.router.routeToMainView()
        }, 2500)
    }
}