/*
 * Copyright (c) 2019 Razeware LLC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * Notwithstanding the foregoing, you may not use, copy, modify, merge, publish,
 * distribute, sublicense, create a derivative work, and/or sell copies of the
 * Software in any work that is designed, intended, or marketed for pedagogical or
 * instructional purposes related to programming, coding, application development,
 * or information technology.  Permission for such use, copying, modification,
 * merger, publication, distribution, sublicensing, creation of derivative works,
 * or sale is expressly withheld.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.raywenderlich.android.triplog

import android.content.Context
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.raywenderlich.android.triplog.model.TripLog
import com.raywenderlich.android.triplog.utils.CoordinatesFormatter
import com.raywenderlich.android.triplog.utils.DateFormatter
import kotlinx.android.synthetic.main.view_log_item.view.*


class TripLogAdapter(context: Context,
                     private val logs: List<TripLog>,
                     private val dateFormatter: DateFormatter,
                     private val coordinatesFormatter: CoordinatesFormatter
) : RecyclerView.Adapter<TripLogAdapter.TripLogViewHolder>() {

  private val happyBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.bg_basic_happy_big)
  private val sadBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.bg_basic_sad_big)

  var listener: Listener? = null

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TripLogViewHolder {
    val inflater = LayoutInflater.from(parent.context)
    val itemView = inflater.inflate(R.layout.view_log_item, parent, false)
    return TripLogViewHolder(itemView)
  }

  override fun getItemCount() = logs.size

  override fun onBindViewHolder(holder: TripLogViewHolder, position: Int) {
    val tripLog = logs[position]
    holder.bind(tripLog)
  }

  inner class TripLogViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val textViewLog = itemView.textViewLog
    private val textViewDate = itemView.textViewDate
    private val textViewLocation = itemView.textViewLocation
    private val imageView = itemView.imageView

    fun bind(tripLog: TripLog) {
      imageView.setImageBitmap(if (tripLog.happyMood) happyBitmap else sadBitmap)

      textViewLog.text = tripLog.log
      textViewDate.text = dateFormatter.format(tripLog.date)
      textViewLocation.text = coordinatesFormatter.format(tripLog.coordinates)
      itemView.setOnClickListener {
        listener?.showDetailLog(tripLog)
      }
    }

  }

  interface Listener {
    fun showDetailLog(tripLog: TripLog)
  }

}