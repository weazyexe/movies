package exe.weazy.movies.model

import exe.weazy.movies.arch.MainContract
import exe.weazy.movies.di.App
import exe.weazy.movies.network.MovieResponse
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
        App.getComponent().injectsMainModel(this)
    }

    override fun loadMovies(page : Int, query: String) {
        val service = retrofit.create(RequestService::class.java)
        val call = service.getMovies(1)
        call.enqueue(object : retrofit2.Callback<MovieResponse> {
            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                presenter.onFailure(t)
            }

            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                val movies = response.body()?.movies
                presenter.onFinished(movies)
            }

        })
    }
}