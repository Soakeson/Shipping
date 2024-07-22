import java.io.FileReader

class FileMapper(type: FileType, private var path: String, vararg args: String) {
    private var reader: FileReader = FileReader(path)
    private var lines: List<String> = reader.readLines()
    private var curr: Int = 0
    private var mapper = when(type) {FileType.CSV -> CsvMapping(*args)}

    fun mapNextLine() : Map<String, String>? {
        if (curr == lines.size)
            return null

        try {
            val words = mapper.mapLine(lines[curr])
            curr++
            return words
        } catch(e: Exception) {
            throw Exception("${path}:${curr+1} ${e.message}")
        }
    }
}

enum class FileType {
    CSV
}