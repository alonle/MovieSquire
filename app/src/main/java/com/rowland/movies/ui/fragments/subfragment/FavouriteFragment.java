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

package com.rowland.movies.ui.fragments.subfragment;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rowland.movies.R;
import com.rowland.movies.rest.models.Movies;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Display Favourite Movies
 */
public class FavouriteFragment extends BaseGridFragment implements LoaderManager.LoaderCallbacks<List<Movies>> {

    // Logging tracker for this class
    private final String LOG_TAG = FavouriteFragment.class.getSimpleName();

    public FavouriteFragment() {

    }

    public static FavouriteFragment newInstance(Bundle args) {
        // Create the new fragment instance
        FavouriteFragment fragmentInstance = (FavouriteFragment) newInstance(new FavouriteFragment(), args);
        // Return the new fragment
        return fragmentInstance;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_favourite, container, false);
        // Initialize the Views
        ButterKnife.bind(this, rootView);
        // Return the view for this fragment
        return rootView;
    }


    @Override
    public Loader<List<Movies>> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<List<Movies>> loader, List<Movies> data) {

    }

    @Override
    public void onLoaderReset(Loader<List<Movies>> loader) {

    }

    // When RefreshLayout is triggered reload the loader
    @Override
    public void onRefresh() {
        getLoaderManager().restartLoader(0, null, this);
    }
}
