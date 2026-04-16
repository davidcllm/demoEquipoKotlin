package david.ceballos.helloworld.scenes.profile.view

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
import androidx.fragment.app.activityViewModels
import david.ceballos.demo.databinding.FragmentProfileBinding
import david.ceballos.helloworld.scenes.base.BaseActivity
import david.ceballos.helloworld.scenes.help.HelpActivity
import david.ceballos.helloworld.scenes.profile.router.ProfileRouter
import david.ceballos.helloworld.scenes.profile.viewModel.ProfileViewModel
import java.io.File

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    lateinit var pictureUri: Uri
    private val viewModel: ProfileViewModel by activityViewModels()
    private lateinit var router: ProfileRouter

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

        configureListeners()
        return this.binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        router = ProfileRouter(requireContext(), BaseActivity())

        // Sincroniza el switch con el valor persistido
        viewModel.isFaceIdEnabled.observe(viewLifecycleOwner) { isEnabled ->
            // Evita disparar el listener al setear el valor programáticamente
            binding.switchConfigFaceID.setOnCheckedChangeListener(null)
            binding.switchConfigFaceID.isChecked = isEnabled
            configureListeners() // re-adjunta el listener después de setear
        }
    }

    private fun configureListeners() {
        //this.binding.cvHelpCenter.setOnClickListener { this.viewModel.routeToHelpView() }

        this.binding.switchConfigFaceID.setOnCheckedChangeListener { _, isChecked ->
            this.viewModel.setFaceIdEnabled(isChecked)
        }

        this.binding.btnLogout.setOnClickListener { this.router.routeToMainView("") }
    }
}
