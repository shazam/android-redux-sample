/*
 * Copyright 2017 Shazam Entertainment Limited
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.
 *
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

package com.shazam.androidredux.redux

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import rx.Subscription

class DispatcherBinder<S : State, VS : ViewState>(lifecycle: Lifecycle,
                                                  private val reduxDipatcher: ReduxDipatcher<S, VS>,
                                                  private val onNext: (VS) -> Unit
) : LifecycleObserver {

    private var subscription: Subscription? = null

    init {
        lifecycle.addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart() {
        subscription = reduxDipatcher.subscribe(onNext)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop() {
        subscription?.let {
            if (!it.isUnsubscribed) {
                it.unsubscribe()
            }
        }
    }
}
