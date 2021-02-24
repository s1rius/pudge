# publish artifact to mavenLocal and debug the gradle plugin transform
# 发布插件到本地的maven仓库，进入插件的debug模式
./gradlew :willfix-core:publishToMavenLocal
./gradlew :willfix-standalone:publishToMavenLocal
./gradlew :willfix-bytex:publishToMavenLocal
./pluginDebug.sh