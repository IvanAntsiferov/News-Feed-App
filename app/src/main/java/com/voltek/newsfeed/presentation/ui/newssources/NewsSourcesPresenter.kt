package com.voltek.newsfeed.presentation.ui.newssources

import com.arellomobile.mvp.InjectViewState
import com.voltek.newsfeed.analytics.Analytics
import com.voltek.newsfeed.domain.usecase.Parameter
import com.voltek.newsfeed.domain.usecase.newssources.EnableNewsSourceUseCase
import com.voltek.newsfeed.domain.usecase.newssources.NewsSourcesUseCase
import com.voltek.newsfeed.presentation.base.BasePresenter
import com.voltek.newsfeed.presentation.base.Event
import com.voltek.newsfeed.presentation.entity.SourceUI
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer

@InjectViewState
class NewsSourcesPresenter(
        private val newsSources: NewsSourcesUseCase,
        private val newsSourceEnable: EnableNewsSourceUseCase,
        private val analytics: Analytics
) : BasePresenter<NewsSourcesView>() {

    private val model: NewsSourcesModel =
            NewsSourcesModel { viewState.render(it as NewsSourcesModel) }

    init {
        bind(arrayOf(newsSources, newsSourceEnable))
    }

    override fun onFirstViewAttach() {
        loadNewsSources(NewsSourcesUseCase.GET)
    }

    override fun event(event: Event) {
        when (event) {
            is Event.FilterSources -> {
                analytics.newsSourcesFilter(event.filter)
                model.categoryId = event.id
                loadNewsSources(event.filter)
            }
            is Event.Refresh -> {
                model.resetId()
                loadNewsSources(NewsSourcesUseCase.REFRESH)
            }
            is Event.EnableNewsSource -> {
                val source = event.source
                with(source) {
                    when {
                        isEnabled -> analytics.newsSourceDisable(name, url, category)
                        else -> analytics.newsSourceEnable(name, url, category)
                    }
                }
                enableNewsSource(source)
            }
        }
    }

    private fun enableNewsSource(source: SourceUI) {
        newsSourceEnable.execute(
                Parameter(item = source),
                Consumer {},
                Consumer {
                    model.message = it.message ?: ""
                    model.update()
                },
                Action {
                    model.sources.firstOrNull {
                        it.id == source.id
                    }?.isEnabled = !source.isEnabled
                }
        )
    }

    private fun loadNewsSources(filter: String) {
        with(model) {
            sources.clear()
            loading = true
            message = ""
            update()
        }

        newsSources.execute(
                Parameter(filter),
                Consumer {
                    model.sources = ArrayList(it.data ?: ArrayList())
                    model.message = it.message
                },
                Consumer {
                    model.message = it.message ?: ""
                    finishLoading()
                },
                Action {
                    finishLoading()
                }
        )
    }

    private fun finishLoading() {
        model.loading = false
        model.update()
    }
}
