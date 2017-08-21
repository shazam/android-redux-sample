/*
 * Copyright 2017 Shazam Entertainment Limited
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.
 *
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

package com.shazam.androidredux.ui.track

import com.shazam.androidredux.redux.ReduxDipatcher
import com.shazam.androidredux.redux.Store
import rx.android.schedulers.AndroidSchedulers

class TrackDispatcher(private val trackMiddleware: TrackMiddleware,
                      store: Store<TrackState> ) :
        ReduxDipatcher<TrackState, TrackViewState>(
                store,
                AndroidSchedulers.mainThread(),
                ::mapTrackStateToTrackViewState) {


    fun fetchTrack(key: String) {
        val currentState = store.currentState()
        if (currentState.track?.key != key && currentState.loadingTrackKey != key) {
            store.dispatch(trackMiddleware.fetchTrack(key))
        }
    }
}
