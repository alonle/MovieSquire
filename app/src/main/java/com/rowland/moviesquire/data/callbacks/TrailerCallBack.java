/*
 * Copyright 2015 Oti Rowland
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
 *
 */

package com.rowland.moviesquire.data.callbacks;

import android.content.Context;
import android.util.Log;

import com.rowland.moviesquire.BuildConfig;
import com.rowland.moviesquire.data.repository.TrailerRepository;
import com.rowland.moviesquire.rest.collections.TrailerCollection;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by Oti Rowland on 12/21/2015.
 */
public class TrailerCallBack implements Callback<TrailerCollection> {

    // The class Log identifier
    private static final String LOG_TAG = TrailerCallBack.class.getSimpleName();
    // Context instance
    private Context context;

    public TrailerCallBack(Context context) {
        this.context = context;
    }

    @Override
    public void onResponse(Response<TrailerCollection> response, Retrofit retrofit) {

        // Check status of response before proceeding
        if (response.isSuccess()) {
            // Collection available
            TrailerCollection trailerCollection = response.body();
            // TrailerRepository instance
            TrailerRepository mTrailerRepository = new TrailerRepository();
            // Save movies to data storage
            mTrailerRepository.saveAll(trailerCollection);
        } else {
            // Check whether we are in debugging mode
            if (BuildConfig.IS_DEBUG_MODE) {
                // we got an error message - Do error handling here
                Log.d(LOG_TAG, response.errorBody().toString());
            }

        }
    }

    @Override
    public void onFailure(Throwable t) {
        // Inform user of failure due to no network e.t.c
        Log.d(LOG_TAG, t.getMessage());
    }
}
