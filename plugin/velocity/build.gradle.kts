allprojects {
    repositories {
        maven("https://repo.papermc.io/repository/maven-public/")
    }

    dependencies {
        implementation(project(":core"))

        compileOnly("com.velocitypowered:velocity-api:3.4.0-SNAPSHOT")
    }
}

subprojects {
    dependencies {
        api(project(":plugin:velocity"))
    }
}
