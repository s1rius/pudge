package wtf.s1.willfix.core

import org.objectweb.asm.MethodVisitor

interface IWillFixContext {

    fun logger(): ILogger

    fun methodVisitor(
        clazzName: String?,
        access: Int,
        name: String?,
        descriptor: String?,
        signature: String?,
        exceptions: Array<out String>?,
        methodVisitor: MethodVisitor
    ): MethodVisitor

    fun exceptionTypeName(): String
}
