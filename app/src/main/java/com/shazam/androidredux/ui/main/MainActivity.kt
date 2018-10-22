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

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import com.jakewharton.rxbinding.view.RxView
import com.shazam.androidredux.R
import com.shazam.androidredux.bind
import com.shazam.androidredux.extensions.getDispatcher
import com.shazam.androidredux.redux.DispatcherBinder
import rx.subscriptions.CompositeSubscription

class MainActivity : AppCompatActivity() {

    private val recyclerView by bind<RecyclerView>(R.id.recycler_view)
    private val progressBar by bind<ProgressBar>(R.id.progress_bar)
    private val errorMessage by bind<TextView>(R.id.error_message)
    private val retry by bind<Button>(R.id.retry)
    private val mainActivityViewAdapter = MainActivityViewAdapter()
    private val subscriptions = CompositeSubscription()

    private val mainDispatcher by lazy { getDispatcher(MainDispatcherHolder::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView.adapter = mainActivityViewAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        DispatcherBinder(this.lifecycle, mainDispatcher) { mainViewState ->
            mainActivityViewAdapter.bindResults(mainViewState.chart?.fullChart ?: emptyList())
            progressBar.visibility = if (mainViewState.isLoading) VISIBLE else GONE
            errorMessage.text = mainViewState.error?.message
        }
    }

    override fun onStart() {
        super.onStart()
        subscriptions.add(mainDispatcher.retryClicks(RxView.clicks(retry)))
    }

    override fun onStop() {
        subscriptions.clear()
        super.onStop()
    }

}
