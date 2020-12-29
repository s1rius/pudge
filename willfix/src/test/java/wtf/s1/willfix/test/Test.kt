package wtf.s1.willfix.test

import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassWriter
import wtf.s1.willfix.TestSetGet
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


object Test {
    @JvmStatic
    fun main(args: Array<String>) {

        try {
            val classReader = ClassReader("wtf.s1.willfix.TestSetGet")
            val classWriter = ClassWriter(classReader, ClassWriter.COMPUTE_MAXS)

//            classReader.accept(
//                WillFixClassVisitor(Opcodes.ASM8, classWriter, WillFixContext()),
//                ClassReader.EXPAND_FRAMES
//            )
//            outputClazz(classWriter.toByteArray())
//
//            //ClassLoader.getSystemClassLoader().loadClass("TestSetGet1.class")
//
//            val file = File(TestSetGet::class.java.getResource("/").path)
//            if (file.exists()) {
//                println("file exist")
//                val url = file.toURI().toURL()
//                val cl: ClassLoader = URLClassLoader(arrayOf(url))
//                println("class load")
//                val loadClass = cl.loadClass("wtf.s1.willfix.TestSetGet")
//                val newInstance = loadClass.newInstance()
//                if (newInstance is TestSetGet) {
//                    print(newInstance.getDesc())
//                }
//            }


        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    private fun outputClazz(bytes: ByteArray) {
        // 输出类字节码
        var out: FileOutputStream? = null
        try {
            val pathName = TestSetGet::class.java.getResource("/").path + "wtf/s1/willfix/TestSetGet.class"
            out = FileOutputStream(File(pathName))
            println("ASM类输出路径：$pathName")
            out.write(bytes)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            if (null != out) try {
                out.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}