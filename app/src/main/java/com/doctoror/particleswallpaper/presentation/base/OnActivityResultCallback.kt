/*
 * Copyright (C) 2017 Yaroslav Mytkalyk
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.doctoror.particleswallpaper.presentation.base

import android.content.Intent

/**
 * Created by Yaroslav Mytkalyk on 31.05.17.
 *
 * onActivityResult() callback.
 *
 * This class overloads [equals] and [hashCode] to compare references instead of equality, which
 * allows, for example, to compare them in "contains" and "remove" methods of Collections.
 */
abstract class OnActivityResultCallback {

    abstract fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)

    override operator fun equals(other: Any?) = this === other
    override fun hashCode() = 0
}