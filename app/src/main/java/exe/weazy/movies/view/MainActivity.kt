package exe.weazy.movies.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.TextView
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

    private lateinit var onItemClickListener : View.OnClickListener



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        App.getComponent().injectsMainActivity(this)

        presenter.attach(this)
        presenter.updateMovieList()
    }

    override fun onStart() {
        super.onStart()

        initListeners()
        initRecyclerView()
    }

    override fun updateList(movies: ArrayList<Movie>) {
        adapter.setMovies(movies)
    }

    override fun showList() {
        layout_circle_progress_bar.visibility = View.GONE
        layout_horizontal_progress_bar.visibility = View.GONE
        layout_error.visibility = View.GONE
        recycler_view_movies.visibility = View.VISIBLE
        layout_not_found.visibility = View.GONE
    }

    override fun showCircleLoading() {
        layout_circle_progress_bar.visibility = View.VISIBLE
        layout_horizontal_progress_bar.visibility = View.GONE
        layout_error.visibility = View.GONE
        recycler_view_movies.visibility = View.GONE
        layout_not_found.visibility = View.GONE
    }

    override fun showHorizontalLoading() {
        recycler_view_movies.visibility = View.VISIBLE
        layout_horizontal_progress_bar.visibility = View.VISIBLE
        layout_circle_progress_bar.visibility = View.GONE
        layout_error.visibility = View.GONE
        layout_not_found.visibility = View.GONE
    }

    override fun showNotFound() {
        text_not_found.text = getString(R.string.not_found, edit_text_search.text.toString())

        recycler_view_movies.visibility = View.GONE
        layout_horizontal_progress_bar.visibility = View.GONE
        layout_circle_progress_bar.visibility = View.GONE
        layout_error.visibility = View.GONE
        layout_not_found.visibility = View.VISIBLE
    }

    override fun showError() {
        if (adapter.itemCount == 0) {
            layout_circle_progress_bar.visibility = View.GONE
            layout_horizontal_progress_bar.visibility = View.GONE
            layout_error.visibility = View.VISIBLE
            recycler_view_movies.visibility = View.GONE
            layout_not_found.visibility = View.GONE
        } else {
            showList()
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
            edit_text_search.setText("", TextView.BufferType.EDITABLE)
            presenter.updateMovieList()
            swipe_refresh_layout_movies.isRefreshing = false
        }

        fab_update.setOnClickListener {
            presenter.updateMovieList()
        }

        edit_text_search.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if (p0.isNullOrBlank()) {
                    presenter.updateMovieList()
                } else {
                    presenter.searchMovie(p0.toString())
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                Log.d("!!!DA", p0.toString())
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                Log.d("!!!DA", p0.toString())
            }
        })

        onItemClickListener = View.OnClickListener {
            val position = recycler_view_movies.getChildAdapterPosition(it)
            Snackbar.make(layout_main, presenter.getMovie(position).title, Snackbar.LENGTH_SHORT).show()
        }
    }

}
