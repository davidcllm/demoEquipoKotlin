package david.ceballos.helloworld.scenes.register.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import david.ceballos.helloworld.scenes.base.BaseActivity
import david.ceballos.demo.databinding.ActivityRegisterBinding
import david.ceballos.helloworld.scenes.main.view.MainActivity
import david.ceballos.helloworld.scenes.register.viewModel.RegisterViewModel
import david.ceballos.helloworld.sharedPreference.SharedPreferenceConstants
import david.ceballos.helloworld.sharedPreference.SharedPreferenceManager

class RegisterActivity : BaseActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var viewModel: RegisterViewModel
    private val TAG = RegisterActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.enableEdgeToEdge()
        this.configureActivity()
    }

    private fun configureActivity() {
        this.initActivityView()
        this.configureListeners()
        this.initComponents()
        this.setObservers()
    }

    private fun initActivityView() {
        this.binding = ActivityRegisterBinding.inflate(layoutInflater)
        this.setContentView(this.binding.root)
        this.viewModel = RegisterViewModel(this, this)
    }

    private fun configureListeners() {
        this.binding.btnRegister.setOnClickListener {
            this.viewModel.validateRegistration()

            // Mensaje registro exitoso
            /*Snackbar.make(this.binding.main, "Registro exitoso", Snackbar.LENGTH_INDEFINITE)
                .setAction("Iniciar sesión") {
                    this.finish()
                }
                .show()*/

            // otro tipo de mensaje
            Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show()
        }

        this.binding.etUsername.addTextChangedListener {
            this.viewModel.user.userName = it.toString()
            this.viewModel.validateForm()
        }
        this.binding.etName.addTextChangedListener {
            this.viewModel.user.name = it.toString()
            this.viewModel.validateForm()
        }
        this.binding.etLastName.addTextChangedListener {
            this.viewModel.user.lastName = it.toString()
            this.viewModel.validateForm()
        }
        this.binding.etPassword.addTextChangedListener {
            this.viewModel.user.password = it.toString()
            this.viewModel.validateForm()
        }
        this.binding.etPasswordConfirm.addTextChangedListener {
            this.viewModel.user.confirmPassword = it.toString()
            this.viewModel.validateForm()
        }
        this.binding.icBackRegister.setOnClickListener { // este es el boton de la flecha para ir hacia atras
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initComponents() {
        this.binding.btnRegister.isEnabled = false
    }

    private fun setObservers() {
        this.viewModel.isiValidForm.observe(this, Observer { isValid ->
            this.binding.btnRegister.isEnabled = isValid
        })

        // TODO: Agregar observers únicos de usuario y contrasña
        // Observer para el nombre
        this.viewModel.isNameValid.observe(this) { error ->
            this.binding.etName.error = error
        }

        //Obbserver para el apellido
        this.viewModel.isLastNameValid.observe(this) { error ->
            this.binding.etLastName.error = error
        }

        //Observer para el usuario
        this.viewModel.isUserNameValid.observe(this) { error ->
            this.binding.etUsername.error = error
        }

        //Observer para la contraseña
        this.viewModel.isPasswordValid.observe(this) { error ->
            this.binding.etPassword.error = error
        }

        //Observer para la confirmacion de contrasena
        this.viewModel.isConfirmPasswordValid.observe(this) { error ->
            this.binding.etPasswordConfirm.error = error
        }

    }

}