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
 * For use with kotlin `object` types inheriting from [ValueClazz].
 *
 * By passing [NoValue] as the constructor argument for [ValueClazz],
 * [ValueClazz.toString] will return the instance's simpleClass name only.
 *
 * e.g.
 *
 *     sealed class Example(value: Any): ValueClass(value) {
 *         object Loading: Example(NoValue())
 *         class UserId(val id: String): Example(id)
 *
 *         class NotActuallyAnObject: Example(NoValue())
 *     }
 *
 *     println(Example.Loading) >> Loading
 *     println(Example.UserId("5")) >> UserId(value=5)
 *
 *     println(Example.Loading.hashCode()) >> 1859374258
 *     println(Example.Loading.hashCode()) >> 1859374258
 *
 *     println(Example.NotActuallyAnObject().hashCode()) >> 1029379255
 *     println(Example.NotActuallyAnObject().hashCode()) >> 7029204251
 *     println(Example.NotActuallyAnObject()) >> NotActuallyObject
 * */
public class NoValue
