package ibnu.sina.simpleinstagram.ui.baseclass

import androidx.fragment.app.Fragment
import ibnu.sina.simpleinstagram.di.component.DaggerFeedComponent
import ibnu.sina.simpleinstagram.viewmodel.MainFeedVMFactory
import javax.inject.Inject

abstract class BaseFragment : Fragment()  {

    @Inject
    lateinit var mainFeedVMFactory: MainFeedVMFactory

    protected fun getFeedComponent() {
        DaggerFeedComponent.builder()
            .build()
            .inject(this)
    }
}