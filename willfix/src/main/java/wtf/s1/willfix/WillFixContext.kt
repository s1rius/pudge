package wtf.s1.willfix

import org.gradle.api.Project
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.ClassWriter
import org.objectweb.asm.Opcodes
import wtf.s1.willfix.logging.ILogger
import wtf.s1.willfix.logging.LevelLog
import wtf.s1.willfix.visitors.WillFixClassVisitor
import java.util.*
import kotlin.collections.HashSet


open class WillFixContext(
    private val project: Project?,
    private val extension: WillFixExtension?
) {

    private val mOptimizationNeededMethods: MutableMap<String, Set<String>> = HashMap()

    fun init() {
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

        if (methodList == null || methodList.size == 0) {
            logger().w("no method need try catch")
            return
        }

        for (item in methodList) {
            val split = item.split(separator.toRegex()).toTypedArray()
            if (split.size != 3) {
                logger().w("Method Error item must consist of 3 parts:$item")
                continue
            }
            if ("" == split[0].trim { it <= ' ' }) {
                logger().w("Method Error ClassName is empty!$item")
                continue
            }
            if ("" == split[1].trim { it <= ' ' }) {
                logger().w("Method Error MethodName is empty!$item")
                continue
            }
            if ("" == split[2].trim { it <= ' ' }) {
                logger().w("Method ErrorMethodDes is empty!$item")
                continue
            }
            getClazzKey(split[0])?.let {

                val set: MutableSet<String> =
                    mOptimizationNeededMethods.computeIfAbsent(getClazzKey(it)) { HashSet() } as HashSet<String>
                set.add(
                    getMethodUniqueKey(
                        methodName = split[1],
                        desc = split[2]
                    ).trim { it <= ' ' })
            }


        }
        logger().d(mOptimizationNeededMethods.toString())
    }

    fun getClassVisitor(cw: ClassWriter): ClassVisitor {
        return WillFixClassVisitor(Opcodes.ASM6, cw, this)
    }

    fun isClazzNeedVisit(className: String?): Boolean {
        if (className == null) {
            return false
        }
        return mOptimizationNeededMethods.containsKey(getClazzKey(className))
    }

    fun isMethodNeedTryCatch(className: String?, methodName: String?, desc: String?): Boolean {
        if (className == null || methodName == null || desc == null) {
            return false
        }
        val classKey = getClazzKey(className)
        if (mOptimizationNeededMethods.containsKey(classKey)) {
            val methods = mOptimizationNeededMethods[classKey]
            if (methods?.contains(
                    getMethodUniqueKey(
                        methodName = methodName,
                        desc = desc
                    )
                ) == true
            ) {
                return true
            }
        }
        return false
    }

    private fun getMethodUniqueKey(
        separator: String = "#",
        methodName: String,
        desc: String
    ): String {
        return methodName.trim { it <= ' ' } + separator + desc.trim { it <= ' ' }
    }

    private fun getClazzKey(className: String): String {
        return replaceDot2Slash(className)
    }

    private fun replaceDot2Slash(str: String): String {
        return str.replace('.', '/')
    }

    fun logger(): ILogger {
        return logger
    }

    private val logger: ILogger = LevelLog()
}