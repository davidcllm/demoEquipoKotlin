package david.ceballos.helloworld.scenes.register.router

import android.content.Context
import android.content.Intent
import david.ceballos.helloworld.scenes.base.BaseActivity
import david.ceballos.helloworld.scenes.main.view.MainActivity
import david.ceballos.helloworld.scenes.main.viewModel.MainViewModel

class RegisterRouter(val context: Context, val activity: BaseActivity) {
    private val TAG = this::class.java.simpleName

    // Regresar al login
    fun routeToMainView(name: String) {
        // Finaliza la actividad actual para no poder retroceder
        // Si no se coloca el usuatio puede retroceder a la actividad anterior
        this.activity.finish() // Finaliza la actividad actual
    }

}