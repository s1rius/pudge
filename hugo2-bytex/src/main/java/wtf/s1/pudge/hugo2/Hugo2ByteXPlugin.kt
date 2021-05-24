package wtf.s1.pudge.hugo2

import com.android.build.gradle.AppExtension
import com.ss.android.ugc.bytex.common.CommonPlugin
import com.ss.android.ugc.bytex.common.TransformConfiguration
import com.ss.android.ugc.bytex.common.flow.main.Process
import com.ss.android.ugc.bytex.common.visitor.ClassVisitorChain
import org.gradle.api.Project
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassWriter

class Hugo2ByteXPlugin: CommonPlugin<Hugo2ByteXExtension, Hugo2ByteXContext>() {

    override fun getContext(
        project: Project?,
        android: AppExtension?,
        extension: Hugo2ByteXExtension?
    ): Hugo2ByteXContext {
        return Hugo2ByteXContext(project, android, extension)
    }

    override fun transform(relativePath: String, chain: ClassVisitorChain): Boolean {
        chain.connect(Hugo2ClassVisitor(context))
        return true
    }

    override fun flagForClassReader(process: Process?): Int {
        return ClassReader.EXPAND_FRAMES
    }

    override fun flagForClassWriter(): Int {
        return ClassWriter.COMPUTE_MAXS
    }


}