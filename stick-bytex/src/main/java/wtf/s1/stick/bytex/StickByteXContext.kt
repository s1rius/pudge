package wtf.s1.stick.bytex

import com.android.build.gradle.AppExtension
import com.ss.android.ugc.bytex.common.BaseContext
import org.gradle.api.Project
import wtf.s1.stick.core.MethodTrace

class StickByteXContext(
    project: Project?,
    android: AppExtension?,
    extension: StickByteXExtension?
) : BaseContext<StickByteXExtension>(project, android, extension) {

    fun config(): MethodTrace.Config {
        return MethodTrace.Config(extension?.annotationEnable?: false, null)
    }
}