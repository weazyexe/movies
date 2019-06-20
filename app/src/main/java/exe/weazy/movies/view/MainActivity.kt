package exe.weazy.movies.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import exe.weazy.movies.R
import exe.weazy.movies.arch.MainContract
import exe.weazy.movies.di.ArchModule
import exe.weazy.movies.di.DaggerComponent
import exe.weazy.movies.di.NetworkModule
import exe.weazy.movies.presenter.MainPresenter
import retrofit2.Retrofit
import javax.inject.Inject

class MainActivity : AppCompatActivity(), MainContract.View {

    @Inject
    lateinit var retrofit: Retrofit

    @Inject
    lateinit var presenter: MainPresenter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        injectDependencies()


        /*val retrofit = Retrofit.Builder()
            .baseUrl("kek")
            .build()

        val service = retrofit.create(RequestService::class.java)
        val call = service.getMovies()
        call.enqueue(object : retrofit2.Callback<List<Movie>> {
            override fun onFailure(call: Call<List<Movie>>, t: Throwable) {

            }

            override fun onResponse(call: Call<List<Movie>>, response: Response<List<Movie>>) {
                val movies = response.body()
            }

        })*/
    }

    override fun showList() {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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
