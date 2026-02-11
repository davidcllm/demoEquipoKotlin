package david.ceballos.helloworld.scenes.register.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import david.ceballos.helloworld.scenes.base.BaseActivity
import david.ceballos.demo.databinding.ActivityRegisterBinding
import david.ceballos.helloworld.scenes.register.viewModel.RegisterViewModel

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
            this.viewModel.user.password = it.toString()
            this.viewModel.validateForm()
        }
        /*this.binding.tvRegistrate.setOnClickListener { // falta cambiar esto
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }*/
    }

    private fun initComponents() {
        this.binding.btnRegister.isEnabled = false
    }

    private fun setObservers() {
        this.viewModel.isiValidForm.observe(this, Observer { isValid ->
            this.binding.btnRegister.isEnabled = isValid

            //this.binding.etUsername.error = if (isValid) null else "El usuario es requerido"
            //this.binding.etPassword.error = if (isValid) null else "La contraseña es requerida"
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
        this.viewModel.isPasswordValid.observe(this, Observer { isValid ->
            this.binding.etPassword.error = if (isValid) null else "La contraseña es requerida"
        })

    }

}