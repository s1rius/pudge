package wtf.s1.willfix

import com.android.build.gradle.AppExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

class WillFixPlugin: Plugin<Project> {

    override fun apply(project: Project) {
        val android = project.extensions.getByType(AppExtension::class.java)

        val extension: WillFixExtension? = project.extensions.create("willFix", WillFixExtension::class.java)

        android.registerTransform(WillFixTransform(WillFixContext(project, extension)))
    }
}