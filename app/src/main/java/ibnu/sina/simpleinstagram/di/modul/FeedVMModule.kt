package ibnu.sina.simpleinstagram.di.modul

import dagger.Module
import dagger.Provides
import ibnu.sina.simpleinstagram.di.scope.FeedScope
import ibnu.sina.simpleinstagram.network.ApiInterface
import ibnu.sina.simpleinstagram.repository.FeedRepository
import ibnu.sina.simpleinstagram.viewmodel.MainFeedVMFactory

@Module
class FeedVMModule {

    @Provides
    @FeedScope
    fun provideFeedRepo(apiInterface: ApiInterface): FeedRepository = FeedRepository(apiInterface)

    @Provides
    @FeedScope
    fun provideFeddVMFactory(feedRepository: FeedRepository): MainFeedVMFactory = MainFeedVMFactory(feedRepository)
}