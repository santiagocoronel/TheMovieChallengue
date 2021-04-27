package com.example.themoviechallenge.domain

import com.example.themoviechallenge.base.BaseUnitTest
import com.example.themoviechallenge.base.data.Response
import com.example.themoviechallenge.domain.model.TvShow
import com.example.themoviechallenge.domain.repository.IGetTvShowRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before

import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito

class GetTvShowUseCaseTest : BaseUnitTest() {

    @Mock
    private lateinit var repository: IGetTvShowRepository

    private lateinit var useCase: GetTvShowUseCase

    @Before
    fun setUp() {
        useCase = Mockito.spy(GetTvShowUseCase(repository))
    }

    @Test
    fun `test execute success`() = testDispatcher.runBlockingTest {

        val mockResponse = listOf<TvShow>()

        val flowExpected = flow {
            emit(Response.Success(mockResponse))
        }

        Mockito.`when`(
            repository.getTvShows(language = null, page = 1)
        ).thenReturn(flowExpected)

        useCase.bind(
            language = null,
            page = 1
        )

        useCase.execute().collect {
            Assert.assertNotNull(it)
        }

        Mockito.verify(repository).getTvShows(language = null, page = 1)

    }
}