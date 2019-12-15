package com.rmalan.app.footballleague.presenter

import com.google.gson.Gson
import com.rmalan.app.footballleague.api.ApiRepository
import com.rmalan.app.footballleague.model.LeagueDetail
import com.rmalan.app.footballleague.model.LeagueDetailResponse
import com.rmalan.app.footballleague.util.CoroutineContextProviderTest
import com.rmalan.app.footballleague.view.LeagueDetailView
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class LeagueDetailPresenterTest {

    @Mock
    private lateinit var view: LeagueDetailView
    @Mock
    private lateinit var gson: Gson
    @Mock
    private lateinit var apiRepository: ApiRepository
    @Mock
    private lateinit var apiResponse: Deferred<String>

    private lateinit var presenter: LeagueDetailPresenter

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        presenter = LeagueDetailPresenter(view, apiRepository, gson, CoroutineContextProviderTest())
    }

    @Test
    fun getLeagueDetail() {
        val leagueDetail: MutableList<LeagueDetail> = mutableListOf()
        val response = LeagueDetailResponse(leagueDetail)
        val leagueId = "4328"

        runBlocking {
            Mockito.`when`(apiRepository.doRequest(ArgumentMatchers.anyString()))
                .thenReturn(apiResponse)

            Mockito.`when`(apiResponse.await()).thenReturn("")

            Mockito.`when`(
                gson.fromJson(
                    "",
                    LeagueDetailResponse::class.java
                )
            ).thenReturn(response)

            presenter.getLeagueDetail(leagueId)

            Mockito.verify(view).showLoading()
            Mockito.verify(view).showLeagueDetail(leagueDetail)
            Mockito.verify(view).hideLoading()
        }
    }
}