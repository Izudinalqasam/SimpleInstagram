package ibnu.sina.simpleinstagram.network

import ibnu.sina.simpleinstagram.model.commentmodel.CommentResponse
import ibnu.sina.simpleinstagram.model.postingmodel.getpostingmodel.BodyGetPostingResponse
import ibnu.sina.simpleinstagram.model.postingmodel.postpostingmodel.BodyPostingRequest
import ibnu.sina.simpleinstagram.model.postingmodel.updatepostingmodel.BodyUpdateRequest
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.*

interface ApiInterface {

    @GET("posts?_limit=10")
    fun getPostingContent(): Single<List<BodyGetPostingResponse>>

    @GET("/comments?")
    fun getCommentPosting(@Query("postId") id: String): Single<List<CommentResponse>>

    @POST("posts")
    fun postPosting(@Body bodyPostingRequest: BodyPostingRequest): Completable

    @PUT("posts/{postId}")
    fun updatePosting(@Path("postId") id: String, @Body bodyUpdateRequest: BodyUpdateRequest): Completable

    @DELETE("posts/{postId}")
    fun deletePosting(@Path("postId") id: String): Completable
}