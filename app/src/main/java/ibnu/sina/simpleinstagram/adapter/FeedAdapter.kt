package ibnu.sina.simpleinstagram.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ibnu.sina.simpleinstagram.R
import ibnu.sina.simpleinstagram.model.postingmodel.getpostingmodel.BodyGetPostingResponse
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_feed_posting.*

class FeedAdapter(
    private val listPosting: List<BodyGetPostingResponse>,
    private val listener: IFeedOperation
) : RecyclerView.Adapter<FeedAdapter.FeedViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        FeedViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_feed_posting, parent, false))

    override fun getItemCount() = listPosting.size

    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
        holder.bindItem(listPosting[position], listener)
    }

    class FeedViewHolder(override val containerView: View): RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bindItem(item: BodyGetPostingResponse, listener: IFeedOperation){

            tv_title_feed.text = item.title
            tv_content_feed.text = item.body

            item_feed_parent.setOnClickListener {
                listener.onFeedClicked(item)
            }
        }
    }

    interface IFeedOperation {
        fun onFeedClicked(item: BodyGetPostingResponse)
    }
}
