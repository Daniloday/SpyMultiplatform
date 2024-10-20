plugins {
    alias(libs.plugins.spy.kotlinMultiplatform)
    alias(libs.plugins.spy.composeMultiplatform)
}

compose.resources {
    publicResClass = true
    packageOfResClass = "com.missclick.spy.resources"
    generateResClass = always
}



