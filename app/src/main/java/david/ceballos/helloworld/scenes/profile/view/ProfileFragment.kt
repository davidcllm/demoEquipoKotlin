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
import david.ceballos.demo.R
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
        {
            binding.ivProfile.setImageURI(pictureUri)
            viewModel.saveProfileImage(pictureUri)  // guardar
        }
    }

    private val galleryLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            binding.ivProfile.setImageURI(it)
            viewModel.saveProfileImage(it)          // guardar
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.binding = FragmentProfileBinding.inflate(inflater, container, false)

        binding.btnTakePhoto.setOnClickListener {
            val prefix = "photo-"
            val postfix = System.currentTimeMillis().toString()
            val directory = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            val photoFile = File.createTempFile(prefix, postfix, directory)

            pictureUri = FileProvider.getUriForFile(
                requireContext(),
                "david.ceballos.helloworld.fileprovider",
                photoFile
            )

            takePicture.launch(pictureUri)
        }

        binding.btnChoosePhoto.setOnClickListener {
            galleryLauncher.launch("image/*")
        }

        configureListeners()
        return this.binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        router = ProfileRouter(requireContext(), requireActivity() as BaseActivity)

        // Carga la foto guardada si existe
        viewModel.profileImageUri.observe(viewLifecycleOwner) { uri ->
            if (uri != null) {
                try {
                    binding.ivProfile.setImageURI(uri)
                } catch (e: SecurityException) {
                    // La URI expiró — limpiar la preferencia para no volver a intentarlo
                    viewModel.clearProfileImage()
                    binding.ivProfile.setImageResource(R.drawable.ic_profile)
                }
            }


        }

        // Sincroniza el switch con el valor persistido
        viewModel.isFaceIdEnabled.observe(viewLifecycleOwner) { isEnabled ->
            // Evita disparar el listener al setear el valor programáticamente
            binding.switchConfigFaceID.setOnCheckedChangeListener(null)
            binding.switchConfigFaceID.isChecked = isEnabled
            configureListeners() // re-adjunta el listener después de setear
        }
    }


    private fun configureListeners() {

        this.binding.switchConfigFaceID.setOnCheckedChangeListener { _, isChecked ->
            this.viewModel.setFaceIdEnabled(isChecked)
        }

        this.binding.btnLogout.setOnClickListener { this.router.routeToMainView("") }
    }
}
