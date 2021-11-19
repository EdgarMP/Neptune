package com.edgarmarcopolo.neptune

import com.edgarmarcopolo.neptune.products.provider.domain.ProductsDataMapper
import com.edgarmarcopolo.neptune.products.provider.domain.models.Product
import com.edgarmarcopolo.neptune.products.provider.repository.remote.NeptuneAPI
import com.edgarmarcopolo.neptune.products.provider.repository.remote.ProductsRepository
import com.edgarmarcopolo.neptune.products.ui.ProductsUIMapper
import io.mockk.spyk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ProductMapperTest {

    private val mapperUI = spyk(ProductsUIMapper(), recordPrivateCalls = true)
    private val mapperDomain = spyk(ProductsDataMapper(), recordPrivateCalls = true)

    private val mockWebServer = MockWebServer()

    private val client = OkHttpClient.Builder()
        .connectTimeout(1, TimeUnit.SECONDS)
        .readTimeout(1, TimeUnit.SECONDS)
        .writeTimeout(1, TimeUnit.SECONDS)
        .build()

    private val api = Retrofit.Builder()
        .baseUrl(mockWebServer.url("/"))
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(NeptuneAPI::class.java)

    private val sut = ProductsRepository(api, mapperDomain, null)

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `should fetch products correctly given 200 response`() {
        val body = "{\n" +
                "  \"batch_id\": 0,\n" +
                "  \"offers\": [\n" +
                "    {\n" +
                "      \"offer_id\": \"40408\",\n" +
                "      \"name\": \"Buy 2: Select TRISCUIT Crackers\",\n" +
                "      \"image_url\": \"https://d3bx4ud3idzsqf.cloudfront.net/public/production/6840/67561_1535141624.jpg\",\n" +
                "      \"cash_back\": 1.0\n" +
                "    } " +
                "  ]\n" +
                "}"

        val mockResponse = MockResponse().setResponseCode(200).setBody(body)
        mockWebServer.enqueue(mockResponse)


        runBlocking {
            var actual : List<Product> = listOf()
            sut.getProducts().collect {
                actual = it.data?.productsList.orEmpty()
            }

            val expected = listOf(
                Product(
                    id = 40408,
                    name = "Buy 2: Select TRISCUIT Crackers",
                    cashback = 1.0,
                    imageUrl = "https://d3bx4ud3idzsqf.cloudfront.net/public/production/6840/67561_1535141624.jpg"
                )
            )

            assertEquals(expected, actual)
        }
    }

    @Test
    fun `test getProductsUI is empty` () {
        val list = mapperUI.convert(null)
        assert(list.productsUIList.isEmpty())
    }

    @Test
    fun `test getProductsDomain is empty` () {
        val list = mapperDomain.convert(null)
        assert(list.productsList.isEmpty())
    }
}