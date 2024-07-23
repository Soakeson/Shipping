package Mappers

abstract class MappingStrategy(vararg keys: String) {
    var keys = keys.toList()
    abstract fun mapString(line: String) : Map<String, String>
}

