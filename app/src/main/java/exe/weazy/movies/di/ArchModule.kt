package exe.weazy.movies.di

import dagger.Module
import dagger.Provides
import exe.weazy.movies.presenter.MainPresenter

@Module
class ArchModule {

    @Provides
    fun provideMainPresenter() : MainPresenter {
        return MainPresenter()
    }

}