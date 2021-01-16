package wtf.s1.willfix

import org.objectweb.asm.tree.ClassNode
import org.objectweb.asm.tree.MethodNode
import org.objectweb.asm.tree.analysis.Analyzer
import org.objectweb.asm.tree.analysis.BasicInterpreter

class WillFixVerify {
    companion object {

        fun verify(classNode: ClassNode) {
            classNode.methods?.forEach {
                verifyMethod(classNode.name, it)
            }
        }

        fun verifyMethod(owner: String, methodNode: MethodNode) {
            Analyzer(BasicInterpreter()).analyze(owner, methodNode)
        }

    }
}