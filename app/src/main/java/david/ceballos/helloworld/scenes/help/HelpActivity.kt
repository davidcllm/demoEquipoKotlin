package david.ceballos.helloworld.scenes.help

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import david.ceballos.demo.databinding.ActivityHelpBinding
import david.ceballos.helloworld.scenes.base.BaseActivity


class HelpActivity : BaseActivity() {
    private lateinit var binding: ActivityHelpBinding
    private var player: ExoPlayer? = null
    private val TAG = this::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.enableEdgeToEdge()
        this.configureActivity()
    }

    override fun onStart() {
        super.onStart()
        this.initializePlayer()
    }

    override fun onStop() {
        // Libera recursos del reproductor
        super.onStop()
        this.player?.release()
        this.player = null
    }

    private fun configureActivity() {
        this.binding = ActivityHelpBinding.inflate(layoutInflater)
        this.setContentView(this.binding.root)

        this.binding.icBack.setOnClickListener { this.finish() } //Finaliza la actividad
    }

    private fun initializePlayer() {

        //Crear instancia del reproductor. Paso 1
        this.player = ExoPlayer.Builder(this).build().also { exoPlayer ->

            // Se vincula al playerview del xml. Paso 2
            this.binding.playerView.player = exoPlayer

            // Crea un mediaItem y carga multimedia (Puede ser URL o archivo local ram/assests). Paso 3
            val uri = "https://storage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"

            val mediaItem = MediaItem.Builder()
                .setUri(uri)
                .build()

            exoPlayer.setMediaItem(mediaItem)

            // Comienza la reproducción. Paso 4
            exoPlayer.prepare()
            exoPlayer.play()
        }
    }
}