package exe.weazy.movies.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import exe.weazy.movies.R
import exe.weazy.movies.Tools
import exe.weazy.movies.adapter.MoviesAdapter
import exe.weazy.movies.arch.MainContract
import exe.weazy.movies.arch.MainViewModel
import exe.weazy.movies.di.App
import exe.weazy.movies.entity.Movie
import exe.weazy.movies.presenter.MainPresenter
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import javax.inject.Inject

class MainActivity : AppCompatActivity(), MainContract.View {

    private lateinit var viewModel : MainViewModel

    private lateinit var presenter: MainPresenter

    private lateinit var adapter : MoviesAdapter

    private lateinit var manager : LinearLayoutManager

    private lateinit var onItemClickListener : View.OnClickListener

    private lateinit var onLikeClickListener : View.OnClickListener



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        val presenterLiveData = viewModel.getPresenter()
        presenterLiveData.observe(this, Observer {
            presenter = it

            presenter.attach(this, File(filesDir, "likes"))
            presenter.updateMovieList()
            showList()
        })
    }

    override fun onStart() {
        super.onStart()

        initListeners()
        initRecyclerView()
    }

    override fun onBackPressed() {
        if (!edit_text_search.text.isNullOrBlank()) {
            edit_text_search.setText("", TextView.BufferType.EDITABLE)
            Tools.hideKeyboard(layout_main, this)
            edit_text_search.clearFocus()
        } else {
            super.onBackPressed()
        }
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
        adapter = MoviesAdapter(this, ArrayList(), onItemClickListener, onLikeClickListener)
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

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
        })

        onItemClickListener = View.OnClickListener {
            val position = recycler_view_movies.getChildAdapterPosition(it)
            Snackbar.make(layout_main, presenter.getMovie(position).title, Snackbar.LENGTH_SHORT).show()
        }

        onLikeClickListener = View.OnClickListener {
            if (it is ImageView) {
                val position = recycler_view_movies.getChildAdapterPosition(it.parent.parent as View)
                val movie = presenter.getMovie(position)

                if (movie.like) {
                    it.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.ic_heart, null))
                } else {
                    it.setImageDrawable(ResourcesCompat.getDrawable(resources, R.drawable.ic_heart_fill, null))
                }

                presenter.likeMovie(movie.id)
            }
        }
    }

}
