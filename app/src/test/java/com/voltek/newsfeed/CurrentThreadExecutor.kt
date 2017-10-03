package com.voltek.newsfeed

import android.support.annotation.NonNull
import java.util.concurrent.Executor

/**
 * Create test RxJava schedulers from this executor
 */
class CurrentThreadExecutor : Executor {

    override fun execute(@NonNull runnable: Runnable) {
        runnable.run()
    }
}
