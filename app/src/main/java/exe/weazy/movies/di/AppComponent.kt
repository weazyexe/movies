package exe.weazy.movies.di

import dagger.Component
import exe.weazy.movies.model.MainModel
import exe.weazy.movies.view.MainActivity

@Component(modules = [ArchModule::class, NetworkModule::class])
interface AppComponent {
    fun injectsMainActivity(activity : MainActivity)
    fun injectsMainModel(model : MainModel)
}