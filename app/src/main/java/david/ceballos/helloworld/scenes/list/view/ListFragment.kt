package david.ceballos.helloworld.scenes.list.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import david.ceballos.demo.databinding.FragmentListBinding

import david.ceballos.helloworld.scenes.list.adapter.NewsAdapter
import david.ceballos.helloworld.scenes.list.worker.ListWorker
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import david.ceballos.helloworld.dataClasses.Article



// muestra la lista de noticias de una categoría específica
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

        // lee la categoría que se pasó como argumento al crear el fragmento
        // por defecto (si no se pasa ninguno), será 'tendencias'
        val category = arguments?.getString("category") ?: "tendencias"

        // recyclerView de arranque con lista vacía mientras espera la respuesta del API
        setupRecyclerView(emptyList())

        // llamada al API para obtener las noticias de la categoría correspondiente
        this.listWorker.getNews(
            // si funciona, actualiza la lista con los artículos recibidos
            onSuccess = { articles -> activity?.runOnUiThread {
                updateNewsList(articles)
                }
            },
            onError = { errorMessage -> activity?.runOnUiThread {
                // si falla, mostrar mensajito de error y mandamos al logcat
                Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
                Log.e("ListFragment", errorMessage)
                }
            }
        )
        return this.binding.root
    }

    // configura el RecyclerView con una lista vertical, lo inicializa con la lista que se pasó
    private fun setupRecyclerView(initialList: List<Article>) {

        binding.rvNews.layoutManager = LinearLayoutManager(requireContext())
        binding.rvNews.adapter = NewsAdapter(initialList)
    }
    // reemplaza el adaptador del RecyclerView con los nuevos artículos recibidos del API
    private fun updateNewsList(newArticles: List<Article>){
        binding.rvNews.adapter = NewsAdapter(newArticles)
    }

    /*
    Permite crear una instancia del fragmento con una categoría específica
     se usa desde HomeActivity para pasar la categoría como argumento
    */
    // TODO: implementar en el buscador de noticias, de momento no se usa
    companion object{
        fun newInstance(category: String): ListFragment {
            val fragment = ListFragment()
            val args = Bundle()
            args.putString("category", category)

            fragment.arguments = args
            return fragment
        }
    }

}