/*
 * Copyright 2017 Shazam Entertainment Limited
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.
 *
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

package com.shazam.androidredux.ui.main

import com.shazam.androidredux.redux.ReduxDipatcher
import com.shazam.androidredux.redux.Store
import rx.Observable
import rx.Scheduler
import rx.Subscription

class MainDispatcher(private val chartMiddleware: () -> Observable<ChartActions>,
                     simpleStore: Store<MainState>,
                     scheduler: Scheduler)
    : ReduxDipatcher<MainState, MainViewState>(
        simpleStore,
        scheduler,
        ::mapChartStateToMainViewState) {

    init {
        simpleStore.dispatch(chartMiddleware())
    }

    fun retryClicks(clicks: Observable<Void>): Subscription = clicks
            .filter { !store.currentState().isLoading } //Proof of concept on how avoid multiple calls when RETRY clicked repetely.
            .subscribe { store.dispatch(chartMiddleware()) }
}
