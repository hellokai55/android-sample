package com.hellokai.orangechat

import com.android.build.api.instrumentation.AsmClassVisitorFactory
import com.android.build.api.instrumentation.ClassContext
import com.android.build.api.instrumentation.ClassData
import com.android.build.api.instrumentation.InstrumentationParameters
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes

abstract class RecordTransform : AsmClassVisitorFactory<InstrumentationParameters.None> {
    override fun createClassVisitor(classContext: ClassContext, nextClassVisitor: ClassVisitor): ClassVisitor {
        return object : ClassVisitor(Opcodes.ASM8, nextClassVisitor) {
            override fun visit(version: Int, access: Int, name: String?, signature: String?, superName: String?, interfaces: Array<out String>?) {
                super.visit(version, access, name, signature, superName, interfaces)
                println("RecordTransfom -> name:${name}")
            }
        }
    }

    override fun isInstrumentable(classData: ClassData): Boolean {
       return classData.className.startsWith("com.hellokai.orangechat")
    }

}