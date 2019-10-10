package ibnu.sina.simpleinstagram

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import ibnu.sina.simpleinstagram.model.postingmodel.getpostingmodel.BodyGetPostingResponse
import ibnu.sina.simpleinstagram.repository.FeedRepository
import ibnu.sina.simpleinstagram.viewmodel.MainFeedViewModel
import io.reactivex.Single
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MainFeedTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    private lateinit var feedRepo: FeedRepository

    @Mock
    private lateinit var observer: Observer<List<BodyGetPostingResponse>>

    private lateinit var viewModel: MainFeedViewModel

    @Before
    fun setup(){
        MockitoAnnotations.initMocks(this)
        viewModel = MainFeedViewModel(feedRepo)
    }

    @Test
    fun testFeedViewModel(){
        val list = mutableListOf<BodyGetPostingResponse>()

        `when`(feedRepo.getFeedPosting()).thenReturn(Single.just(list))

        viewModel.succesGetPosting.observeForever(observer)
        viewModel.getFeedPosting()

        verify(feedRepo).getFeedPosting()
        verify(observer).onChanged(list)

        assertEquals(list, viewModel.succesGetPosting.value)
    }

}