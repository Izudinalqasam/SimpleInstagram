package ibnu.sina.simpleinstagram.repository

import ibnu.sina.simpleinstagram.helper.Mapper
import ibnu.sina.simpleinstagram.model.postingmodel.getpostingmodel.BodyGetPostingResponse
import ibnu.sina.simpleinstagram.model.postingmodel.postpostingmodel.BodyPostingRequest
import ibnu.sina.simpleinstagram.network.ApiInterface
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class FeedRepository(private val apiInterface: ApiInterface) {

    fun getFeedPosting() = apiInterface.getPostingContent()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

    fun getCommentPosting(id: String) = apiInterface.getCommentPosting(id)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

    fun postNewFeed(bodyPostingRequest: BodyPostingRequest) = apiInterface.postPosting(bodyPostingRequest)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

    fun updateFeedPosting(bodyGetPostingResponse: BodyGetPostingResponse, body: String, title: String) =
        apiInterface.updatePosting(bodyGetPostingResponse.userId.toString(), Mapper.getToUpdatePosting(bodyGetPostingResponse, body, title ))
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

    fun deleteFeedPosting(id: String) = apiInterface.deletePosting(id)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}