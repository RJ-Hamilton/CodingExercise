import com.hamilton.services.hiring.api.HiringApi
import com.hamilton.services.hiring.api.models.data.HiringResponse
import com.hamilton.services.hiring.impl.HiringRepositoryImpl
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertThrows
import org.junit.Test
import retrofit2.Response
import kotlin.test.assertEquals

class HiringRepositoryImplTest {

    private val hiringApi: HiringApi = mockk()
    private val repository = HiringRepositoryImpl(hiringApi)

    @Test
    fun `getData should return list of HiringDetail when API returns valid data`() = runTest {
        // GIVEN
        val hiringDataList = listOf(
            HiringResponse(id = 1, listId = 1, name = "Item 1"),
            HiringResponse(id = 2, listId = 1, name = "Item 2")
        )
        val mockResponse = Response.success(hiringDataList)

        coEvery { hiringApi.getFetchData() } returns mockResponse

        // WHEN
        val result = repository.getData()

        // THEN
        assertEquals(2, result.size)
        assertEquals("Item 1", result[0].name)
        assertEquals(1, result[0].listId)
    }

    @Test
    fun `getData should throw RuntimeException when API returns null body`(): Unit = runTest {
        // GIVEN
        val mockResponse = Response.success<List<HiringResponse>?>(null)

        coEvery { hiringApi.getFetchData() } returns mockResponse

        // WHEN & THEN
        assertThrows(RuntimeException::class.java) {
            runBlocking { repository.getData() }
        }
    }

    @Test
    fun `getData should throw RuntimeException when API call fails`(): Unit = runTest {
        // GIVEN
        coEvery { hiringApi.getFetchData() } throws RuntimeException("API call failed")

        // WHEN & THEN
        assertThrows(RuntimeException::class.java) {
            runBlocking { repository.getData() }
        }
    }
}