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

        // recyclerView de arranque con lista vacía
        setupRecyclerView(emptyList())

        // llamada al API
        this.listWorker.getNews(
            // si funciona, actualiza la lista
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

    private fun setupRecyclerView(initialList: List<Article>) {

        binding.rvNews.layoutManager = LinearLayoutManager(requireContext())
        binding.rvNews.adapter = NewsAdapter(initialList)
    }

    private fun updateNewsList(newArticles: List<Article>){
        val adapter = NewsAdapter(newArticles)
        binding.rvNews.adapter = adapter
    }

}