package ibnu.sina.simpleinstagram.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ibnu.sina.simpleinstagram.helper.Mapper
import ibnu.sina.simpleinstagram.model.commentmodel.CommentResponse
import ibnu.sina.simpleinstagram.model.postingmodel.getpostingmodel.BodyGetPostingResponse
import ibnu.sina.simpleinstagram.model.postingmodel.postpostingmodel.BodyPostingRequest
import ibnu.sina.simpleinstagram.repository.FeedRepository
import io.reactivex.disposables.CompositeDisposable

class MainFeedViewModel(
    private val feedRepository: FeedRepository
) : ViewModel() {

    val succesGetPosting = MutableLiveData<List<BodyGetPostingResponse>>()
    val errorGetPosting = MutableLiveData<Exception>()
    val succesGetComment = MutableLiveData<List<CommentResponse>>()
    val errorGetComment = MutableLiveData<Exception>()

    val stateUpdateFeed = MutableLiveData<Boolean>()
    val statePostFeed = MutableLiveData<Boolean>()
    val stateDeleteFeed = MutableLiveData<Boolean>()
    private val disposable = CompositeDisposable()


    fun getFeedPosting(){
        disposable.add(feedRepository.getFeedPosting()
            .subscribe({
                succesGetPosting.postValue(it)
            }, {
                errorGetPosting.postValue(Exception(it))
            }))
    }

    fun getCommentPosting(id: String){
        disposable.add(feedRepository.getCommentPosting(id)
            .subscribe({
                succesGetComment.postValue(it)
            }, {
                errorGetComment.postValue(Exception(it))
            }))
    }

    fun postNewsFeed(body: String, title: String){
        disposable.add(feedRepository.postNewFeed(Mapper.createObjekPosting(body, title))
            .subscribe({
                statePostFeed.postValue(true)
            }, {
                statePostFeed.postValue(false)
            }))
    }

    fun updatePostingFeed(bodyGetPostingResponse: BodyGetPostingResponse?, body: String, title: String){
        bodyGetPostingResponse?.let {
            disposable.add(feedRepository.updateFeedPosting(it, body, title)
                .subscribe({
                    stateUpdateFeed.postValue(true)
                }, {
                    stateUpdateFeed.postValue(false)
                }))
        }
    }

    fun deletePosting(id: String){
        disposable.add(feedRepository.deleteFeedPosting(id)
            .subscribe({
                stateDeleteFeed.postValue(true)
            }, {
                stateDeleteFeed.postValue(false)
            }))
    }

    override fun onCleared() {
        super.onCleared()

        if (!disposable.isDisposed){
            disposable.dispose()
        }
    }

}