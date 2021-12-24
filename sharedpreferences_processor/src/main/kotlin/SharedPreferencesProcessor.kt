import com.google.devtools.ksp.processing.*
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSPropertyDeclaration
import com.google.devtools.ksp.symbol.KSVisitorVoid
import com.google.devtools.ksp.validate
import com.justprodev.annotations.Default
import java.io.OutputStream

fun OutputStream.append(str: String) {
    this.write(str.toByteArray())
}

class SharedPreferencesProcessor(
    val codeGenerator: CodeGenerator,
    val logger: KSPLogger
) : SymbolProcessor {
    override fun finish() {}

    override fun process(resolver: Resolver): List<KSAnnotated> {
        val symbols = resolver.getSymbolsWithAnnotation("com.justprodev.annotations.SharedPreferences")
        val ret = symbols.filter { !it.validate() }.toList()
        symbols
            .filter { it is KSClassDeclaration && it.validate() }
            .forEach { it.accept(SharedPreferencesVisitor(), Unit) }
        return ret
    }

    inner class SharedPreferencesVisitor : KSVisitorVoid() {
        override fun visitClassDeclaration(classDeclaration: KSClassDeclaration, data: Unit) {
            val packageName = classDeclaration.packageName.asString()
            val className = classDeclaration.simpleName.asString()
            val classImplName = classDeclaration.simpleName.asString() + "Impl"

            // create file
            val file = codeGenerator.createNewFile(
                Dependencies(true, classDeclaration.containingFile!!),
                packageName,
                classImplName
            )

            // collect fields
            val fields = ArrayList<Field>()
            for (declaration in classDeclaration.declarations) {
                if(declaration is KSPropertyDeclaration) {
                    // field annotated with @Default("value")
                    val default: Any? = declaration.annotations
                        .find { it.shortName.asString() == Default::class.simpleName }
                        ?.arguments?.getOrNull(0)?.value

                    fields.add(Field(
                        declaration.simpleName.asString(),
                        declaration.type.resolve().declaration.simpleName.asString(),
                        default
                    ))
                } else {
                    logger.error("$declaration not allowed in the interface ${packageName}.$className")
                }
            }

            // generate Impl
            file.append(Interface(classImplName, className, packageName, fields).generateImplementation())

            file.close()
        }
    }
}

class SharedPreferencesProcessorProvider : SymbolProcessorProvider {
    override fun create(
        environment: SymbolProcessorEnvironment
    ): SymbolProcessor {
        return SharedPreferencesProcessor(environment.codeGenerator, environment.logger)
    }
}