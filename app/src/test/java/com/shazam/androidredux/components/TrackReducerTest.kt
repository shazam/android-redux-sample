/*
 * Copyright 2017 Shazam Entertainment Limited
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.
 *
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

package com.shazam.androidredux.components

import com.shazam.androidredux.R.id.key
import com.shazam.androidredux.model.Chart
import com.shazam.androidredux.model.Track
import com.shazam.androidredux.ui.main.ChartActions
import com.shazam.androidredux.ui.main.MainReducer
import com.shazam.androidredux.ui.main.MainState
import com.shazam.androidredux.ui.track.*
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.*
import org.junit.Assert
import org.junit.Assert.assertThat
import org.junit.Test

class TrackReducerTest {

    companion object {
        val an_error = Throwable("something happened")
        val a_track = Track("aTrack")
    }

    @Test
    fun `should return state with loading when is loading`() {

        val key = "a_key"

        assertThat(
                givenState = TrackState(isLoading = false),
                whenAction = TrackActions.TrackLoadingAction(key),
                thenState = TrackState(isLoading = true, loadingTrackKey = key, track = null, error = null)
        )
    }

    @Test
    fun `should return state with error when error provided`() {

        assertThat(
                givenState = TrackState(error = null),
                whenAction = TrackActions.TrackErrorAction(an_error),
                thenState = TrackState(error = an_error, track = null, isLoading = false)
        )

    }

    @Test
    fun `should return state with track when track loaded`() {

        assertThat(
                givenState = TrackState(track = null),
                whenAction = TrackActions.TrackResultAction(a_track),
                thenState = TrackState(track = a_track, error = null, isLoading = false)
        )
    }

    private fun assertThat(givenState: TrackState, whenAction: TrackActions, thenState: TrackState) {
        val actual = TrackReducer.reduce(givenState, whenAction)
        assertThat(actual, equalTo(thenState))
    }
}
