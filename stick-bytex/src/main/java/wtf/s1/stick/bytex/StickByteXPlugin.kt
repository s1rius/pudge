package wtf.s1.stick.bytex

import com.android.build.gradle.AppExtension
import com.ss.android.ugc.bytex.common.CommonPlugin
import com.ss.android.ugc.bytex.common.Constants
import com.ss.android.ugc.bytex.common.TransformConfiguration
import com.ss.android.ugc.bytex.common.flow.main.Process
import com.ss.android.ugc.bytex.common.visitor.ClassVisitorChain
import org.gradle.api.Project
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassWriter
import wtf.s1.stick.core.TraceClassVisitor

class StickByteXPlugin: CommonPlugin<StickByteXExtension, StickByteXContext>() {

    override fun getContext(
        project: Project?,
        android: AppExtension?,
        extension: StickByteXExtension?
    ): StickByteXContext {
        return StickByteXContext(project, android, extension)
    }

    override fun transform(relativePath: String, chain: ClassVisitorChain): Boolean {
        chain.connect(TraceClassVisitor(context, Constants.ASM_API))
        return true
    }

    override fun flagForClassReader(process: Process?): Int {
        return ClassReader.EXPAND_FRAMES
    }

    override fun flagForClassWriter(): Int {
        return ClassWriter.COMPUTE_MAXS
    }

    override fun afterExecute() {
        super.afterExecute()
        //todo s1rius output file
    }
}