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

import com.shazam.androidredux.model.Chart
import com.shazam.androidredux.model.Track
import org.hamcrest.CoreMatchers.equalTo
import org.junit.Assert.assertThat
import org.junit.Test

class MainReducerTest {


    companion object {
        val an_error = Throwable("something happened")
        val a_chart = Chart(fullChart = listOf(Track("aTrack")))
    }

    @Test
    fun `should return state with loading when is loading`() {
        assertThat(
                givenState = MainState(chart = null, error = null, isLoading = false),
                whenAction = ChartActions.ChartProgress,
                thenState = MainState(chart = null, error = null, isLoading = true)
        )
    }

    @Test
    fun `should return state with error when error provided`() {
        assertThat(
                givenState = MainState(chart = null, error = null, isLoading = false),
                whenAction = ChartActions.ChartResultError(an_error),
                thenState = MainState(chart = null, error = an_error, isLoading = false)
        )
    }

    @Test
    fun `should return state with chart when chart loaded`() {

        assertThat(
                givenState = MainState(chart = null, error = null, isLoading = false),
                whenAction = ChartActions.ChartResultSuccess(a_chart),
                thenState = MainState(chart = a_chart, error = null, isLoading = false)
        )
    }


    private fun assertThat(givenState: MainState, whenAction: ChartActions, thenState: MainState) {
        val actual = MainReducer.reduce(givenState, whenAction)
        assertThat(actual, equalTo(thenState))
    }
}