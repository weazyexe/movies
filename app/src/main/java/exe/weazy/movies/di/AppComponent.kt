package exe.weazy.movies.di

import dagger.Component
import exe.weazy.movies.view.MainActivity
import retrofit2.Retrofit

@Component(modules = [ArchModule::class, NetworkModule::class])
interface Component {
    fun injectsMainActivity(activity : MainActivity)

    //fun getRetrofit() : Retrofit
}