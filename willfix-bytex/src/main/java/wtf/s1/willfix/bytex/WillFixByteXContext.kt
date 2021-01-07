package wtf.s1.willfix.bytex

import com.android.build.gradle.AppExtension
import com.ss.android.ugc.bytex.common.BaseContext
import com.ss.android.ugc.bytex.common.Constants
import org.gradle.api.Project
import org.objectweb.asm.MethodVisitor
import wtf.s1.willfix.core.ILogger
import wtf.s1.willfix.core.IWillFixContext
import wtf.s1.willfix.core.MethodDetector

public class WillFixByteXContext(
    project: Project?,
    android: AppExtension?,
    extension: WillFixByteXExtension?
) : BaseContext<WillFixByteXExtension>(project, android, extension), IWillFixContext {

    override fun init() {
        super.init()
        methodDetector = MethodDetector(
            Constants.ASM_API,
            this,
            extension?.methodList,
            extension?.separator ?: "#"
        )
    }

    private lateinit var methodDetector: MethodDetector

    override fun logger(): ILogger {
        return ByteXLogWrapper(logger)
    }

    override fun methodVisitor(
        clazzName: String?,
        access: Int,
        name: String?,
        descriptor: String?,
        signature: String?,
        exceptions: Array<out String>?,
        methodVisitor: MethodVisitor
    ): MethodVisitor {
        return methodDetector.methodVisitor(
            clazzName,
            access,
            name,
            descriptor,
            signature,
            exceptions,
            methodVisitor
        )
    }
}