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
package com.doctoror.particleswallpaper.data.repository

import android.annotation.TargetApi
import android.content.Context
import android.content.res.Resources
import android.os.Build
import android.util.TypedValue
import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import kotlin.reflect.KFunction

@TargetApi(Build.VERSION_CODES.M)
class SettingsRepositoryImplTest {

    private lateinit var settings: SettingsRepositoryImpl

    @Before
    fun setUp() {
        val fakePrefs = InMemorySharedPreferences()

        val context = mock<Context>(Context::class.java)
        val resources = mock<Resources>(Resources::class.java)
        val theme = mock(Resources.Theme::class.java)
        val typedValue = mock(TypedValue::class.java)
        val typedValueFactory = mock(SettingsRepositoryDefault.TypedValueFactory::class.java)

        `when`(resources.getInteger(anyInt())).thenReturn(1)
        `when`(resources.getDimension(anyInt())).thenReturn(1f)
        `when`(resources.getColor(anyInt(), ArgumentMatchers.eq(theme))).thenReturn(1)
        `when`(resources.getColor(anyInt(), ArgumentMatchers.eq(theme))).thenReturn(1)

        `when`(context.resources).thenReturn(resources)
        `when`(context.theme).thenReturn(theme)
        `when`(context.getSharedPreferences(anyString(), anyInt())).thenReturn(fakePrefs)

        `when`(typedValue.float).thenReturn(1f)
        `when`(typedValueFactory.newTypedValue()).thenReturn(typedValue)

        settings = SettingsRepositoryImpl(context,
                SettingsRepositoryDefault(resources, theme, typedValueFactory))
    }

    private fun <T> assertObserverHasValueCount(o: TestObserver<T>, count: Int) {
        o.assertNoErrors()
        o.assertNotComplete()
        o.assertSubscribed()
        o.assertValueCount(count)
    }

    private fun <T> assertObservableHasValueCount(o: Observable<T>, valueCount: Int): TestObserver<T> {
        val observer = TestObserver.create<T>()
        o.subscribe(observer)
        assertObserverHasValueCount(observer, valueCount)

        return observer
    }

    private fun <T> assertObservableHasValue(o: Observable<T>, value: T) {
        val testObserver = assertObservableHasValueCount(o, 1)
        testObserver.assertValue(value)
    }

    private fun <T> testPreferenceAccessor(accessor: KFunction<Observable<T>>) {
        assertObservableHasValueCount(accessor.call(), 1)
    }

    private fun <T> testPreferenceMutator(
            accessor: KFunction<Observable<T>>,
            mutator: KFunction<Unit>,
            testValue: T) {
        mutator.call(testValue)
        assertObservableHasValue(accessor.call(), testValue)
    }

    private fun <T> testPreferenceNotifiesChanges(
            accessor: KFunction<Observable<T>>,
            mutator: KFunction<Unit>,
            testValue: T) {
        val observer = TestObserver.create<T>()
        accessor.call().subscribe(observer)

        assertObserverHasValueCount(observer, 1)

        mutator.call(testValue)

        assertObserverHasValueCount(observer, 2)
        observer.assertValueAt(1, { v -> v == testValue })
    }

    @Test
    fun testGetParticlesColor() {
        testPreferenceAccessor(settings::getParticlesColor)
    }

    @Test
    fun testSetParticlesColor() {
        testPreferenceMutator(
                settings::getParticlesColor,
                settings::setParticlesColor,
                0xff000000.toInt())
    }

    @Test
    fun testParticlesColorNotifiesChanges() {
        testPreferenceNotifiesChanges(
                settings::getParticlesColor,
                settings::setParticlesColor,
                0xff000001.toInt()
        )
    }

    @Test
    fun testGetBackgroundColor() {
        testPreferenceAccessor(settings::getBackgroundColor)
    }

    @Test
    fun testSetBackgroundColor() {
        testPreferenceMutator(
                settings::getBackgroundColor,
                settings::setBackgroundColor,
                0xff000002.toInt())
    }

    @Test
    fun testBackgroundColorNotifiesChanges() {
        testPreferenceNotifiesChanges(
                settings::getBackgroundColor,
                settings::setBackgroundColor,
                0xff000003.toInt()
        )
    }

    @Test
    fun testGetBackgroundUri() {
        testPreferenceAccessor(settings::getBackgroundUri)
    }

    @Test
    fun testSetBackgroundUri() {
        testPreferenceMutator(
                settings::getBackgroundUri,
                settings::setBackgroundUri,
                "uri://a")
    }

    @Test
    fun testBackgroundUriNotifiesChanges() {
        testPreferenceNotifiesChanges(
                settings::getBackgroundUri,
                settings::setBackgroundUri,
                "uri://b")
    }

    @Test
    fun testGetNumDots() {
        testPreferenceAccessor(settings::getNumDots)
    }

    @Test
    fun testSetNumDots() {
        testPreferenceMutator(
                settings::getNumDots,
                settings::setNumDots,
                1)
    }

    @Test
    fun testNumDotsNotifiesChanges() {
        testPreferenceNotifiesChanges(
                settings::getNumDots,
                settings::setNumDots,
                2)
    }

    @Test
    fun testGetFrameDelay() {
        testPreferenceAccessor(settings::getFrameDelay)
    }

    @Test
    fun testSetFrameDelay() {
        testPreferenceMutator(
                settings::getFrameDelay,
                settings::setFrameDelay,
                3)
    }

    @Test
    fun testFrameDelayNotifiesChanges() {
        testPreferenceNotifiesChanges(
                settings::getFrameDelay,
                settings::setFrameDelay,
                4)
    }

    @Test
    fun testGetStepMultiplier() {
        testPreferenceAccessor(settings::getStepMultiplier)
    }

    @Test
    fun testSetStepMultiplier() {
        testPreferenceMutator(
                settings::getStepMultiplier,
                settings::setStepMultiplier,
                0.1f)
    }

    @Test
    fun testStepMultiplierNotifiesChanges() {
        testPreferenceNotifiesChanges(
                settings::getStepMultiplier,
                settings::setStepMultiplier,
                0.2f)
    }

    @Test
    fun testGetDotScale() {
        testPreferenceAccessor(settings::getDotScale)
    }

    @Test
    fun testSetDotScale() {
        testPreferenceMutator(
                settings::getDotScale,
                settings::setDotScale,
                0.3f)
    }

    @Test
    fun testDotScaleNotifiesChanges() {
        testPreferenceNotifiesChanges(
                settings::getDotScale,
                settings::setDotScale,
                0.4f)
    }

    @Test
    fun testGetLineScale() {
        testPreferenceAccessor(settings::getLineScale)
    }

    @Test
    fun testSetLineScale() {
        testPreferenceMutator(
                settings::getLineScale,
                settings::setLineScale,
                0.5f)
    }

    @Test
    fun testLineScaleNotifiesChanges() {
        testPreferenceNotifiesChanges(
                settings::getLineScale,
                settings::setLineScale,
                0.6f)
    }

    @Test
    fun testGetLineDistance() {
        testPreferenceAccessor(settings::getLineDistance)
    }

    @Test
    fun testSetLineDistance() {
        testPreferenceMutator(
                settings::getLineDistance,
                settings::setLineDistance,
                0.7f)
    }

    @Test
    fun testLineDistanceNotifiesChanges() {
        testPreferenceNotifiesChanges(
                settings::getLineDistance,
                settings::setLineDistance,
                0.8f)
    }
}