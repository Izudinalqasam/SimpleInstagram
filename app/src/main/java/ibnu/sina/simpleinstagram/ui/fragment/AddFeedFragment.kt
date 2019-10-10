package ibnu.sina.simpleinstagram.ui.fragment


import android.os.Bundle
import android.view.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar

import ibnu.sina.simpleinstagram.R
import ibnu.sina.simpleinstagram.ui.baseclass.BaseFragment
import ibnu.sina.simpleinstagram.utils.start
import ibnu.sina.simpleinstagram.utils.stop
import ibnu.sina.simpleinstagram.viewmodel.MainFeedViewModel
import kotlinx.android.synthetic.main.fragment_add_feed.*


class AddFeedFragment : BaseFragment() {

    private val mainFeedViewModel by lazy {
        ViewModelProviders.of(this, mainFeedVMFactory)[MainFeedViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_feed, container, false)
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


        mainFeedViewModel.statePostFeed.observe(this, stateAddObserver)
        btn_add_feed.setOnClickListener { sendUpdateFeed() }
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
        menu?.findItem(R.id.edit_posting_menu)?.isVisible = false
        menu?.findItem(R.id.delete_posting_menu)?.isVisible = false
    }

    private fun sendUpdateFeed(){
        swipe_add_feed.isEnabled = true
        swipe_add_feed.start()
        mainFeedViewModel.postNewsFeed(edt_add_body.text.toString(), edt_add_title.text.toString())
    }

    private val stateAddObserver = Observer<Boolean>{
        if (it){
            Snackbar.make(swipe_add_feed, getString(R.string.feed_posting_success), Snackbar.LENGTH_SHORT).show()
            findNavController().popBackStack()
        }else{
            Snackbar.make(swipe_add_feed, getString(R.string.feed_failed_posting), Snackbar.LENGTH_SHORT).show()
        }

        swipe_add_feed.stop()
        swipe_add_feed.isEnabled = false
    }
}
