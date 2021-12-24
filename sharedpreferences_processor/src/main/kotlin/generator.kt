/**
 * @author alex@justprodev.com on 24.12.2021.
 */
fun Interface.generateImplementation(): String {
    val sb = StringBuilder()

    sb.append("package $packageName\n\n")
    sb.append("import android.content.Context\n")
    sb.append("import android.content.SharedPreferences\n\n")

    sb.append("class ${classImplName}(context: Context) : $className {\n")

    sb.append("\tprivate val prefs = context.getSharedPreferences(\"$className\", Context.MODE_PRIVATE)\n")

    fields.forEach {
        val prefName = "\"${it.name}\""
        sb.append("\toverride var ${it.name}: ${it.type}?\n")
        sb.append("\t\tget() = prefs.get${it.type}($prefName, ${it.default})\n")
        sb.append("\t\tset(value) = if(value == null) prefs.edit().remove($prefName).apply() else prefs.edit().put${it.type}($prefName, value).apply()\n")
    }

    sb.append("}")

    return sb.toString()
}

class Interface(
    val classImplName: String,
    val className: String,
    val packageName: String,
    val fields: List<Field>
)

class Field(
    val name: String,
    val type: String,
    val value: Any? = null
) {
    val default: String
        get() =  if(value==null) {
            when(type) {
                "Int","Long" -> "0"
                "Float" -> "0f"
                "Boolean" -> "false"
                else -> "null"
            }
        } else {
            when(type) {
                "String" -> "\"$value\""
                "Float" -> "${value}f"
                else -> "$value"
            }
        }


}