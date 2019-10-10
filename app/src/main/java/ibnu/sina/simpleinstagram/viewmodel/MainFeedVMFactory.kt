package ibnu.sina.simpleinstagram.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ibnu.sina.simpleinstagram.repository.FeedRepository

class MainFeedVMFactory(
    private val feedRepository: FeedRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>) = MainFeedViewModel(feedRepository) as T
}