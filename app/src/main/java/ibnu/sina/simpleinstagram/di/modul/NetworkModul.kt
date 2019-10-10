package ibnu.sina.simpleinstagram.di.modul

import dagger.Module
import dagger.Provides
import ibnu.sina.simpleinstagram.di.scope.FeedScope
import ibnu.sina.simpleinstagram.network.ApiInterface
import ibnu.sina.simpleinstagram.network.Retrofit

@Module
class NetworkModul {

    @Provides
    @FeedScope
    fun provideOkHttpClient(): ApiInterface = Retrofit.instance

}