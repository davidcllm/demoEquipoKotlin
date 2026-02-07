package david.ceballos.demo.scenes.profile.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import david.ceballos.demo.databinding.FragmentProfileBinding
import david.ceballos.demo.scenes.help.HelpActivity

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding

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

        return this.binding.root
    }

}