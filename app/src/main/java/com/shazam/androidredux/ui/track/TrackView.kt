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

import android.arch.lifecycle.ViewModelProviders
import android.view.View
import android.widget.TextView
import com.shazam.androidredux.R
import com.shazam.androidredux.bind
import com.shazam.androidredux.extensions.findActivity
import com.shazam.androidredux.extensions.plusAssign

class TrackView : android.support.constraint.ConstraintLayout {
    constructor(context: android.content.Context) : super(context)

    constructor(context: android.content.Context, attrs: android.util.AttributeSet) : super(context, attrs)

    constructor(context: android.content.Context, attrs: android.util.AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private val keyView by bind<TextView>(R.id.key)
    private val title by bind<TextView>(R.id.title)
    private val progress by bind<View>(R.id.progress)

    private lateinit var trackDispatcher: TrackDispatcher
    private var trackKey: String? = null

    private val subscriptions = rx.subscriptions.CompositeSubscription()

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        subscriptions += trackDispatcher.subscribe {
            keyView.text = it.key
            title.text = it.title
            progress.visibility = if (it.isLoading) View.VISIBLE else View.GONE
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        subscriptions.clear()
    }

    fun bindTo(key: String) {
        this.trackKey = key
        subscriptions.clear()

        trackDispatcher = ViewModelProviders
                .of(this.context.findActivity())
                .get(key, TrackDispatcherHolder::class.java)
                .dispatcher
        trackDispatcher.fetchTrack(key)
    }
}
