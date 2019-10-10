package ibnu.sina.simpleinstagram.di.component

import dagger.Component
import ibnu.sina.simpleinstagram.di.modul.FeedVMModule
import ibnu.sina.simpleinstagram.di.modul.NetworkModul
import ibnu.sina.simpleinstagram.di.scope.FeedScope
import ibnu.sina.simpleinstagram.ui.baseclass.BaseFragment
import ibnu.sina.simpleinstagram.ui.fragment.FeedFragment
import javax.inject.Singleton


@Singleton
@FeedScope
@Component(modules = [NetworkModul::class, FeedVMModule::class])
interface FeedComponent {
    fun inject(fragment: BaseFragment)
}