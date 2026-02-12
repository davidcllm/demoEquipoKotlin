package david.ceballos.helloworld.scenes.main.viewModel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import david.ceballos.helloworld.dataClasses.User
import david.ceballos.helloworld.scenes.main.model.MainModel
import david.ceballos.helloworld.scenes.main.router.MainRouter
import david.ceballos.helloworld.scenes.main.view.MainActivity
import david.ceballos.helloworld.sharedPreference.SharedPreferenceConstants
import david.ceballos.helloworld.sharedPreference.SharedPreferenceManager

class MainViewModel(val context: Context, val activity: MainActivity): ViewModel() {
    private val TAG = MainViewModel::class.java.simpleName
    private val model = MainModel()
    private val router = MainRouter(context, activity)
    private val sharedPreferenceManager = SharedPreferenceManager(context)

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

    companion object {
        val PARAM_NAME = "PARAM_NAME"

        fun hello() = print("Hello World")
    }

    /*
        Funcion para iniciar sesion
     */
    fun validateLogin() {
        val name = "${this.user.userName}"
        val sharedKey = this.sharedPreferenceManager.getBoolean(SharedPreferenceConstants.IS_REGISTERED_KEY)
        val sharedUserName = this.sharedPreferenceManager.getString(SharedPreferenceConstants.USER_KEY)
        val sharedPassword = this.sharedPreferenceManager.getString(SharedPreferenceConstants.PASSWORD_KEY)


        if ( sharedKey &&
            sharedPassword == this.user.password &&
            sharedUserName == this.user.userName) {

            this.model.isValidForm.value = true
            this.router.routeToHomeView(name)

        }
        else {
            Log.i(TAG, "Error")
        }
    }
}