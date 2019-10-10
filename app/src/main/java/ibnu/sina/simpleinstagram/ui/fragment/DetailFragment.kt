package ibnu.sina.simpleinstagram.ui.fragment


import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.signature.ObjectKey
import com.google.android.material.snackbar.Snackbar

import ibnu.sina.simpleinstagram.R
import ibnu.sina.simpleinstagram.adapter.CommentAdapter
import ibnu.sina.simpleinstagram.databinding.FragmentDetailBinding
import ibnu.sina.simpleinstagram.model.commentmodel.CommentResponse
import ibnu.sina.simpleinstagram.model.postingmodel.getpostingmodel.BodyGetPostingResponse
import ibnu.sina.simpleinstagram.ui.baseclass.BaseFragment
import ibnu.sina.simpleinstagram.utils.start
import ibnu.sina.simpleinstagram.utils.stop
import ibnu.sina.simpleinstagram.viewmodel.MainFeedViewModel
import kotlinx.android.synthetic.main.fragment_detail.*

class DetailFragment : BaseFragment(), SwipeRefreshLayout.OnRefreshListener {

    private lateinit var adapter: CommentAdapter
    private lateinit var itemBinding: FragmentDetailBinding
    private var feeds: BodyGetPostingResponse? = null
    private val feedViewModel by lazy {
        ViewModelProviders.of(this, mainFeedVMFactory)[MainFeedViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        itemBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false)
        return itemBinding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.option_menu, menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        setMenuItem(menu)
        super.onPrepareOptionsMenu(menu)
    }

    private fun setMenuItem(menu: Menu?){
        menu?.findItem(R.id.add_new_menu)?.isVisible = false
        menu?.findItem(R.id.edit_posting_menu)?.isVisible = true
        menu?.findItem(R.id.delete_posting_menu)?.isVisible = true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item?.itemId){
            R.id.delete_posting_menu -> {
                feedViewModel.deletePosting(feeds?.userId.toString())
            }

            R.id.edit_posting_menu -> {
                val actionEdit = DetailFragmentDirections.actionDetailFragmentToEditFeedFragment(feeds)
                activity?.let { Navigation.findNavController(it, R.id.nav_host_feed_fragment).navigate(actionEdit) }
            }

        }

        return super.onOptionsItemSelected(item)
    }

    private fun initView(){
        getFeedComponent()

        feeds = arguments?.let { DetailFragmentArgs.fromBundle(it).argPosting }
        feedViewModel.succesGetComment.observe(this, commentObserver)
        feedViewModel.errorGetComment.observe(this, errorCommentObserver)
        feedViewModel.stateDeleteFeed.observe(this, deleteObserver)
        swipe_detail_feed.setOnRefreshListener(this)

        swipe_detail_feed.start()
        feedViewModel.getCommentPosting(feeds?.userId.toString())

        rv_comment_detail_feed.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }

        Glide.with(this)
            .load("")
            .signature(ObjectKey(""))

        feeds?.let { setDataBinding() }
    }

    private fun setDataBinding(){
        itemBinding.posting = feeds
    }

    private val deleteObserver = Observer<Boolean> {
        if (it){
            Snackbar.make(swipe_detail_feed, getString(R.string.text_delete_success), Snackbar.LENGTH_SHORT).show()
            findNavController().popBackStack()
        }else {
            Snackbar.make(swipe_detail_feed, getString(R.string.text_delete_gagal), Snackbar.LENGTH_SHORT).show()
        }
    }

    private val commentObserver = Observer<List<CommentResponse>> {
        adapter = CommentAdapter(it)
        rv_comment_detail_feed.adapter = adapter
        swipe_detail_feed.stop()
    }

    private val errorCommentObserver = Observer<Exception> {
        Snackbar.make(swipe_detail_feed, getString(R.string.cant_get_comment), Snackbar.LENGTH_SHORT).show()
        swipe_detail_feed.stop()
    }

    override fun onRefresh() {
        feedViewModel.getCommentPosting(feeds?.userId.toString())
    }

}
