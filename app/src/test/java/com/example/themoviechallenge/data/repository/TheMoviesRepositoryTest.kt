package com.example.themoviechallenge.data.repository

import com.example.themoviechallenge.BuildConfig
import com.example.themoviechallenge.data.repository.network.api.TheMoviesApi
import com.example.themoviechallenge.data.repository.network.response.TvPageResponse
import com.example.themoviechallenge.base.data.Response
import com.example.themoviechallenge.base.BaseUnitTest
import com.example.themoviechallenge.base.exception.GenericException
import com.example.themoviechallenge.base.exception.NoInternetException
import com.example.themoviechallenge.base.exception.ServiceErrorException
import com.example.themoviechallenge.base.exception.UnAuthorizeException
import com.example.themoviechallenge.data.repository.network.response.TvResponse
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.BDDMockito
import org.mockito.Mock
import org.mockito.Mockito
import java.net.UnknownHostException

class TheMoviesRepositoryTest : BaseUnitTest() {

    @Mock
    private lateinit var theMoviesApi: TheMoviesApi

    private lateinit var repository: TheMoviesRepository

    @Before
    fun setUp() {
        repository = TheMoviesRepository(theMoviesApi)
    }

    @Test
    fun `test getTvShows then success`(): Unit = runBlocking {

        val mockReturn = TvPageResponse(
            page = 1,
            results = listOf(
                TvResponse(
                    backdropPath = "fake_backdrop_path",
                    firstAirDate = "fake_first_air_date",
                    genreIds = listOf(1),
                    id = 1,
                    name = "fake_name",
                    originCountry = listOf("fake_country"),
                    originalLanguage = "fake_original_language",
                    originalName = "fake_original_name",
                    overview = "fake_overview",
                    popularity = 10.0,
                    posterPath = "fake_poster_path",
                    voteAverage = 10.0,
                    voteCount = 10
                )
            ),
            totalPages = 10,
            totalResults = 1
        )

        val mockResponse = retrofit2.Response.success(mockReturn)

        BDDMockito.given(
            theMoviesApi.getPopularTvShows(
                apiKey = BuildConfig.APIKEY,
                language = null,
                page = 1
            )
        ).willReturn(mockResponse)

        repository.getTvShows(language = null, page = 1).collect {
            when (it) {
                is Response.Success -> {
                    Assert.assertNotNull(it.data)
                }
            }
        }
    }

    @Test
    fun `test getTvShows then exception 401`(): Unit = runBlocking {

        val mockResponseBody = Mockito.mock(ResponseBody::class.java)
        val mockResponse = retrofit2.Response.error<TvPageResponse>(401,mockResponseBody)

        BDDMockito.given(
            theMoviesApi.getPopularTvShows(
                apiKey = BuildConfig.APIKEY,
                language = null,
                page = 1
            )
        ).willReturn(mockResponse)

        repository.getTvShows(language = null, page = 1).collect {
            when (it) {
                is Response.Failure<*> -> {
                    Assert.assertTrue(it.error is UnAuthorizeException)
                }
            }
        }
    }

    @Test
    fun `test getTvShows then exception 404`(): Unit = runBlocking {

        val mockResponseBody = Mockito.mock(ResponseBody::class.java)
        val mockResponse = retrofit2.Response.error<TvPageResponse>(404,mockResponseBody)

        BDDMockito.given(
            theMoviesApi.getPopularTvShows(
                apiKey = BuildConfig.APIKEY,
                language = null,
                page = 1
            )
        ).willReturn(mockResponse)

        repository.getTvShows(language = null, page = 1).collect {
            when (it) {
                is Response.Failure<*> -> {
                    Assert.assertTrue(it.error is ServiceErrorException)
                }
            }
        }
    }

