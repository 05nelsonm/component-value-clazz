# component-value-clazz
[![badge-license]][url-license]
[![badge-latest-release]][url-latest-release]

[![badge-kotlin]][url-kotlin]

![badge-platform-android]
![badge-platform-jvm]
![badge-platform-js]
![badge-platform-js-node]
![badge-platform-linux]
![badge-platform-macos]
![badge-platform-ios]
![badge-platform-tvos]
![badge-platform-watchos]
![badge-platform-wasm]
![badge-platform-windows]
![badge-support-android-native]
![badge-support-apple-silicon]
![badge-support-js-ir]
![badge-support-linux-arm]
![badge-support-linux-mips]

A small library that provides a functionally equivalent alternative to Kotlin's 
`value class` via inheritance.

Kotlin's `value class` is powerful and I use them pervasively, but there are some caveats 
to using them.
 - Kotlin `value class` does not compile to other languages (e.g. Java or Js). Instead, 
   the underlying type of the `value class` is what consumers from those languages see. If 
   you wish to retain the type and expose/accept it in your public API(s), this becomes 
   problematic for non-Kotlin consumers of your code.
 - If your Kotlin `value class` inherits from an `interface` or `sealed interface`, it
   loses the boxing/unboxing properties that make them so amazing! They become equivalant
   to a regular `class`, with the downside of them not compiling to platform specific
   code (as mentioned above).
     - If you're using Kotlin `value class`es that implement `interface`es, especially if they
       are a part of your public API(s), this library is for you!

As a library creator, I've learned that using Kotlin's `value class` in your public API(s) 
(such as `kotlin.Result`) comes with challenges; I've begun limiting their usage to `internal`
only.

Ensuring that consumers of your code have a plesant experience, whether they are using Kotlin 
or not, was the motivation for creating this. **Enjoy!**

A full list of `kotlin-components` projects can be found [HERE][url-kotlin-components]

### Example Usages

```kotlin
fun main() {
    val id = "123456789"
    val userId = UserId(id)

    println(userId) // >> UserId(value=123456789)
    println(UserId.equals(id)) // >> false
    println(id.hashCode()) // >> -1867378635
    println(userId.hashCode()) // >> -1867378635
}

class UserId(val id: String): ValueClazz(id)
```

Usage in sealed classes
```kotlin
fun main() {
    println(Data.Loading) // >> Loading
    println(Data.UserId("5")) // >> UserId(value=5)
    println(Data.UserNames(listOf("matthew", "nelson"))) // >> UserNames(value=[matthew, nelson])
}

sealed class Data(value: Any): ValueClazz(value) {
    object Loading: Data(NoValue())
    class UserId(val id: String): Data(id)
    class UserNames(val names: List<String>): Data(names)
}
```

Objects objects objects!

```kotlin
fun main() {
    println(Objects.Loading.hashCode()) // >> 1859374258
    println(Objects.Success.hashCode()) // >> 807752428
    println(Objects.Loading.equals(Objects.Success)) // >> false
    println(Objects.Loading) // >> Loading
    println(Objects.Success) // >> Success
    println(Objects.Failure) // >> Failure
}

sealed class Objects: ValueClazz(NoValue()) {
    object Loading: Objects()
    object Success: Objects()
    object Failure: Objects()
}
```

Real world
```kotlin
sealed class Address(@JvmField val value: String): ValueClazz(value) {
    abstract fun canonicalHostname(): String
}

sealed class IpAddress(value: String): Address(value)

class IpV4Address
@Throws(IllegalArgumentException::class)
constructor(value: String): IpAddress(value) {

    init {
        require(value.matches(IPV4_REGEX)) {
            "$value is not a valid IPv4 address"
        }
    }

    override fun canonicalHostname(): String = value
}

class IpV6Address
@Throws(IllegalArgumentException::class)
constructor(value: String): IpAddress(value) {

    init {
        require(value.matches(IPV6_REGEX)) {
            "$value is not a valid IPv6 address"
        }
    }

    override fun canonicalHostname(): String = "[$value]"
}
```

### Get Started

<!-- TAG_VERSION -->

```kotlin
// build.gradle.kts
dependencies {
    // if ValueClazz will be a part of your public API (library devs),
    // use api instead of implementation.
    implementation("io.matthewnelson.kotlin-components:value-clazz:0.1.0")
}
```

<!-- TAG_VERSION -->

```groovy
// build.gradle
dependencies {
    // if ValueClazz will be a part of your public API (library devs),
    // use api instead of implementation.
    implementation "io.matthewnelson.kotlin-components:value-clazz:0.1.0"
}
```

### Kotlin Version Compatibility

<!-- TAG_VERSION -->

| value-clazz | kotlin |
|:-----------:|:------:|
|    0.1.0    | 1.8.0  |

### Git

This project utilizes git submodules. You will need to initialize them when
cloning the repository via:

```bash
$ git clone --recursive https://github.com/05nelsonm/component-value-clazz.git
```

If you've already cloned the repository, run:
```bash
$ git checkout master
$ git pull
$ git submodule update --init
```

In order to keep submodules updated when pulling the latest code, run:
```bash
$ git pull --recurse-submodules
```

<!-- TAG_VERSION -->
[badge-latest-release]: https://img.shields.io/badge/latest--release-0.1.0-blue.svg?style=flat
[badge-license]: https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg?style=flat

<!-- TAG_DEPENDENCIES -->
[badge-kotlin]: https://img.shields.io/badge/kotlin-1.8.0-blue.svg?logo=kotlin

<!-- TAG_PLATFORMS -->
[badge-platform-android]: http://img.shields.io/badge/-android-6EDB8D.svg?style=flat
[badge-platform-jvm]: http://img.shields.io/badge/-jvm-DB413D.svg?style=flat
[badge-platform-js]: http://img.shields.io/badge/-js-F8DB5D.svg?style=flat
[badge-platform-js-node]: https://img.shields.io/badge/-nodejs-68a063.svg?style=flat
[badge-platform-linux]: http://img.shields.io/badge/-linux-2D3F6C.svg?style=flat
[badge-platform-macos]: http://img.shields.io/badge/-macos-111111.svg?style=flat
[badge-platform-ios]: http://img.shields.io/badge/-ios-CDCDCD.svg?style=flat
[badge-platform-tvos]: http://img.shields.io/badge/-tvos-808080.svg?style=flat
[badge-platform-watchos]: http://img.shields.io/badge/-watchos-C0C0C0.svg?style=flat
[badge-platform-wasm]: https://img.shields.io/badge/-wasm-624FE8.svg?style=flat
[badge-platform-windows]: http://img.shields.io/badge/-windows-4D76CD.svg?style=flat
[badge-support-android-native]: http://img.shields.io/badge/support-[AndroidNative]-6EDB8D.svg?style=flat
[badge-support-apple-silicon]: http://img.shields.io/badge/support-[AppleSilicon]-43BBFF.svg?style=flat
[badge-support-js-ir]: https://img.shields.io/badge/support-[js--IR]-AAC4E0.svg?style=flat
[badge-support-linux-arm]: http://img.shields.io/badge/support-[LinuxArm]-2D3F6C.svg?style=flat
[badge-support-linux-mips]: http://img.shields.io/badge/support-[LinuxMIPS]-2D3F6C.svg?style=flat

[url-latest-release]: https://github.com/05nelsonm/component-value-clazz/releases/latest
[url-license]: https://www.apache.org/licenses/LICENSE-2.0.txt
[url-kotlin]: https://kotlinlang.org
[url-kotlin-components]: https://kotlin-components.matthewnelson.io
