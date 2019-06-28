package exe.weazy.movies.model

import exe.weazy.movies.arch.MainContract
import exe.weazy.movies.di.App
import exe.weazy.movies.network.MovieResponse
import exe.weazy.movies.network.RequestService
import exe.weazy.movies.presenter.MainPresenter
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import java.io.File
import javax.inject.Inject

class MainModel(private var presenter: MainPresenter, private var file : File) : MainContract.Model {

    @Inject
    lateinit var retrofit: Retrofit

    init {
        App.getComponent().injectsMainModel(this)
    }

    override fun loadMovies(page : Int, query: String) {
        val service = retrofit.create(RequestService::class.java)

        val call = if (query.isBlank()) {
            service.getMovies(1)
        } else {
            service.getMoviesByQuery(query)
        }

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

    override fun writeLikes(likes : ArrayList<Int>) {
        val likesToWrite = StringBuilder()

        likes.forEach {
            likesToWrite.append(it.toString() + '\n')
        }

        file.writeText(likesToWrite.toString())
    }

    override fun getLikes() : ArrayList<Int> {

        return if (file.exists()) {
            val likes = file.readText().split("\n").toList().filterNot { it == "" }

            val result = ArrayList<Int>()

            likes.forEach {
                result.add(it.toInt())
            }

            result
        } else {
            file.createNewFile()

            ArrayList()
        }
    }
}