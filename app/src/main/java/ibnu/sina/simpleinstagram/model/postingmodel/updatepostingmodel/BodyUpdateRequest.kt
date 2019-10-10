package ibnu.sina.simpleinstagram.model.postingmodel.updatepostingmodel

data class BodyUpdateRequest(
    val body: String,
    val title: String,
    val userId: Int
)