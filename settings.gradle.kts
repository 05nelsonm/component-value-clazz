rootProject.name = "component-value-clazz"

includeBuild("kotlin-components/includeBuild/dependencies")
includeBuild("kotlin-components/includeBuild/kmp")

@Suppress("PrivatePropertyName")
private val CHECK_PUBLICATION: String? by settings
if (CHECK_PUBLICATION != null) {
    include(":tools:check-publication")
} else {
    include(":value-clazz")
}
