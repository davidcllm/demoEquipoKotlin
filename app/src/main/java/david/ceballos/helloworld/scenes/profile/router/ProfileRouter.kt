package david.ceballos.helloworld.scenes.profile.router

import android.content.Context
import android.content.Intent
import david.ceballos.helloworld.scenes.base.BaseActivity
import david.ceballos.helloworld.scenes.home.view.HomeActivity
import david.ceballos.helloworld.scenes.home.viewModel.HomeViewModel
import david.ceballos.helloworld.scenes.main.view.MainActivity
import david.ceballos.helloworld.scenes.main.viewModel.MainViewModel

class ProfileRouter(val context: Context, val activity: BaseActivity) {
    fun routeToMainView(name: String) {
        //Levanta otra actividad. Mercado libre
        val intent = Intent(this.context, MainActivity::class.java)

        // PutExtra envia parametros
        intent.putExtra(MainViewModel.PARAM_NAME, name)

        //starActivity inicia el intent
        this.context.startActivity(intent)

        // Finaliza la actividad actual para no poder retroceder
        // Si no se coloca el usuatio puede retroceder a la actividad anterior
        this.activity.finish() // Finaliza la actividad actual
    }
}