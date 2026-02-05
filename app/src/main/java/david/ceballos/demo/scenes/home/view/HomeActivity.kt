package david.ceballos.demo.scenes.home.view

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.fragment.app.Fragment
import david.ceballos.demo.R
import david.ceballos.demo.databinding.ActivityHomeBinding
import david.ceballos.demo.scenes.base.BaseActivity
import david.ceballos.demo.scenes.home.viewModel.HomeViewModel
import david.ceballos.demo.scenes.list.ListFragment

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
        //this.setObserver()
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
                R.id.profile_menu -> this.replaceFragment(ListFragment())
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentTransaction = this.supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fl_content, fragment).commit()
    }

    /*private fun setObserver() {
        this.viewModel.name.observe(this) { userName ->
            Log.i(TAG, "userName: $userName")
            this.binding.tvName.text = userName
        }
    }*/
}