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
import androidx.savedstate.serialization.saved
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
        return this.binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.listWorker = ListWorker(requireContext())
        binding.rvNews.layoutManager = LinearLayoutManager(requireContext())
        setupRecyclerView(emptyList())

        // Listeners de búsqueda
        binding.icSearch.setOnClickListener {
            ejecutarBusqueda()
        }

        // Si el usuario pulsa "Enter" en el teclado:
        binding.etSearch.setOnEditorActionListener { _, _, _ ->
            ejecutarBusqueda()
            true
        }

        // 2. Carga inicial (tendencias o categoría previa)
        val category = arguments?.getString("category") ?: "tendencias"
        llamarApi(category)
    }

    private fun ejecutarBusqueda() {
        val query = binding.etSearch.text.toString().trim()
        if (query.isNotEmpty()) {
            llamarApi(query)
        } else {
            Toast.makeText(requireContext(), "Escribe algo para buscar", Toast.LENGTH_SHORT).show()
        }
    }

    private fun llamarApi(query: String) {
        this.listWorker.getNews(
            query = query,
            onSuccess = { articles ->
                Log.i("PRUEBA", "Primer título: ${articles.firstOrNull()?.title}")
                activity?.runOnUiThread {
                    updateNewsList(articles)
                }
            },
            onError = { errorMessage ->
                activity?.runOnUiThread {
                    Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
                    Log.e("ListFragment", errorMessage)
                }
            }
        )
    }



    // configura el RecyclerView con una lista vertical, lo inicializa con la lista que se pasó
    private fun setupRecyclerView(initialList: List<Article>) {

        binding.rvNews.layoutManager = LinearLayoutManager(requireContext())
        binding.rvNews.adapter = NewsAdapter(initialList)
    }
    // reemplaza el adaptador del RecyclerView con los nuevos artículos recibidos del API
    private fun updateNewsList(newArticles: List<Article>){
        Log.d("ListFragment", "Llegaron ${newArticles.size} artículos al fragmento")
        binding.rvNews.post {
            binding.rvNews.adapter = NewsAdapter(newArticles)
            binding.rvNews.adapter?.notifyDataSetChanged()
            Log.d("ListFragment", "Adaptador actualizado con ${newArticles.size} elementos")
        }
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