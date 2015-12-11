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

package com.rowland.movies.objects;

/**
 * Created by Rowland on 7/14/2015.
 */
public class ListPopupMenu {

    private String name;
    private int icon;

    public ListPopupMenu(int profilepicRes, String name)
    {
        this.icon = profilepicRes;
        this.name = name;
    }

    public int getProfilePic()
    {
        return this.icon;
    }
    public String getName()
    {
        return this.name;
    }
}
