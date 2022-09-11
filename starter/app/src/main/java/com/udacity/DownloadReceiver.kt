/*
 * Copyright (C) 2019 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.udacity

import android.app.DownloadManager
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.DOWNLOAD_SERVICE
import android.content.Intent
import android.util.Log
import androidx.core.content.ContextCompat

class DownloadReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        var status = false
        var title = ""

        val downloadId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, 0)

        val downloadManager: DownloadManager =
            context.getSystemService(DOWNLOAD_SERVICE) as DownloadManager

        val query = DownloadManager.Query();
        query.setFilterById(downloadId);

        val cursor = downloadManager.query(query)

        if (cursor.moveToFirst()) {
            if (cursor.columnCount > 0) {

                status = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))
                    .equals("200")
                title = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_TITLE))
                /* Log.d("cursor status : ", cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)))
                 Log.d("cursor title : ", title)
                 Log.d(
                     "cursor reason : ",
                     cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_REASON))
                 )
                 Log.d(
                     "cursor url : ",
                     cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_URI))
                 )
 */
            }
        }

        cursor.close();

        // TODO:  sendNotification
        val manager = ContextCompat.getSystemService(
            context,
            NotificationManager::class.java
        ) as NotificationManager

        manager.sendNotification(
            title,
            status,
            context
        )

    }

}