package wtf.s1.pudge.hugo2

import com.android.build.gradle.AppExtension
import com.ss.android.ugc.bytex.common.BaseContext
import org.gradle.api.Project

class Hugo2ByteXContext(
    project: Project?,
    android: AppExtension?,
    extension: Hugo2ByteXExtension?
) : BaseContext<Hugo2ByteXExtension>(project, android, extension)