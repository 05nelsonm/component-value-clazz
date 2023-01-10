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

/**
 * Functionally equivalent to a Kotlin `value class` that implements
 * an `interface`, yet can compile to and is usable from other languages.
 *
 * e.g.
 *
 *     class UserId(val id: String): ValueClazz(id)
 *
 *     val userId = UserId("5")
 *     assertEquals("5".hashCode(), userId.hashCode())
 *     assertFalse(userId.equals("5"))
 *
 * @see [NoValue]
 * @throws [IllegalStateException] when instantiating as an anonymous object (e.g. object: ValueClass(4) {})
 * */
public abstract class ValueClazz

@Throws(IllegalStateException::class)
public constructor(private val value: Any?) {

    init {
        check(this::class.simpleName != null) {
            "Anonymous instances of ValueClazz are prohibited"
        }
    }

    final override fun equals(other: Any?): Boolean {
        if (other === this) return true
        if (other == null || other::class != this::class) return false
        return (other as ValueClazz).value == value
    }

    final override fun hashCode(): Int {
        return value.hashCode()
    }

    override fun toString(): String {
        return if (value is NoValue) {
            this::class.simpleName!!
        } else {
            // A shortcoming is that we're stuck with "value" instead
            // of the actual field name. A cost of doing business.
            "${this::class.simpleName}(value=$value)"
        }
    }
}
