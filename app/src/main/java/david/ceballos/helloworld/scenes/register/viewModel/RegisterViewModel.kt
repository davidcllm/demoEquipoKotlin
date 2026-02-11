package david.ceballos.helloworld.scenes.register.viewModel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import david.ceballos.helloworld.dataClasses.User
import david.ceballos.helloworld.scenes.register.model.RegisterModel
import david.ceballos.helloworld.scenes.register.router.RegisterRouter
import david.ceballos.helloworld.scenes.register.view.RegisterActivity

class RegisterViewModel(val context: Context, val activity: RegisterActivity): ViewModel() {
    private val TAG = this::class.java.simpleName
    private val model = RegisterModel()
    private val router = RegisterRouter(context, activity)

    var user: User
        get() = this.model.user

        set(value) {
            this.model.user = value
        }

    val isiValidForm: LiveData<Boolean>
        get() = this.model.isValidForm

    val isNameValid: LiveData<String?>
        get() = this.model.isNameValid
    val isLastNameValid: LiveData<String?>
        get() = this.model.isLastNameValid
    val isUserNameValid: LiveData<String?>
        get() = this.model.isUserNameValid
    val isPasswordValid: LiveData<Boolean>
        get() = this.model.isPasswordValid

    fun validateForm() {
        Log.i(TAG, "User: ${this.user}")

        // Validaciones para apellido
        val lastName = user.lastName.trim()

        val lastNameErrorMessage = when {
            lastName.isEmpty() -> "El nombre es obligatorio"
            lastName.length < 2 -> "Nombre muy corto"
            lastName.length > 30 -> "Nombre muy largo"
            !lastName.matches(Regex("^[A-Za-z ]+$")) -> "Solo se permiten letras y espacios"
            else -> null
        }
        model.isLastNameValid.value = lastNameErrorMessage

        // Validaciones para nombre
        val name = user.name.trim()

        val nameErrorMessage = when {
            name.isEmpty() -> "El nombre es obligatorio"
            name.length < 2 -> "Nombre muy corto"
            name.length > 30 -> "Nombre muy largo"
            !name.matches(Regex("^[A-Za-z ]+$")) -> "Solo se permiten letras y espacios"
            else -> null
        }
        model.isNameValid.value = nameErrorMessage

        // Validaciones para username
        val username = user.userName.trim()

        val userNameErrorMessage = when {
            username.isEmpty() -> "El usuario es obligatorio"
            username.length < 4 || username.length > 20 -> "Debe tener entre 4 y 20 caracteres"
            !username.matches(Regex("^[A-Za-z][A-Za-z0-9]{3,19}$")) -> "No se permiten espacios ni caracteres especiales"
            else -> null
        }
        model.isUserNameValid.value = userNameErrorMessage

        /*this.model.isUserNameValid.value = this.user.userName.isNotEmpty()
        this.model.isPasswordValid.value = this.user.password.isNotEmpty()
        this.model.isValidForm.value = this.model.isUserNameValid.value!! && this.model.isPasswordValid.value!!*/

        Log.i(TAG, "isValid: ${this.model.isValidForm.value}")

    }

    fun validateRegistration() {
        val name = "${this.user.userName}"
        this.router.routeToMainView(name)
    }
}