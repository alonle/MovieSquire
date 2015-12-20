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

package com.rowland.movies.asynctaskloaders;

import android.content.Context;
import android.text.TextUtils;

import com.rowland.movies.ApplicationController;
import com.rowland.movies.BuildConfig;
import com.rowland.movies.rest.collections.TrailersCollection;
import com.rowland.movies.rest.enums.EAPITypes;
import com.rowland.movies.rest.pojos.Trailers;
import com.rowland.movies.rest.services.IMoviesAPIService;
import com.rowland.movies.rest.services.IRetrofitAPI;
import com.uwetrottmann.androidutils.GenericSimpleLoader;

import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by Oti Rowland on 12/12/2015.
 */
public class TrailersLoader extends GenericSimpleLoader {
    // The class Log identifier
    private static final String LOG_TAG = TrailersLoader.class.getSimpleName();
    // The movie id whose trailers are retrieved
    private int mTmdbMovieId;
    // The list of movies our loader returns
    private List<Trailers> trailers;

    public TrailersLoader(Context context, int mTmdbMovieId) {
        super(context);
        this.mTmdbMovieId = mTmdbMovieId;
    }

    @Override
    public List<Trailers> loadInBackground() {
        // Get the RetrofitApi with correct Endpoint
        IRetrofitAPI moviesAPI = ApplicationController.getApplicationInstance().getApiOfType(EAPITypes.MOVIES_API);
        // Get the MoviesAPIService
        IMoviesAPIService movieService = moviesAPI.getMoviesApiServiceInstance();
        // Retrieve the trailers data
        Call<TrailersCollection> createdCall = movieService.loadTrailersData(mTmdbMovieId, BuildConfig.IMDB_API_KEY);

        // Asynchronously access
        createdCall.enqueue(new Callback<TrailersCollection>() {
            @Override
            public void onResponse(Response<TrailersCollection> response, Retrofit retrofit) {
                trailers = response.body().results;

                for (Trailers trailer : trailers) {
                    // Save revies in the database
                    trailer.save();
                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });

        if (trailers.size() != 0) {
            return trailers;
        }

        /*try {
            Response<TrailersCollection> result = createdCall.execute();
            return result.body().items;
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(LOG_TAG, "IOException during loadInBackground()");
        }*/
        return null;
    }

    // Extract the individual movie trailers
    // Handy method, might help in future
    private Trailers extractTrailer(TrailersCollection videos) {
        // If no trailer videos are found return
        if (videos == null || videos.results == null || videos.results.size() == 0) {
            return null;
        }

        // Pop out the lead YouTube trailer
        for (Trailers video : videos.results) {
            if ("Trailer".equals(video.getType()) && "YouTube".equals(video.getSite()) && !TextUtils.isEmpty(video.getKey())) {
                return video;
            }
        }
        return null;

    }
}
