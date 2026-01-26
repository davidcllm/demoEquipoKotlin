package david.ceballos.demo.scenes.base

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

open class BaseActivity : AppCompatActivity() {
    private val TAG: String = this::class.java.simpleName

    /*
        El callback onCreate() se manda a llamar la primera vez que se crea la actividad.
        Es decir, se llama cada que se abre la app por primera vez o después de que haya sido destruida con onDestroy().
        Con este callback, la actividad entra en estado "Created".
    */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate")
    }

    /*
        El callback onStart() es invocado cuando el usuario puede ver la actividad.
        Es decir, la UI se prepara para entrar en primer plano.
        Esta puede aparecer después de un onCreate() o de un onStop() y ser usada.
        Con este callback, la actividad entra en estado "Started".
    */
    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart")
    }

    /*
        El callback onResume() es invocado cuando la app entra en contacto con el usuario.
        Es decir, cuando está lista para interactuar con el usuario.
        Esta puede aparecer después de un onStart() o de un onPause().
        Con este callback, la actividad entra en estado "Reanudada".
    */
    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume")
    }

    /*
        El callback onPause() es invocado cuando el usuario abandona la atividad.
        Es decir, que la actividad ya no está en primer plano, pero puede verse si se entra en el modo multiventana.
        Esta puede aparecer después de que la actividad esté corriendo y pasa a 2do plano.
        Con este callback, la actividad entra en estado "Paused".
    */
    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause")
    }

    /*
        El callback onStop() es invocado cuando otra acttividad cubre toda la pantalla o cuando la actividad termina de ejecutarse y está por finalizar.
        Esta puede aparecer después de un onPause().
        Con este callback, la actividad entra en estado "Stopped".
    */
    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop")
    }

    /*
        El callback onRestart() es invocado cuando el usuario regresa a la actividad, después de haberse salido.
        Esta puede aparecer después de un onStop().
    */
    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, "onRestart")
    }

    /*
        El callback onDestroy() es invocado cuando el usuario descarta la app o por configuración de cambios como el ingreso al modo multiventana.
        Esta puede aparecer después de un onStop().
        Después de este callback, la actividad termina.
    */
    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy")
    }

}