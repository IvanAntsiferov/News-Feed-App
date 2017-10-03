package com.voltek.newsfeed.domain.use_case

import com.voltek.newsfeed.CurrentThreadExecutor
import io.reactivex.schedulers.Schedulers

abstract class BaseUseCaseTest {

    protected val scheduler = Schedulers.from(CurrentThreadExecutor())
}
