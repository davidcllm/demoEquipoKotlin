package david.ceballos.demo.scenes.home.viewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import david.ceballos.demo.scenes.base.BaseActivity
import david.ceballos.demo.scenes.home.model.HomeModel

class HomeViewModel(val context: Context, val activity: BaseActivity): ViewModel() {
    private val TAG = this::class.java.simpleName
    private val model = HomeModel()

    // Funcionan como las propiedades estaticas en java
    // Propiedades que no necesitan ser instanciadas, sino que pertenencen a la misma clase
    companion object {
        val PARAM_NAME = "PARAM_NAME"

        fun hello() = print("Hello World")
    }

    /*
        Funcion que se ejecuta cuando se inicializa el viewModel
     */
    // GetSerializable para pasar parametro de una pantalla anterior a la nueva
    // Lo intenta convertir en String, si no, se hace nulo
    init {
        this.model.name.value = (this.activity.intent.getSerializableExtra(PARAM_NAME) as? String) ?: ""
    }

    /*
        Variable que permite obtner el nombre y cambiar su valor desde el model
     */
    val name: LiveData<String>
        get() = this.name
}