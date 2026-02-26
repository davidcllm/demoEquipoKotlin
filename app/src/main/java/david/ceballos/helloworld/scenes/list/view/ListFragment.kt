package david.ceballos.helloworld.scenes.list.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import david.ceballos.demo.databinding.FragmentListBinding
import david.ceballos.helloworld.scenes.list.worker.ListWorker

class ListFragment : Fragment() {
    private lateinit var binding: FragmentListBinding
    private lateinit var listWorker: ListWorker

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.binding = FragmentListBinding.inflate(inflater, container, false)

        this.listWorker.exampleGET(
            onSuccess = {

            },
            onError = {

            }
        )
        return this.binding.root
    }

}