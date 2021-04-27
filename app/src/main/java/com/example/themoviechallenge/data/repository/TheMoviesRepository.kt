package com.example.themoviechallenge.data.repository

import com.example.themoviechallenge.BuildConfig
import com.example.themoviechallenge.data.repository.network.api.TheMoviesApi
import com.example.themoviechallenge.domain.model.mapper.TvPageResponseMapper
import com.example.themoviechallenge.domain.model.TvShow
import com.example.themoviechallenge.domain.repository.IGetRelatedTvShowRepository
import com.example.themoviechallenge.domain.repository.IGetTvShowRepository
import com.highquality.base.GenericException
import com.highquality.base.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class TheMoviesRepository(private val theMoviesApi: TheMoviesApi) : IGetTvShowRepository,
    IGetRelatedTvShowRepository {

    @Throws(Exception::class)
    override suspend fun getTvShows(
        language: String?,
        page: Int
    ): Flow<Response<List<TvShow>>> = flow {

        val response = theMoviesApi.getPopularTvShows(
            apiKey = BuildConfig.APIKEY,
            language = language,
            page = page
        )

        when (response.code()) {
            200 -> {
                val mapped = TvPageResponseMapper.toTvShow(response.body()!!)
                emit(Response.Success(mapped))
            }
            401 -> {
                emit(
                    Response.Failure(
                        GenericException(
                            statusCode = 401,
                            statusMessage = "Credentials Error"
                        )
                    )
                )
            }
            404 -> {
                emit(
                    Response.Failure(
                        GenericException(
                            statusCode = 401,
                            statusMessage = "Unknow endpoint"
                        )
                    )
                )
            }
            else -> {
                emit(
                    Response.Failure(
                        GenericException(
                            statusCode = 999,
                            statusMessage = "Something unexpected happened"
                        )
                    )
                )
            }
        }

    }.catch {
        it.printStackTrace()
        emit(
            Response.Failure(
                GenericException(
                    statusCode = 999,
                    statusMessage = "Something unexpected happened"
                )
            )
        )
    }

    override suspend fun getRelatedTvShows(
        tvId: String,
        language: String?,
        page: Int
    ): Flow<Response<List<TvShow>>> = flow {

        val response = theMoviesApi.getSimilarTvShows(
            tvId = tvId,
            apiKey = BuildConfig.APIKEY,
            language = language,
            page = page
        )

        when (response.code()) {
            200 -> {
                val mapped = TvPageResponseMapper.toTvShow(response.body()!!)
                emit(Response.Success(mapped))
            }
            401 -> {
                emit(
                    Response.Failure(
                        GenericException(
                            statusCode = 401,
                            statusMessage = "Credentials Error"
                        )
                    )
                )
            }
            404 -> {
                emit(
                    Response.Failure(
                        GenericException(
                            statusCode = 401,
                            statusMessage = "Unknow endpoint"
                        )
                    )
                )
            }
            else -> {
                emit(
                    Response.Failure(
                        GenericException(
                            statusCode = 999,
                            statusMessage = "Something unexpected happened"
                        )
                    )
                )
            }
        }

    }.catch {
        it.printStackTrace()
        emit(
            Response.Failure(
                GenericException(
                    statusCode = 999,
                    statusMessage = "Something unexpected happened"
                )
            )
        )
    }

}