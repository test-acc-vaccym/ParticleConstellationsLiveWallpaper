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
package com.doctoror.particleswallpaper.presentation.presenter

import android.support.annotation.VisibleForTesting

/**
 * Created by Yaroslav Mytkalyk on 03.06.17.
 *
 * Presenter that maps between SeekBar progress values and raw values
 */
interface MapperSeekBarPresenter<T> {

    @VisibleForTesting(otherwise = VisibleForTesting.PROTECTED)
    fun getSeekbarMax(): Int

    @VisibleForTesting(otherwise = VisibleForTesting.PROTECTED)
    fun transformToRealValue(progress: Int): T

    @VisibleForTesting(otherwise = VisibleForTesting.PROTECTED)
    fun transformToProgress(value : T) : Int

}