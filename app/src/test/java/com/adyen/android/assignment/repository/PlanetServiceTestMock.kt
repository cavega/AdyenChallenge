package com.adyen.android.assignment.repository

import com.adyen.android.assignment.api.PlanetaryService
import com.adyen.android.assignment.api.model.AstronomyPicture
import com.adyen.android.assignment.api.model.DayAdapter
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response

class PlanetServiceTestMock : PlanetaryService {

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .add(DayAdapter())
        .build()

    private val jsonAdapter: JsonAdapter<List<AstronomyPicture>> = moshi.adapter(
        Types.newParameterizedType(
            List::class.java,
            AstronomyPicture::class.java
        )
    )

    private var mockJsonString: String? = null

    companion object {

        val successJson: String = "[\n" +
                "    {\n" +
                "        \"copyright\": \"\\nGordon \\nGarradd.  Used with permission.\\n\\n\",\n" +
                "        \"date\": \"1996-02-08\",\n" +
                "        \"explanation\": \"Get ready for one of the most impressive but least anticipated light shows in modern astronomical history. Next month, newly discovered Comet Hyakutake will pass closer to the Earth than any recent comet. Unknown before its discovery by Yuji Hyakutake on 30 January 1996, the fuzzy spot in the above photograph is a comet now predicted to become bright enough to see without a telescope. Although comets act in such diverse ways that predictions are frequently inaccurate, even conservative estimates indicate that this comet is likely to impress. For example, even if Comet Hyakutake remains physically unchanged, its close pass near the Earth in late March 1996 should cause it to appear to brighten to about 3rd magnitude - still bright enough to see with the unaided eye. In the next two months, though, the comet will continue to approach the Sun and hence should become brighter still. Optimistic predictions include that Comet Hyakutake will change physically, develop a larger coma and tail, brighten dramatically, move noticeably in the sky during a single night, and may ultimately become known as the \\\"The Great Comet of 1996.\\\" Move over Hale-Bopp!\",\n" +
                "        \"hdurl\": \"https://apod.nasa.gov/apod/image/hyakutake_garradd_big.gif\",\n" +
                "        \"media_type\": \"image\",\n" +
                "        \"service_version\": \"v1\",\n" +
                "        \"title\": \"Hyakutake: The Great Comet of 1996?\",\n" +
                "        \"url\": \"https://apod.nasa.gov/apod/image/hyakutake_garradd.gif\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"copyright\": \"Jeff Dai\",\n" +
                "        \"date\": \"2021-11-18\",\n" +
                "        \"explanation\": \"A photographer in silhouette stands in bright moonlight as the Full Moon rises in this well-planned telephoto image. Of course, the Full Moon is normally the brightest lunar phase. But on November 18/19, the Full Moon's light will be dimmed during a deep partial lunar eclipse seen across much of planet Earth. At maximum eclipse only a few percent of the lunar disk's diameter should remain outside the Earth's dark umbral shadow when the Moon slides close to the shadow's southern edge. Near apogee, the farthest point in its orbit, the Moon's motion will be slow. That should make this second lunar eclipse of 2021 an exceptionally long partial lunar eclipse. For most of North America the eclipse partial phases will be visible in predawn hours. Since eclipses tend to come in pairs, this lunar eclipse will be followed by a solar eclipse in two weeks on December 4.\",\n" +
                "        \"hdurl\": \"https://apod.nasa.gov/apod/image/2111/moonwalk1.jpg\",\n" +
                "        \"media_type\": \"image\",\n" +
                "        \"service_version\": \"v1\",\n" +
                "        \"title\": \"Full Moonlight\",\n" +
                "        \"url\": \"https://apod.nasa.gov/apod/image/2111/moonwalk1c1024.jpg\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"copyright\": \"Paolo De\\nSalvatore\",\n" +
                "        \"date\": \"2019-04-17\",\n" +
                "        \"explanation\": \"One of the brightest galaxies in planet Earth's sky is similar in size to our Milky Way Galaxy: big, beautiful Messier 81. Also known as NGC 3031 or Bode's galaxy for its 18th century discoverer, this grand spiral can be found toward the northern constellation of Ursa Major, the Great Bear. The detailed telescopic view reveals M81's bright yellow nucleus, blue spiral arms, pink starforming regions, and sweeping cosmic dust lanes. Some dust lanes actually run through the galactic disk (left of center), contrary to other prominent spiral features though. The errant dust lanes may be the lingering result of a close encounter between M81 and its smaller companion galaxy, M82. Scrutiny of variable stars in M81 has yielded one of the best determined distances for an external galaxy -- 11.8 million light-years.\",\n" +
                "        \"hdurl\": \"https://apod.nasa.gov/apod/image/1904/M81salvatore.jpg\",\n" +
                "        \"media_type\": \"image\",\n" +
                "        \"service_version\": \"v1\",\n" +
                "        \"title\": \"Messier 81\",\n" +
                "        \"url\": \"https://apod.nasa.gov/apod/image/1904/M81salvatore1024.jpg\"\n" +
                "    }]"

        val successBadDataJson: String = "[\n" +
                "    {\n" +
                "        \"copyright\": \"\\nGordon \\nGarradd.  Used with permission.\\n\\n\",\n" +
                "        \"date\": \"1996-02-08\",\n" +
                "        \"explanation\": \"Get ready for one of the most impressive but least anticipated light shows in modern astronomical history. Next month, newly discovered Comet Hyakutake will pass closer to the Earth than any recent comet. Unknown before its discovery by Yuji Hyakutake on 30 January 1996, the fuzzy spot in the above photograph is a comet now predicted to become bright enough to see without a telescope. Although comets act in such diverse ways that predictions are frequently inaccurate, even conservative estimates indicate that this comet is likely to impress. For example, even if Comet Hyakutake remains physically unchanged, its close pass near the Earth in late March 1996 should cause it to appear to brighten to about 3rd magnitude - still bright enough to see with the unaided eye. In the next two months, though, the comet will continue to approach the Sun and hence should become brighter still. Optimistic predictions include that Comet Hyakutake will change physically, develop a larger coma and tail, brighten dramatically, move noticeably in the sky during a single night, and may ultimately become known as the \\\"The Great Comet of 1996.\\\" Move over Hale-Bopp!\",\n" +
                "        \"hdurl\": \"https://apod.nasa.gov/apod/image/hyakutake_garradd_big.gif\",\n" +
                "        \"media_type\": \"image\",\n" +
                "        \"service_version\": \"v1\",\n" +
                "        \"title\": \"Hyakutake: The Great Comet of 1996?\",\n" +
                "        \"url\": \"\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"copyright\": \"Jeff Dai\",\n" +
                "        \"date\": \"2021-11-18\",\n" +
                "        \"explanation\": \"A photographer in silhouette stands in bright moonlight as the Full Moon rises in this well-planned telephoto image. Of course, the Full Moon is normally the brightest lunar phase. But on November 18/19, the Full Moon's light will be dimmed during a deep partial lunar eclipse seen across much of planet Earth. At maximum eclipse only a few percent of the lunar disk's diameter should remain outside the Earth's dark umbral shadow when the Moon slides close to the shadow's southern edge. Near apogee, the farthest point in its orbit, the Moon's motion will be slow. That should make this second lunar eclipse of 2021 an exceptionally long partial lunar eclipse. For most of North America the eclipse partial phases will be visible in predawn hours. Since eclipses tend to come in pairs, this lunar eclipse will be followed by a solar eclipse in two weeks on December 4.\",\n" +
                "        \"hdurl\": \"https://apod.nasa.gov/apod/image/2111/moonwalk1.jpg\",\n" +
                "        \"media_type\": \"image\",\n" +
                "        \"service_version\": \"v1\",\n" +
                "        \"title\": \"Full Moonlight\",\n" +
                "        \"url\": null\n" +
                "    },\n" +
                "    {\n" +
                "        \"copyright\": \"Paolo De\\nSalvatore\",\n" +
                "        \"date\": \"2019-04-17\",\n" +
                "        \"explanation\": \"One of the brightest galaxies in planet Earth's sky is similar in size to our Milky Way Galaxy: big, beautiful Messier 81. Also known as NGC 3031 or Bode's galaxy for its 18th century discoverer, this grand spiral can be found toward the northern constellation of Ursa Major, the Great Bear. The detailed telescopic view reveals M81's bright yellow nucleus, blue spiral arms, pink starforming regions, and sweeping cosmic dust lanes. Some dust lanes actually run through the galactic disk (left of center), contrary to other prominent spiral features though. The errant dust lanes may be the lingering result of a close encounter between M81 and its smaller companion galaxy, M82. Scrutiny of variable stars in M81 has yielded one of the best determined distances for an external galaxy -- 11.8 million light-years.\",\n" +
                "        \"hdurl\": \"https://apod.nasa.gov/apod/image/1904/M81salvatore.jpg\",\n" +
                "        \"media_type\": \"image\",\n" +
                "        \"service_version\": \"v1\",\n" +
                "        \"title\": \"Messier 81\",\n" +
                "        \"url\": \"https://apod.nasa.gov/apod/image/1904/M81salvatore1024.jpg\"\n" +
                "    }]"
    }

    override suspend fun getPictures(): Response<List<AstronomyPicture>> {
        if (mockJsonString == null) {
            return Response<List<AstronomyPicture>>.error(
                500,
                "Network error found".toResponseBody()
            )
        }
        val items = jsonAdapter.fromJson(mockJsonString!!)
        return Response<List<AstronomyPicture>>.success(items)
    }

    fun setMockData(jsonString: String?) {
        this.mockJsonString = jsonString
    }
}