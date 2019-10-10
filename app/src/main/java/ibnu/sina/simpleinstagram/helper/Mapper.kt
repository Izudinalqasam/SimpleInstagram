package ibnu.sina.simpleinstagram.helper

import ibnu.sina.simpleinstagram.model.postingmodel.getpostingmodel.BodyGetPostingResponse
import ibnu.sina.simpleinstagram.model.postingmodel.postpostingmodel.BodyPostingRequest
import ibnu.sina.simpleinstagram.model.postingmodel.updatepostingmodel.BodyUpdateRequest

object Mapper {

    fun getToUpdatePosting(bodyGetPostingResponse: BodyGetPostingResponse, body: String, title: String): BodyUpdateRequest {
        return BodyUpdateRequest(body, title, bodyGetPostingResponse.userId)
    }

    fun createObjekPosting(body: String, title: String) =
            BodyPostingRequest(body, title, 1)
}