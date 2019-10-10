package ibnu.sina.simpleinstagram.ui.fragment


import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar

import ibnu.sina.simpleinstagram.R
import ibnu.sina.simpleinstagram.databinding.FragmentEditFeedBinding
import ibnu.sina.simpleinstagram.model.postingmodel.getpostingmodel.BodyGetPostingResponse
import ibnu.sina.simpleinstagram.ui.baseclass.BaseFragment
import ibnu.sina.simpleinstagram.utils.start
import ibnu.sina.simpleinstagram.utils.stop
import ibnu.sina.simpleinstagram.viewmodel.MainFeedViewModel
import kotlinx.android.synthetic.main.fragment_edit_feed.*


class EditFeedFragment : BaseFragment() {

    private lateinit var layoutBinding: FragmentEditFeedBinding
    private var feeds: BodyGetPostingResponse? = null
    private val mainFeedViewModel by lazy {
        ViewModelProviders.of(this, mainFeedVMFactory)[MainFeedViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        layoutBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_edit_feed, container, false)
        return layoutBinding.root
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

        feeds = arguments?.let { EditFeedFragmentArgs.fromBundle(it).argPosting }
        mainFeedViewModel.stateUpdateFeed.observe(this, stateEditObserver)

        btn_edit_feed.setOnClickListener { sendUpdateFeed() }

        feeds?.let { setItemBinding() }
    }

    private fun setItemBinding(){
        layoutBinding.postingEdit = feeds
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
        swipe_edit_feed.isEnabled = true
        swipe_edit_feed.start()
        mainFeedViewModel.updatePostingFeed(feeds, edt_edit_body.text.toString(), edt_edit_title.text.toString())
    }

    private val stateEditObserver = Observer<Boolean>{
        if (it){
            Snackbar.make(swipe_edit_feed, "Data berhasil di update", Snackbar.LENGTH_SHORT).show()
            navigateToHome()
        }else{
            Snackbar.make(swipe_edit_feed, "Gagal update data", Snackbar.LENGTH_SHORT).show()
        }

        swipe_edit_feed.stop()
        swipe_edit_feed.isEnabled = false
    }

    private fun navigateToHome(){
        activity?.let { Navigation.findNavController(it, R.id.nav_host_feed_fragment).navigate(R.id.action_editFeedFragment_to_feedFragment) }
    }

}
