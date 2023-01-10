/*
 * Copyright (c) 2023 Matthew Nelson
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 **/
package io.matthewnelson.component.value.clazz

import kotlin.jvm.JvmInline
import kotlin.test.*

class ValueClazzUnitTest {

    @JvmInline
    value class KotlinValueClass(val value: Any?)

    class TestValueClazz(val value: Any?): ValueClazz(value)

    data class TestDataValueClazz(val value: Any?, val int: Int): ValueClazz(value)

    object TestObject: ValueClazz(NoValue())

    @Test
    fun givenAnonymousValueClazz_whenInstantiated_thenThrowsException() {
        try {
            object : ValueClazz(Unit) {}
            fail()
        } catch (_: IllegalStateException) {
            // pass
        }
    }

    @Test
    fun givenPrimitiveInput_whenCompared_thenSuccess() {
        assertCompare(5)
    }

    @Test
    fun givenArray_whenCompared_thenSuccess() {
        assertCompare(ByteArray(5) { 9 })
    }

    @Test
    fun givenCollection_whenCompared_thenSuccess() {
        assertCompare(listOf("matthew", "nelson"))
    }

    @Test
    fun givenNoValue_whenToString_thenOutputIsClassNameOnly() {
        assertEquals("TestObject", TestObject.toString())
        assertEquals("TestValueClazz", TestValueClazz(NoValue()).toString())
    }

    @Throws(AssertionError::class)
    private fun assertCompare(input: Any?) {
        val data = TestDataValueClazz(input, Int.MAX_VALUE)
        val kotlin = KotlinValueClass(input)
        val lib = TestValueClazz(input)

        println(data)
        println(kotlin)
        println(lib)

        // equals
        assertFalse(lib.equals(data))
        assertFalse(lib.equals(kotlin))

        // hashCode
        assertEquals(input.hashCode(), data.hashCode())
        assertEquals(data.hashCode(), kotlin.hashCode())
        assertEquals(kotlin.hashCode(), lib.hashCode())
    }
}
