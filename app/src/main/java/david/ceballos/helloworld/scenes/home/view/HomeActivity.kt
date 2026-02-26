package david.ceballos.helloworld.scenes.home.view

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.fragment.app.Fragment
import david.ceballos.demo.R
import david.ceballos.demo.databinding.ActivityHomeBinding
import david.ceballos.helloworld.scenes.base.BaseActivity
import david.ceballos.helloworld.scenes.home.viewModel.HomeViewModel
import david.ceballos.helloworld.scenes.list.view.ListFragment
import david.ceballos.helloworld.scenes.profile.view.ProfileFragment

class HomeActivity : BaseActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var viewModel: HomeViewModel
    private val TAG = this::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.enableEdgeToEdge()
        this.configureActivity()
    }

    private fun configureActivity() {
        this.initActivityView()
        this.configurerListeners()
        this.replaceFragment(ListFragment())
        //this.replaceFragment()
        this.setObserver()
    }

    private fun initActivityView() {
        this.binding = ActivityHomeBinding.inflate(layoutInflater) //inicializar binding del xml
        this.setContentView(this.binding.root) //envia la vista del binding
        this.viewModel = HomeViewModel(this, this) // Inicializa el Home view model
    }

    private fun initActivity() {

    }

    private fun configurerListeners() {
        this.binding.bnvHome.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.list_menu -> this.replaceFragment(ListFragment())
                R.id.profile_menu -> this.replaceFragment(ProfileFragment())
                // ELIMINADO config_menu
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentTransaction = this.supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fl_content, fragment).commit()
    }

    private fun setObserver() {
        this.viewModel.name.observe(this) { userName ->
            //Log.i(TAG, "userName: $userName")
            //this.binding.tvName.text = "Hola, $userName"


            // Esto no sé por qué se pone abajo en lugar de arriba
            val toast = Toast.makeText(this, "Bienvenido, $userName!", Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.TOP, 0, 0)
            toast.show()
        }
    }
}