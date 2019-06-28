package exe.weazy.movies.di

import dagger.Component
import exe.weazy.movies.model.MainModel

@Component(modules = [NetworkModule::class])
interface AppComponent {
    fun injectsMainModel(model : MainModel)
}