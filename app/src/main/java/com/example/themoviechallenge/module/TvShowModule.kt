package com.example.themoviechallenge.module

import com.example.themoviechallenge.data.repository.TheMoviesRepository
import com.example.themoviechallenge.data.repository.network.api.TheMoviesApi
import com.example.themoviechallenge.domain.GetRelatedTvShowUseCase
import com.example.themoviechallenge.domain.GetTvShowUseCase
import com.example.themoviechallenge.domain.repository.IGetRelatedTvShowRepository
import com.example.themoviechallenge.domain.repository.IGetTvShowRepository
import com.example.themoviechallenge.presenter.viewmodel.TvShowViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val tvShowModule = module {

    //viewmodel
    viewModel { TvShowViewModel(get(), get()) }

    //usecases
    single { provideGetRelatedTvShowUseCase(get()) }
    single { provideGetTvShowUseCase(get()) }

    //repositories
    single<IGetRelatedTvShowRepository> { TheMoviesRepository(get()) }
    single<IGetTvShowRepository> { TheMoviesRepository(get()) }

    //api
    single { provideTheMoviesApi(get()) }

    //analytics

}

private fun provideGetRelatedTvShowUseCase(repository: IGetRelatedTvShowRepository): GetRelatedTvShowUseCase {
    return GetRelatedTvShowUseCase(repository)
}

private fun provideGetTvShowUseCase(repository: IGetTvShowRepository): GetTvShowUseCase {
    return GetTvShowUseCase(repository)
}

private fun provideTheMoviesApi(retrofit: Retrofit): TheMoviesApi {
    return retrofit.create(TheMoviesApi::class.java)
}