plugins {
    id("org.gradle.toolchains.foojay-resolver-convention").version("1.0.0")
}

rootProject.name = "version-checker"
include("core")
include("github")
include("hangar")
include("modrinth")

include("plugin:paper")
include("plugin:paper:github-paper")
include("plugin:paper:hangar-paper")
include("plugin:paper:modrinth-paper")

include("plugin:velocity")
include("plugin:velocity:github-velocity")
include("plugin:velocity:hangar-velocity")
include("plugin:velocity:modrinth-velocity")