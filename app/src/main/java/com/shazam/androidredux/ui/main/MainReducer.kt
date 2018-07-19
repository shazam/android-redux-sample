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


import com.shazam.androidredux.redux.Action
import com.shazam.androidredux.redux.Reducer

object MainReducer : Reducer<MainState> {

    override fun reduce(oldState: MainState, action: Action): MainState {
        return when (action) {
            is ChartActions.ChartProgress -> oldState.copy(isLoading = true, chart = null)
            is ChartActions.ChartResultError -> oldState.copy(error = action.error, isLoading = false)
            is ChartActions.ChartResultSuccess -> oldState.copy(chart = action.chart, isLoading = false)
            else -> oldState
        }
    }
}
