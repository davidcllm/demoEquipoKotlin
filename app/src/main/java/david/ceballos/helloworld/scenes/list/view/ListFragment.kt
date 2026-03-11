package david.ceballos.helloworld.scenes.list.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import david.ceballos.demo.databinding.FragmentListBinding
import david.ceballos.helloworld.dataClasses.News
import david.ceballos.helloworld.scenes.list.adapter.NewsAdapter
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

        this.listWorker = ListWorker(requireContext())

        setupRecyclerView()

        this.listWorker.exampleGET(
            onSuccess = {

            },
            onError = {

            }
        )
        return this.binding.root
    }

    private fun setupRecyclerView() {
        /*
        Estos datos son únicamente de prueba, ya de acuerdo con la información
        del API de noticias es que tendremos que modificar esta carga de datos para que cargue
        las noticias del API y también tendremos que cambiar el dataclass de News de
        acuerdo a la información que devuelva el API.
         */
        val newsData = listOf(
            News("El Noticiero","11 Mar 2026","Pasó algo en el Ángel de la Independencia","Subtítulo","Wow, que noticia tan interesante"),
            News("Reforma", "11 Mar 2026", "Debes de leer estos 10 libros antes de morir", "Subtítulo", "Una noticia muy interesante ...")
        )

        //Configuración del RecyclerView
        val adapter = NewsAdapter(newsData)
        binding.rvNews.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(requireContext())
        binding.rvNews.adapter = adapter
    }

}