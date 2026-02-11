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
    val isPasswordValid: LiveData<String?>
        get() = this.model.isPasswordValid
    val isConfirmPasswordValid: LiveData<String?>
        get() = this.model.isConfirmPasswordValid

    fun validateForm() {
        Log.i(TAG, "User: ${this.user}")

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

        // Validaciones para apellido
        val lastName = user.lastName.trim()

        val lastNameErrorMessage = when {
            lastName.isEmpty() -> "El apellido es obligatorio"
            lastName.length < 2 -> "Apellido muy corto"
            lastName.length > 30 -> "Apellido muy largo"
            !lastName.matches(Regex("^[A-Za-z ]+$")) -> "Solo se permiten letras y espacios"
            else -> null
        }
        model.isLastNameValid.value = lastNameErrorMessage

        // Validaciones para username
        val username = user.userName.trim()

        val userNameErrorMessage = when {
            username.isEmpty() -> "El usuario es obligatorio"
            username.length < 4 || username.length > 20 -> "Debe tener entre 4 y 20 caracteres"
            !username.matches(Regex("^[A-Za-z][A-Za-z0-9]{3,19}$")) -> "No se permiten espacios ni caracteres especiales"
            else -> null
        }
        model.isUserNameValid.value = userNameErrorMessage

        // Validaciones para contrasena
        val password = user.password.trim()

        val passwordErrorMessage = when {
            password.isEmpty() -> "La contraseña es obligatoria"
            password.length < 8 -> "Debe tener al menos 8 caracteres"
            !password.matches((Regex("^\\S+$"))) -> "No se permiten espacios"
            !password.matches(Regex(".*[a-z].*")) -> "Debe contener por lo menos una minúscula"
            !password.matches(Regex(".*[A-Z].*")) -> "Debe contener por lo menos una mayúscula"
            else -> null
        }
        model.isPasswordValid.value = passwordErrorMessage

        //Validaciones para confirmar contrasena
        val confirmPassword = user.confirmPassword.trim()

        val confirmPasswordErrorMessage = when {
            confirmPassword.isEmpty() -> "Debes confirmar la contraseña"
            !password.equals(confirmPassword) -> "Las contraseñas no coinciden"
            else -> null
        }
        model.isConfirmPasswordValid.value = confirmPasswordErrorMessage


        if (this.model.isNameValid.value == null &&
            this.model.isUserNameValid.value == null &&
            this.model.isLastNameValid.value == null &&
            this.model.isPasswordValid.value == null &&
            this.model.isConfirmPasswordValid.value == null) {

            this.model.isValidForm.value = true
        }
        else {
            this.model.isValidForm.value = false
        }

        Log.i(TAG, "isValid: ${this.model.isValidForm.value}")

    }

    fun validateRegistration() {
        val name = "${this.user.userName}"
        this.router.routeToMainView(name)
    }
}