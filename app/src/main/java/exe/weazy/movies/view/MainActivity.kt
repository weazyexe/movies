package exe.weazy.movies.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import exe.weazy.movies.R
import exe.weazy.movies.adapter.MoviesAdapter
import exe.weazy.movies.arch.MainContract
import exe.weazy.movies.di.ArchModule
import exe.weazy.movies.di.DaggerComponent
import exe.weazy.movies.di.NetworkModule
import exe.weazy.movies.entity.Movie
import exe.weazy.movies.presenter.MainPresenter
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Retrofit
import javax.inject.Inject

class MainActivity : AppCompatActivity(), MainContract.View {

    @Inject
    lateinit var presenter: MainPresenter

    private lateinit var movies : ArrayList<Movie>

    private lateinit var adapter : MoviesAdapter

    private lateinit var manager : LinearLayoutManager

    private var page = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        injectDependencies()

        presenter.updateMovieList(page)
    }

    override fun onStart() {
        super.onStart()

        adapter = MoviesAdapter(movies)
        manager = LinearLayoutManager(this)

        recycler_view_movies.adapter = adapter
        recycler_view_movies.layoutManager = manager
    }

    override fun showList(movies : ArrayList<Movie>) {
        adapter.setMovies(movies)
    }

    override fun showLoading() {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showNotFound() {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showError() {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }



    private fun injectDependencies() {
        val component = DaggerComponent.builder()
            .archModule(ArchModule(this))
            .networkModule(NetworkModule())
            .build()

        component.injectsMainActivity(this)
    }
}
