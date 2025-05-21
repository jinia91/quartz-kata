package deepdive.quartz.quartz

import org.objectweb.asm.*
import java.io.InputStream

import java.lang.invoke.SerializedLambda
import java.lang.reflect.Method


fun main() {

    val lambda = {
        println("Hello, World! ${System.currentTimeMillis()}")
    }

    val lambdaClass = lambda.javaClass
    val resourcePath = "/" + lambdaClass.name.replace('.', '/') + ".class"
    val lambdaStream: InputStream = lambdaClass.getResourceAsStream(resourcePath)!!

    val reader = ClassReader(lambdaStream)
    reader.accept(object : ClassVisitor(Opcodes.ASM9) {
        override fun visitMethod(
            access: Int,
            name: String?,
            descriptor: String?,
            signature: String?,
            exceptions: Array<out String>?
        ): MethodVisitor {
            return object : MethodVisitor(Opcodes.ASM9) {
                override fun visitMethodInsn(
                    opcode: Int,
                    owner: String,
                    name: String,
                    descriptor: String,
                    isInterface: Boolean
                ) {
                    println("Lambda calls -> $owner.$name $descriptor")
                }
            }
        }
    }, 0)

}