package wtf.s1.willfix.bytex

import com.android.build.gradle.AppExtension
import com.ss.android.ugc.bytex.common.CommonPlugin
import com.ss.android.ugc.bytex.common.TransformConfiguration
import com.ss.android.ugc.bytex.common.flow.main.Process
import com.ss.android.ugc.bytex.common.visitor.ClassVisitorChain
import org.gradle.api.Project
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassWriter

class WillFixByteXPlugin : CommonPlugin<WillFixByteXExtension, WillFixByteXContext>() {
    override fun getContext(
        project: Project?,
        android: AppExtension?,
        extension: WillFixByteXExtension?
    ): WillFixByteXContext {
        return WillFixByteXContext(project, android, extension)
    }


    override fun transformConfiguration(): TransformConfiguration {
        return object : TransformConfiguration {
            override fun isIncremental(): Boolean {
                //插件默认是增量的，如果插件不支持增量，需要返回false
                //The plugin is incremental by default.It should return false if incremental is not supported by the plugin
                return false
            }
            //            @NotNull
            //            @Override
            //            public Set<? super QualifiedContent.Scope> getScopes(@Nullable VariantScope variantScope) {
            //                return TransformManager.SCOPE_FULL_PROJECT_WITH_LOCAL_JARS;
            //            }
        }
    }

    override fun transform(relativePath: String, chain: ClassVisitorChain): Boolean {
        chain.connect(WrapWillFixVisitor(context))
        return true
    }

    override fun flagForClassReader(process: Process?): Int {
        return ClassReader.EXPAND_FRAMES
    }

    override fun flagForClassWriter(): Int {
        return ClassWriter.COMPUTE_MAXS
    }
}