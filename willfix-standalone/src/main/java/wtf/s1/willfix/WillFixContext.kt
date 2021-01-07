package wtf.s1.willfix

import com.android.build.api.transform.TransformInvocation
import org.gradle.api.Project
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.ClassWriter
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes
import wtf.s1.willfix.core.ILogger
import wtf.s1.willfix.core.IWillFixContext
import wtf.s1.willfix.core.MethodDetector
import wtf.s1.willfix.core.visitors.WillFixClassVisitor
import wtf.s1.willfix.logging.LevelLog


open class WillFixContext(
    private val project: Project?,
    private val extension: WillFixExtension?
) : IWillFixContext {

    private var transformInvocation: TransformInvocation? = null

    private lateinit var detector: MethodDetector

    fun init(transformInvocation: TransformInvocation?) {
        this.transformInvocation = transformInvocation

        if (extension?.enable == false) {
            logger().w("will fix plugin disable")
            return
        }

        extension?.logLevel?.let {
            if (logger is LevelLog) {
                logger.setLevel(it)
            }
        }

        val methodList = extension?.methodList
        val separator: String = extension?.separator ?: "#"

        detector = MethodDetector(Opcodes.ASM6, this, methodList, separator)

    }

    fun getClassVisitor(cw: ClassWriter): ClassVisitor {
        return WillFixClassVisitor(Opcodes.ASM6, cw, this)
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
        return detector.methodVisitor(
            clazzName,
            access,
            name,
            descriptor,
            signature,
            exceptions,
            methodVisitor
        )
    }

    override fun logger(): ILogger {
        return logger
    }

    private val logger: ILogger = LevelLog()

    open fun isReleaseBuild(): Boolean {
        return transformInvocation?.context?.variantName?.toLowerCase()?.contains("release")
            ?: false
    }

    fun isEnable(): Boolean {
        return extension?.isEnable == true && (extension.isEnableInDebug || isReleaseBuild())
    }
}