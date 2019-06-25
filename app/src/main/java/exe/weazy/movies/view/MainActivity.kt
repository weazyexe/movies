package exe.weazy.movies.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import exe.weazy.movies.R
import exe.weazy.movies.adapter.MoviesAdapter
import exe.weazy.movies.arch.MainContract
import exe.weazy.movies.di.App
import exe.weazy.movies.entity.Movie
import exe.weazy.movies.presenter.MainPresenter
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity(), MainContract.View {

    @Inject
    lateinit var presenter: MainPresenter

    private lateinit var adapter : MoviesAdapter

    private lateinit var manager : LinearLayoutManager

    private var page = 1

    private lateinit var onItemClickListener : View.OnClickListener


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        App.getComponent().injectsMainActivity(this)

        presenter.attach(this)
        presenter.updateMovieList(page)
    }

    override fun onStart() {
        super.onStart()

        initListeners()
        initRecyclerView()
    }

    override fun showList(movies : ArrayList<Movie>) {
        adapter.setMovies(movies)

        layout_progress_bar.visibility = View.GONE
        layout_error.visibility = View.GONE
        recycler_view_movies.visibility = View.VISIBLE
    }

    override fun showLoading() {
        layout_progress_bar.visibility = View.VISIBLE
        layout_error.visibility = View.GONE
        recycler_view_movies.visibility = View.GONE
    }

    override fun showNotFound() {
        // TODO: implement it
    }

    override fun showError() {

        if (adapter.itemCount == 0) {
            layout_progress_bar.visibility = View.GONE
            layout_error.visibility = View.VISIBLE
            recycler_view_movies.visibility = View.GONE
        } else {
            layout_progress_bar.visibility = View.GONE
            layout_error.visibility = View.GONE
            recycler_view_movies.visibility = View.VISIBLE

            Snackbar.make(layout_main, R.string.error, Snackbar.LENGTH_LONG).show()
        }
    }


    private fun initRecyclerView() {

        adapter = MoviesAdapter(ArrayList(), onItemClickListener)
        manager = LinearLayoutManager(this)

        recycler_view_movies.adapter = adapter
        recycler_view_movies.layoutManager = manager
    }

    private fun initListeners() {
        swipe_refresh_layout_movies.setOnRefreshListener {
            page = 1
            presenter.updateMovieList(page)
            swipe_refresh_layout_movies.isRefreshing = false
        }

        fab_update.setOnClickListener {
            page = 1
            presenter.updateMovieList(page)
        }

        onItemClickListener = View.OnClickListener {
            val position = recycler_view_movies.getChildAdapterPosition(it)
            Snackbar.make(layout_main, presenter.getMovie(position).title, Snackbar.LENGTH_SHORT).show()
        }
    }

}
