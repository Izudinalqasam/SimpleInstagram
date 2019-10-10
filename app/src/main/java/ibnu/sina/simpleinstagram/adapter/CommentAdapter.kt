package ibnu.sina.simpleinstagram.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ibnu.sina.simpleinstagram.R
import ibnu.sina.simpleinstagram.model.commentmodel.CommentResponse
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_comment_posting.*

class CommentAdapter(
    private val comments: List<CommentResponse>
): RecyclerView.Adapter<CommentAdapter.CommentViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        CommentViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_comment_posting, parent, false))

    override fun getItemCount() = comments.size

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        holder.bindItem(comments[position])
    }

    class CommentViewHolder(override val containerView: View)
        : RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bindItem(comment: CommentResponse){
            tv_email_item_comment.text = comment.email
            tv_content_item_comment.text = comment.body
        }
    }
}