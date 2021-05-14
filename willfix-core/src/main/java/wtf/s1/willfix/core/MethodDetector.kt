package wtf.s1.willfix.core

import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes
import org.objectweb.asm.Type
import org.objectweb.asm.tree.MethodInsnNode
import wtf.s1.willfix.core.visitors.HasReturnMethodTransformer
import wtf.s1.willfix.core.visitors.VoidReturnMethodTransformer
import java.util.HashMap

class MethodDetector(
    private val api: Int,
    private val context: IWillFixContext,
    methodList: List<String>?,
    private var separator: String = "#",
    catchHandler: String?
) {

    private val mOptimizationNeededMethods: MutableMap<String, Set<String>> = HashMap()
    private var exceptionHandleNode: MethodInsnNode

    init {
        methodList?.let {
            if (it.isNotEmpty()) {
                for (item in it) {
                    val split = item.split(separator.toRegex()).toTypedArray()
                    if (split.size != 3) {
                        context.logger().w("Method Error item must consist of 3 parts:$item")
                        continue
                    }
                    if ("" == split[0].trim { it <= ' ' }) {
                        context.logger().w("Method Error ClassName is empty!$item")
                        continue
                    }
                    if ("" == split[1].trim { it <= ' ' }) {
                        context.logger().w("Method Error MethodName is empty!$item")
                        continue
                    }
                    if ("" == split[2].trim { it <= ' ' }) {
                        context.logger().w("Method ErrorMethodDes is empty!$item")
                        continue
                    }
                    getClazzKey(split[0]).let {

                        val set: MutableSet<String> =
                            mOptimizationNeededMethods.computeIfAbsent(getClazzKey(it)) { HashSet() } as HashSet<String>
                        set.add(
                            getMethodUniqueKey(
                                methodName = split[1],
                                desc = split[2]
                            ).trim { it <= ' ' })
                    }


                }
            } else {
                context.logger().w("no method need try catch")
            }
        }

        exceptionHandleNode = getCatchMethod(catchHandler)
        context.logger().d(mOptimizationNeededMethods.toString())
    }

    private fun isMethodNeedTryCatch(
        className: String?,
        methodName: String?,
        desc: String?
    ): Boolean {
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

    fun methodVisitor(
        clazzName: String?,
        access: Int,
        name: String?,
        descriptor: String?,
        signature: String?,
        exceptions: Array<out String>?,
        methodVisitor: MethodVisitor
    ): MethodVisitor {

        return if (isMethodNeedTryCatch(clazzName, name, descriptor)) {
            context.logger().i("transform this method $access $name $descriptor")
            if (Type.getReturnType(descriptor) == Type.VOID_TYPE) {
                context.logger().d("void return")
                VoidReturnMethodTransformer(
                    context,
                    methodVisitor,
                    exceptionHandleNode,
                    this.api,
                    access,
                    name,
                    descriptor,
                    signature,
                    exceptions
                )
            } else {
                context.logger().d("has return")
                HasReturnMethodTransformer(
                    context,
                    methodVisitor,
                    exceptionHandleNode,
                    this.api,
                    access,
                    name,
                    descriptor,
                    signature,
                    exceptions
                )
            }
        } else {
            methodVisitor
        }
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

    private fun getCatchMethod(catchHandler: String?): MethodInsnNode {
        catchHandler?.let {
            val split = it.split(separator.toRegex()).toTypedArray()
            if (split.size == 3) {
                return MethodInsnNode(
                    Opcodes.INVOKESTATIC,
                    getClazzKey(split[0]),
                    split[1],
                    split[2],
                    false
                )
            }
        }
        return MethodInsnNode(
            Opcodes.INVOKEVIRTUAL, "java/lang/Exception",
            "printStackTrace",
            "()V",
            false
        )
    }
}