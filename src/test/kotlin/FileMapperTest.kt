import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails
import kotlin.test.assertNotNull

class FileMapperTest {
    @Test
    fun testFileMapperConstruct() {
        assertNotNull(FileMapper(FileType.CSV, "src/test/kotlin/test-file.txt", "1", "2", "3", "4"))
    }

    @Test
    fun testInvalidPath() {
        assertFails { FileMapper(FileType.CSV, "test-file.txt", "1", "2", "3", "4") }
    }

    @Test
    fun testFileMapperCsv() {
        val fileMapper = FileMapper(FileType.CSV, "src/test/kotlin/test-file.txt", "1", "2", "3", "4")
        assertEquals(mapOf(Pair("1", "valid"), Pair("2", "100"), Pair("3", "100"), Pair("4", "100")), fileMapper.mapNextLine())
        assertEquals(mapOf(Pair("1", "valid"), Pair("2", "100")), fileMapper.mapNextLine())
        assertFails { fileMapper.mapNextLine() }
    }
}