package david.ceballos.demo.scenes.profile.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import david.ceballos.demo.databinding.FragmentProfileBinding
import david.ceballos.demo.scenes.help.HelpActivity
import java.io.File

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    lateinit var pictureUri: Uri

    private val takePicture = registerForActivityResult(ActivityResultContracts.TakePicture()) { iImageSaved ->
        if (iImageSaved)
            this.binding.ivProfile.setImageURI(this.pictureUri)
    }

    private val galleryLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        this.binding.ivProfile.setImageURI(uri)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.binding = FragmentProfileBinding.inflate(inflater, container, false)

        this.binding.cvHelp.setOnClickListener {
            this.startActivity(
                Intent(activity, HelpActivity::class.java)
            )
        }

        this.binding.btnTakePhoto.setOnClickListener {
            val prefix = "photo-"
            val postfix = System.currentTimeMillis().toString()
            val directory = this.requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            val photoFile = File.createTempFile(prefix, postfix, directory)

            this.pictureUri = FileProvider.getUriForFile(
                this.requireContext(),
                "david.ceballos.helloworld.fileprovider",
                photoFile
            )

            this.takePicture.launch(this.pictureUri)
        }

        this.binding.btnChoosePhoto.setOnClickListener {
            this.galleryLauncher.launch("image/*")
        }

        return this.binding.root
    }

    private fun configureListeners() {
        //this.binding.cvHelpCenter.setOnClickListener { this.viewModel.routeToHelpView() }


    }


}