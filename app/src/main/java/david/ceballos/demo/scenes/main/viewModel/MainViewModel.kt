package david.ceballos.demo.scenes.main.viewModel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import david.ceballos.demo.dataClasses.User
import david.ceballos.demo.scenes.main.model.MainModel
import david.ceballos.demo.scenes.main.router.MainRouter
import david.ceballos.demo.scenes.main.view.MainActivity

class MainViewModel(val context: Context, val activity: MainActivity): ViewModel() {
    private val TAG = MainViewModel::class.java.simpleName
    private val model = MainModel()
    private val router = MainRouter(context, activity)

    /*

     */

     /*

      */

    var user: User
        get() = this.model.user

        set(value) {
            this.model.user = value
        }

    val isiValidForm: LiveData<Boolean>
        get() = this.model.isValidForm

    // TO DO: Agregar observers únicos de usuario y contraseña
    val isUserNameValid: LiveData<Boolean>
        get() = this.model.isUserNameValid
    val isPasswordValid: LiveData<Boolean>
        get() = this.model.isPasswordValid
    fun validateForm() {
        Log.i(TAG, "User: ${this.user}")
        //this.model.isValidForm.value = !(this.user.userName.isEmpty() || this.user.password.isEmpty())

        //Agregamos las cosas que queremos que se observen en el modelo
        this.model.isUserNameValid.value = this.user.userName.isNotEmpty()
        this.model.isPasswordValid.value = this.user.password.isNotEmpty()
        this.model.isValidForm.value = this.model.isUserNameValid.value!! && this.model.isPasswordValid.value!!

        Log.i(TAG, "isValid: ${this.model.isValidForm.value}")

        //TODO: Agregar observers únicos de usuario y contraseña
    }

    /*
        Funcion para iniciar sesion
     */
    fun validateLogin() {
        val name = "${this.user.userName}"
        this.router.routeToHomeView(name)
    }
}