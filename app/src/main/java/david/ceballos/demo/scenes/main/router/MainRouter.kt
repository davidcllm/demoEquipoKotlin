package david.ceballos.demo.scenes.main.router

import android.content.Context
import android.content.Intent
import david.ceballos.demo.scenes.base.BaseActivity
import david.ceballos.demo.scenes.home.viewModel.HomeViewModel
import david.ceballos.demo.scenes.main.view.MainActivity

class MainRouter(val context: Context, val activity: BaseActivity) {
    private val TAG = this::class.java.simpleName

    /*
        El intent permite la comunicacion entre modulos. Mesajeria entre activities (bavegamos por pantallas)
     */
    fun routeToHomeView(name: String) {
        //Levanta otra actividad. Mercado libre
        val intent = Intent(this.context, MainActivity::class.java)

        // PutExtra envia parametros
        intent.putExtra(HomeViewModel.PARAM_NAME, name)

        //starActivity inicia el intent
        this.context.startActivity(intent)

        // Finaliza la actividad actual para no poder retroceder
        // Si no se coloca el usuatio puede retroceder a la actividad anterior
        this.activity.finish() // Finaliza la actividad actual
    }
}