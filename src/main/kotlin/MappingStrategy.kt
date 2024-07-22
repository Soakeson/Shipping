abstract class MappingStrategy(vararg keys: String) {
    var keys = keys.toList()
    abstract fun mapLine(line: String) : Map<String, String>
}

