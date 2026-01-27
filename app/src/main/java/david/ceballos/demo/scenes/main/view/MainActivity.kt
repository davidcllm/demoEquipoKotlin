package david.ceballos.demo.scenes.main.view

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import david.ceballos.demo.scenes.base.BaseActivity
import david.ceballos.demo.databinding.ActivityMainBinding
import david.ceballos.demo.scenes.main.viewModel.MainViewModel

class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private val TAG = MainActivity::class.java.simpleName

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
        this.binding = ActivityMainBinding.inflate(layoutInflater)
        this.setContentView(this.binding.root)
        this.viewModel = MainViewModel(this, this)
    }

    private fun configureListeners() {
        this.binding.btnLogin.setOnClickListener {
            this.viewModel.validateLogin()
        }
        this.binding.etUsername.addTextChangedListener {
            this.viewModel.user.userName = it.toString()
            this.viewModel.validateForm()
        }
        this.binding.etPassword.addTextChangedListener {
            this.viewModel.user.password = it.toString()
            this.viewModel.validateForm()
        }
    }

    private fun initComponents() {
        this.binding.btnLogin.isEnabled = false
    }

    private fun setObservers() {
        this.viewModel.isiValidForm.observe(this, Observer { isValid ->
            this.binding.btnLogin.isEnabled = isValid

            //this.binding.etUsername.error = if (isValid) null else "El usuario es requerido"
            //this.binding.etPassword.error = if (isValid) null else "La contraseña es requerida"
        })

        // TODO: Agregar observers únicos de usuario y contrasña
        //Observer para el usuario
        this.viewModel.isUserNameValid.observe(this, Observer { isValid ->
            this.binding.etUsername.error = if (isValid) null else "El usuario es requerido"
        })

        //Observer para la contraseña
        this.viewModel.isPasswordValid.observe(this, Observer { isValid ->
            this.binding.etPassword.error = if (isValid) null else "La contraseña es requerida"
        })

    }

    /*override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvHello.text = "Hello World!"
        binding.tvHello.setOnClickListener {
            if (binding.tvHello.text == "Hello World!") {
                binding.tvHello.text = "Nuevo Texto"
            }
            else {
                binding.tvHello.text = "Hello World!"
            }

        }
    }*/
}