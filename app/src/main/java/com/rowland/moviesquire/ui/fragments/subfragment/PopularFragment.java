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

package com.rowland.moviesquire.ui.fragments.subfragment;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.activeandroid.query.Select;
import com.rowland.moviesquire.R;
import com.rowland.moviesquire.data.loaders.ModelLoader;
import com.rowland.moviesquire.rest.enums.ESortOrder;
import com.rowland.moviesquire.rest.models.Movie;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Display Popular Movie
 */
public class PopularFragment extends BaseMovieFragment implements LoaderManager.LoaderCallbacks<List<Movie>> {

    // Logging tracker for this class
    private final String LOG_TAG = PopularFragment.class.getSimpleName();

    private static final int POP_MOVIES_LOADER_ID = 0;

    // Default constructor
    public PopularFragment() {
        super();
    }

    // Create fragment with arguments
    public static PopularFragment newInstance(Bundle args) {
        // Create the new fragment instance
        PopularFragment fragmentInstance = (PopularFragment) newInstance(new PopularFragment(), args);
        // Return the new fragment
        return fragmentInstance;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Check if we have arguments
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_popular, container, false);
        // Initialize the ViewPager and TabStripLayout
        ButterKnife.bind(this, rootView);
        // Return the view for this fragment
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Initialize the list
        mMovieList = new ArrayList<>();
        // Initialize the sort order
        mSortOrder = ESortOrder.POPULAR_DESCENDING;
        // Call service if first launch of fragment
        if (isLaunch) {
            startMovieIntentService();
            isLaunch = false;
        }
        // Initialize the Loader
        getActivity().getSupportLoaderManager().initLoader(POP_MOVIES_LOADER_ID, null, this);
    }

    @Override
    public Loader<List<Movie>> onCreateLoader(int id, Bundle args) {
        // Create new loader
        ModelLoader movieLoader = new ModelLoader<>(getActivity(), Movie.class, new Select().from(Movie.class).where("isPopular = ?", true), true);
        // Return new loader
        return movieLoader;
    }

    @Override
    public void onLoadFinished(Loader<List<Movie>> loader, List<Movie> movieList) {
        // Set refreshing off, when done loading
        mSwRefreshLayout.setRefreshing(false);
        // Fill our movies list with data
        mMovieList = movieList;
        // Pass it on to our adapter
        mMovieAdapter.addAll(movieList);
        // Update the Empty View
        updateEmptyView();
    }

    @Override
    public void onLoaderReset(Loader<List<Movie>> loader) {
        // Set refreshing off, when resetting
        mSwRefreshLayout.setRefreshing(false);
        // We reset the loader, nullify old data
        mMovieAdapter.addAll(null);
    }
}