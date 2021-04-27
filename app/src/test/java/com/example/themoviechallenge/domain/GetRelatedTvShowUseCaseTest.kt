package com.example.themoviechallenge.domain

import com.example.themoviechallenge.base.BaseUnitTest
import com.highquality.base.data.Response
import com.example.themoviechallenge.domain.model.TvShow
import com.example.themoviechallenge.domain.repository.IGetRelatedTvShowRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

import org.mockito.Mock
import org.mockito.Mockito

class GetRelatedTvShowUseCaseTest : BaseUnitTest() {

    @Mock
    private lateinit var repository: IGetRelatedTvShowRepository

    private lateinit var useCase: GetRelatedTvShowUseCase

    @Before
    fun setUp() {
        useCase = Mockito.spy(GetRelatedTvShowUseCase(repository))
    }

    @Test
    fun `test execute success`() = testDispatcher.runBlockingTest {

        val mockResponse = listOf<TvShow>()

        val flowExpected = flow {
            emit(Response.Success(mockResponse))
        }

        Mockito.`when`(
            repository.getRelatedTvShows(tvId = "fake_tv_id", language = null, page = 1)
        ).thenReturn(flowExpected)

        useCase.bind(
            tvId = "fake_tv_id",
            language = null,
            page = 1
        )

        useCase.execute().collect {
            Assert.assertNotNull(it)
        }

        Mockito.verify(repository).getRelatedTvShows(tvId = "fake_tv_id", language = null, page = 1)

    }
}