    @Test
    fun `test getTvShows then exception 500`(): Unit = runBlocking {

        val mockResponseBody = Mockito.mock(ResponseBody::class.java)
        val mockResponse = retrofit2.Response.error<TvPageResponse>(500,mockResponseBody)

        BDDMockito.given(
            theMoviesApi.getPopularTvShows(
                apiKey = BuildConfig.APIKEY,
                language = null,
                page = 1
            )
        ).willReturn(mockResponse)

        repository.getTvShows(language = null, page = 1).collect {
            when (it) {
                is Response.Failure<*> -> {
                    Assert.assertTrue(it.error is GenericException)
                }
            }
        }
    }

    @Test
    fun `test getTvShowsRelated then success`(): Unit = runBlocking {

        val mockReturn = TvPageResponse(
            page = 1,
            results = listOf(
                TvResponse(
                    backdropPath = "fake_backdrop_path",
                    firstAirDate = "fake_first_air_date",
                    genreIds = listOf(1),
                    id = 1,
                    name = "fake_name",
                    originCountry = listOf("fake_country"),
                    originalLanguage = "fake_original_language",
                    originalName = "fake_original_name",
                    overview = "fake_overview",
                    popularity = 10.0,
                    posterPath = "fake_poster_path",
                    voteAverage = 10.0,
                    voteCount = 10
                )
            ),
            totalPages = 10,
            totalResults = 1
        )

        val mockResponse = retrofit2.Response.success(mockReturn)

        BDDMockito.given(
            theMoviesApi.getSimilarTvShows(
                apiKey = BuildConfig.APIKEY,
                language = null,
                page = 1,
                tvId = "123"
            )
        ).willReturn(mockResponse)

        repository.getRelatedTvShows(tvId = "123", language = null, page = 1).collect {
            when (it) {
                is Response.Success -> {
                    Assert.assertNotNull(it.data)
                }
            }
        }
    }

    @Test
    fun `test getTvShowsRelated then exception 401`(): Unit = runBlocking {

        val mockResponseBody = Mockito.mock(ResponseBody::class.java)
        val mockResponse = retrofit2.Response.error<TvPageResponse>(401,mockResponseBody)

        BDDMockito.given(
            theMoviesApi.getSimilarTvShows(
                apiKey = BuildConfig.APIKEY,
                language = null,
                page = 1,
                tvId = "123"
            )
        ).willReturn(mockResponse)

        repository.getRelatedTvShows(tvId = "123", language = null, page = 1).collect {
            when (it) {
                is Response.Failure<*> -> {
                    Assert.assertTrue(it.error is UnAuthorizeException)
                }
            }
        }
    }

    @Test
    fun `test getTvShowsRelated then exception 404`(): Unit = runBlocking {

        val mockResponseBody = Mockito.mock(ResponseBody::class.java)
        val mockResponse = retrofit2.Response.error<TvPageResponse>(404,mockResponseBody)

        BDDMockito.given(
            theMoviesApi.getSimilarTvShows(
                apiKey = BuildConfig.APIKEY,
                language = null,
                page = 1,
                tvId = "123"
            )
        ).willReturn(mockResponse)

        repository.getRelatedTvShows(tvId = "123", language = null, page = 1).collect {
            when (it) {
                is Response.Failure<*> -> {
                    Assert.assertTrue(it.error is ServiceErrorException)
                }
            }
        }
    }

    @Test
    fun `test getTvShowsRelated then exception 500`(): Unit = runBlocking {

        val mockResponseBody = Mockito.mock(ResponseBody::class.java)
        val mockResponse = retrofit2.Response.error<TvPageResponse>(500,mockResponseBody)

        BDDMockito.given(
            theMoviesApi.getSimilarTvShows(
                apiKey = BuildConfig.APIKEY,
                language = null,
                page = 1,
                tvId = "123"
            )
        ).willReturn(mockResponse)

        repository.getRelatedTvShows(tvId = "123", language = null, page = 1).collect {
            when (it) {
                is Response.Failure<*> -> {
                    Assert.assertTrue(it.error is GenericException)
                }
            }
        }
    }
}