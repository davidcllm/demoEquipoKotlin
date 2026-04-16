package david.ceballos.helloworld.scenes.home.view

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.semantics.text
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

    // variables de las tabs para evitar que se descarten al cambiar entre cada una
    private var listFragment: ListFragment? = null
    private var profileFragment: ProfileFragment? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.enableEdgeToEdge()
        this.configureActivity()
    }

    private fun initActivityView() {
        this.binding = ActivityHomeBinding.inflate(layoutInflater) //inicializar binding del xml
        this.setContentView(this.binding.root) //envia la vista del binding
        this.viewModel = HomeViewModel(this, this) // Inicializa el Home view model
    }

    private fun initActivity() {

    }

    private fun configureActivity(){
        this.initActivityView()
        this.configurerListeners()

        // crea los fragmentos una sola vez y los agrega al contenedor
        listFragment = ListFragment.newInstance("tendencias")
        profileFragment = ProfileFragment()

        supportFragmentManager.beginTransaction()
            .add(R.id.fl_content, listFragment!!, "list")
            .add(R.id.fl_content, profileFragment!!, "profile")
            .hide(profileFragment!!)  // oculta perfil al inicio
            .commit()
        this.setObserver()
    }

    private fun configurerListeners() {


        this.binding.bnvHome.setOnItemSelectedListener { item ->
            when (item.itemId) {
                // reutiliza el fragmento si ya existe en lugar de crear uno nuevo
                R.id.list_menu -> showFragment(listFragment!!, profileFragment!!)
                R.id.profile_menu -> showFragment(profileFragment!!, listFragment!!)
            }
            true
        }
    }
    // muestra un fragmento y oculta el otro sin destruirlo
    private fun showFragment(show: Fragment, hide: Fragment) {
        supportFragmentManager.beginTransaction()
            .show(show)
            .hide(hide)
            .commit()
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