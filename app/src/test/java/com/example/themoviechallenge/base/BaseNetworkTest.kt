package com.example.themoviechallenge.base

import com.google.gson.GsonBuilder
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.mockito.MockitoAnnotations
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

abstract class BaseNetworkTest: KoinTest {

    protected lateinit var mMockServerInstance: MockWebServer
    private var mShouldStart = false

    companion object {
        val CODE_SUCCESS = 200
        val CODE_ERROR   = 403
    }

    @Before
    open fun setup(){
        MockitoAnnotations.initMocks(this)
        startMockServer(true)

    }

    open fun createMockApi(): Retrofit {
        val retrofit = Retrofit.Builder()
            .baseUrl(mMockServerInstance.url("/"))
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
        return retrofit
    }

    fun getJson(path : String) : String {
        val uri = javaClass.classLoader!!.getResource(path)
        val file = File(uri.path)

        return String(file.readBytes())
    }


    private fun startMockServer(shouldStart:Boolean){
        if (shouldStart){
            mShouldStart = shouldStart
            mMockServerInstance = MockWebServer()
            mMockServerInstance.start()
        }
    }

    fun getMockWebServerUrl() = mMockServerInstance.url("/").toString()

    private fun stopMockServer() {
        if (mShouldStart){
            mMockServerInstance.shutdown()
        }
    }

    @After
    open fun tearDown(){
        //Stop Mock server
        stopMockServer()
        //Stop Koin as well
        stopKoin()
    }

    open fun generateMockResponseTimeout(): MockResponse {
        return  MockResponse().throttleBody(bytesPerPeriod = 1, period = 1, unit = TimeUnit.SECONDS)
    }
}