package com.example.themoviechallenge.presenter.viewmodel

import androidx.lifecycle.Observer
import com.example.themoviechallenge.base.BaseUnitTest
import com.example.themoviechallenge.base.data.Response
import com.example.themoviechallenge.domain.GetRelatedTvShowUseCase
import com.example.themoviechallenge.domain.GetTvShowUseCase
import com.example.themoviechallenge.domain.model.TvShow
import com.example.themoviechallenge.presenter.model.TvShowModel
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.BDDMockito

import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.isNotNull
import org.mockito.Mockito.times

class TvShowViewModelTest : BaseUnitTest() {

    private lateinit var viewModel: TvShowViewModel

    @Mock
    private lateinit var getRelatedTvShowUseCase: GetRelatedTvShowUseCase

    @Mock
    private lateinit var getTvShowUseCase: GetTvShowUseCase

    @Mock
    private lateinit var mutablePageTvShowListObserver: Observer<Int>

    @Mock
    private lateinit var tvShowLiveDataObserver: Observer<List<TvShowModel>>

    @Mock
    private lateinit var mutablePageTvShowRelatedListObserver: Observer<Int>

    @Mock
    private lateinit var tvShowRelatedLiveDataObserver: Observer<List<TvShowModel>>

    @Mock
    private lateinit var mutableConnectionObserver: Observer<Boolean>

    @Mock
    private lateinit var mutableLoadingObserver: Observer<Boolean>

    @Mock
    private lateinit var mutableThrowablesObserver: Observer<Throwable?>

    @Before
    fun setUp() {
        super.setup()

        viewModel = Mockito.spy(
            TvShowViewModel(
                getRelatedTvShowUseCase = getRelatedTvShowUseCase,
                getTvShowUseCase = getTvShowUseCase
            )
        )

        viewModel.tvShowLiveData.observeForever(tvShowLiveDataObserver)
        viewModel.tvShowRelatedLiveData.observeForever(tvShowRelatedLiveDataObserver)

        viewModel.mutableConnection.observeForever(mutableConnectionObserver)
        viewModel.mutableLoading.observeForever(mutableLoadingObserver)
        viewModel.mutableThrowables.observeForever(mutableThrowablesObserver)
    }

    @After
    override fun tearDown() {
        super.tearDown()

        viewModel.tvShowLiveData.removeObserver(tvShowLiveDataObserver)
        viewModel.tvShowRelatedLiveData.removeObserver(tvShowRelatedLiveDataObserver)

        viewModel.mutableConnection.removeObserver(mutableConnectionObserver)
        viewModel.mutableLoading.removeObserver(mutableLoadingObserver)
        viewModel.mutableThrowables.removeObserver(mutableThrowablesObserver)
    }

    @Test
    fun `test fetchTvShows return success event`() = testDispatcher.runBlockingTest {

        val expectedResult = listOf<TvShow>()

        val expectedFlow = flow {
            emit(Response.Success(expectedResult))
        }

        BDDMockito.given(getTvShowUseCase.execute()).willReturn(expectedFlow)

        viewModel.fetchTvShows(firstTime = true)

        Mockito.verify(getTvShowUseCase).bind(language = null, page = 1)
        Mockito.verify(getTvShowUseCase).execute()
        Mockito.verify(mutableLoadingObserver).onChanged(true)
        Mockito.verify(mutableConnectionObserver).onChanged(true)
        Mockito.verify(tvShowLiveDataObserver, times(1)).onChanged(isNotNull())
    }

    @Test
    fun `test fetchTvShowsRelated return success event`() = testDispatcher.runBlockingTest {

        val expectedResult = listOf<TvShow>()

        val expectedFlow = flow {
            emit(Response.Success(expectedResult))
        }

        BDDMockito.given(getRelatedTvShowUseCase.execute()).willReturn(expectedFlow)

        viewModel.fetchTvShowsRelated("fake_tv_id")

        Mockito.verify(getRelatedTvShowUseCase).bind(tvId = "fake_tv_id", language = null, page = 1)
        Mockito.verify(getRelatedTvShowUseCase).execute()
        Mockito.verify(mutableConnectionObserver).onChanged(true)
        Mockito.verify(tvShowRelatedLiveDataObserver, times(1)).onChanged(isNotNull())
    }
}