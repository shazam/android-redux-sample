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

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import com.shazam.androidredux.R
import com.shazam.androidredux.model.Track
import com.shazam.androidredux.ui.main.MainActivityViewAdapter.ViewHolder
import com.shazam.androidredux.ui.track.TrackView

class MainActivityViewAdapter : RecyclerView.Adapter<ViewHolder>() {

    private var tracks: MutableList<Track> = ArrayList()

    fun bindResults(tracks: List<Track>) {
        this.tracks.clear()
        this.tracks.addAll(tracks)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val track = tracks[position]
         holder.trackView.bindTo(track.key)
    }

    override fun onCreateViewHolder(parent: android.view.ViewGroup, viewType: Int): ViewHolder =
            ViewHolder(LayoutInflater.from(parent.context)
                    .inflate(R.layout.view_track, parent, false))

    override fun getItemCount(): Int = tracks.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val trackView = view as TrackView
    }
}
