package ibnu.sina.simpleinstagram.ui.fragment


import android.os.Bundle
import android.view.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.navigation.ui.onNavDestinationSelected
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.snackbar.Snackbar

import ibnu.sina.simpleinstagram.R
import ibnu.sina.simpleinstagram.adapter.FeedAdapter
import ibnu.sina.simpleinstagram.model.postingmodel.getpostingmodel.BodyGetPostingResponse
import ibnu.sina.simpleinstagram.ui.baseclass.BaseFragment
import ibnu.sina.simpleinstagram.utils.Utils
import ibnu.sina.simpleinstagram.utils.start
import ibnu.sina.simpleinstagram.utils.stop
import ibnu.sina.simpleinstagram.viewmodel.MainFeedViewModel
import kotlinx.android.synthetic.main.fragment_feed.*
import java.lang.Exception


class FeedFragment : BaseFragment(), FeedAdapter.IFeedOperation, SwipeRefreshLayout.OnRefreshListener {

    private lateinit var adapter: FeedAdapter
    private val feedViewModel by lazy {
        ViewModelProviders.of(this, mainFeedVMFactory)[MainFeedViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_feed, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView(){
        getFeedComponent()
        Utils.hideKeyboard(swipe_feed_posting, context)

        feedViewModel.succesGetPosting.observe(this, postingObserver)
        feedViewModel.errorGetPosting.observe(this, errorPostingObserver)

        swipe_feed_posting.post { swipe_feed_posting.start() }
        feedViewModel.getFeedPosting()
        swipe_feed_posting.setOnRefreshListener(this)

        rv_feed_posting.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        setMenuItem(menu)
        super.onPrepareOptionsMenu(menu)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.option_menu, menu)
    }

    private fun setMenuItem(menu: Menu?){
        menu?.findItem(R.id.add_new_menu)?.isVisible = true
        menu?.findItem(R.id.edit_posting_menu)?.isVisible = false
        menu?.findItem(R.id.delete_posting_menu)?.isVisible = false
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val navController = activity?.let { Navigation.findNavController(it, R.id.nav_host_feed_fragment) }
        return navController?.let { item.onNavDestinationSelected(it) } ?: super.onOptionsItemSelected(item)
    }

    private val postingObserver = Observer<List<BodyGetPostingResponse>>{
        adapter = FeedAdapter(it, this@FeedFragment)
        rv_feed_posting.adapter = adapter
        swipe_feed_posting.stop()
    }

    private val errorPostingObserver = Observer<Exception> {
        swipe_feed_posting.stop()
        Snackbar.make(swipe_feed_posting, it.localizedMessage, Snackbar.LENGTH_SHORT).show()
    }

    override fun onRefresh() {
        feedViewModel.getFeedPosting()
        swipe_feed_posting.stop()
    }

    override fun onFeedClicked(item: BodyGetPostingResponse) {
        val action = FeedFragmentDirections.actionFeedFragmentToDetailFragment(item)
        activity?.let { Navigation.findNavController(it, R.id.nav_host_feed_fragment).navigate(action) }
    }

}
