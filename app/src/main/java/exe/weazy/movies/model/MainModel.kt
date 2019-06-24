package exe.weazy.movies.model

import exe.weazy.movies.arch.LoadingListener
import exe.weazy.movies.arch.MainContract
import exe.weazy.movies.di.DaggerComponent
import exe.weazy.movies.di.NetworkModule
import exe.weazy.movies.entity.Movie
import exe.weazy.movies.network.RequestService
import exe.weazy.movies.presenter.MainPresenter
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject

class MainModel(private var presenter: MainPresenter) : MainContract.Model {

    @Inject
    lateinit var retrofit: Retrofit

    init {
        injectDependencies()
    }

    override fun loadMovies(page : Int, query: String) {
        val service = retrofit.create(RequestService::class.java)
        val call = service.getMovies(1)
        call.enqueue(object : retrofit2.Callback<ArrayList<Movie>> {
            override fun onFailure(call: Call<ArrayList<Movie>>, t: Throwable) {
                presenter.onFailure(t)
            }

            override fun onResponse(call: Call<ArrayList<Movie>>, response: Response<ArrayList<Movie>>) {
                val movies = response.body()
                presenter.onFinished(movies)
            }

        })
    }

    private fun injectDependencies() {
        val component = DaggerComponent.builder()
            .networkModule(NetworkModule())
            .build()

        //retrofit = component.injectsMainActivity()
    }
}