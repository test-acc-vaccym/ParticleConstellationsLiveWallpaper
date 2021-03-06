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
package com.doctoror.particleswallpaper.presentation.preference

import com.doctoror.particleswallpaper.data.execution.ComputationSchedulers
import com.doctoror.particleswallpaper.data.repository.MockSettingsRepositoryFactory
import com.doctoror.particleswallpaper.presentation.presenter.*
import com.doctoror.particleswallpaper.presentation.view.FakeSeekBarPreferenceView
import org.junit.Assert.*
import org.junit.Test

/**
 * Created by Yaroslav Mytkalyk on 30.05.17.
 *
 * Tests [MapperSeekBarPresenter] implementations
 */
class SeekBarSettingsPreferencesTest {

    @Test
    fun testDotScalePreference() {
        testMapper(
                DotScalePreferencePresenter(
                        ComputationSchedulers(),
                        MockSettingsRepositoryFactory.createMutable()))
    }

    @Test
    fun testFrameDelayPreference() {
        val p = FrameDelayPreferencePresenter(ComputationSchedulers(),
                MockSettingsRepositoryFactory.createMutable())
        p.onTakeView(FakeSeekBarPreferenceView())
        testMapper(p)
    }

    @Test
    fun testFrameDelayPreferenceMinValue() {
        val p = FrameDelayPreferencePresenter(ComputationSchedulers(),
                MockSettingsRepositoryFactory.createMutable())
        p.onTakeView(FakeSeekBarPreferenceView())
        assertEquals(p.frameDelaySeekbarMin, p.transformToRealValue(p.getSeekbarMax()))
    }

    @Test
    fun testLineDistancePreference() {
        testMapper(LineDistancePreferencePresenter(ComputationSchedulers(),
                MockSettingsRepositoryFactory.createMutable()))
    }

    @Test
    fun testLineScalePreference() {
        testMapper(LineScalePreferencePresenter(ComputationSchedulers(),
                MockSettingsRepositoryFactory.createMutable()))
    }

    @Test
    fun testNumDotsPreference() {
        testMapper(NumDotsPreferencePresenter(ComputationSchedulers(),
                MockSettingsRepositoryFactory.createMutable()))
    }

    @Test
    fun testSpeedFactorPreferencePresenter() {
        testMapper(SpeedFactorPreferencePresenter(ComputationSchedulers(),
                MockSettingsRepositoryFactory.createMutable()))
    }

    private fun <T> testMapper(p: MapperSeekBarPresenter<T>) {
        testMapperMinValue(p)
        testMapperMaxValue(p)
    }

    private fun <T> testMapperMinValue(p: MapperSeekBarPresenter<T>) {
        val seekBarValue = 0
        val frameDelay = p.transformToRealValue(seekBarValue)
        assertEquals(seekBarValue, p.transformToProgress(frameDelay))
    }

    private fun <T> testMapperMaxValue(p: MapperSeekBarPresenter<T>) {
        val seekBarValue = p.getSeekbarMax()
        val frameDelay = p.transformToRealValue(seekBarValue)
        assertEquals(seekBarValue, p.transformToProgress(frameDelay))
    }
}