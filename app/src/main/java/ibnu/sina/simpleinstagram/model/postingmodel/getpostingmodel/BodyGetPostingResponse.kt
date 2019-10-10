package ibnu.sina.simpleinstagram.model.postingmodel.getpostingmodel

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BodyGetPostingResponse(
    val body: String,
    val id: Int,
    val title: String,
    val userId: Int
): Parcelable