package exe.weazy.movies.di

import dagger.Module
import dagger.Provides
import exe.weazy.movies.presenter.MainPresenter
import exe.weazy.movies.view.MainActivity

@Module
class ArchModule(private var view : MainActivity) {

    @Provides
    fun provideMainPresenter() : MainPresenter {
        return MainPresenter(view)
    }

}