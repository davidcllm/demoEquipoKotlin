package david.ceballos.helloworld.scenes.splash.view

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import david.ceballos.demo.databinding.ActivitySplashBinding
import david.ceballos.helloworld.scenes.base.BaseActivity
import david.ceballos.helloworld.scenes.splash.viewModel.SplashViewModel

class SplashActivity : BaseActivity() {
    private lateinit var binding: ActivitySplashBinding
    private lateinit var viewModel: SplashViewModel
    private val TAG = SplashActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.enableEdgeToEdge()
        this.configureActivity()
    }

    /*
    Configuramos nuestro activity
     */
    private fun configureActivity() {
        this.initActivityView()
        this.supportActionBar?.hide()
    }

    private fun initActivityView() {
        this.binding = ActivitySplashBinding.inflate(layoutInflater)
        this.setContentView(this.binding.root)
        this.viewModel = SplashViewModel(this, this)
    }

}