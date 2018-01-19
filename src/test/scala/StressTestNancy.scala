
import com.typesafe.config.ConfigFactory
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import net.liftweb.json.{DefaultFormats, parse}

import scala.concurrent.duration._
import scalaj.http.Http

class StressTestNancy extends Simulation {

	val baseUrl = ConfigFactory.load().getString("baseURL")
	val countUser = ConfigFactory.load().getInt("countUser")
	val soakTestTime = ConfigFactory.load().getInt("soakTestTime")
	val stressTestTime = ConfigFactory.load().getInt("stressTestTime")
	val influxDbURL = ConfigFactory.load().getString("influxDbURL")

	val httpProtocol = http
		.baseURL(baseUrl)
		.inferHtmlResources()
		.acceptHeader("*/*")
		.contentTypeHeader("text/plain")
		.userAgentHeader("PostmanRuntime/7.1.1")

	val headers_0 = Map(
		"Postman-Token" -> "f953fc8c-c9e1-4b2c-a9c5-1a79f886f391",
		"accept-encoding" -> "gzip, deflate",
		"cache-control" -> "no-cache",
		"content-length" -> "70",
		"cookie" -> "__NCTRACE=a882e5db-f7f6-46f9-9f6f-cf356db2f603")

    val uri1 = baseUrl +"/CallOtherMicroservice/TriggerTestMicroservice"

	val scn = scenario("StressTest")
		.exec(http("CallOtherMicroservice/TriggerTestMicroservice")
			.post("/CallOtherMicroservice/TriggerTestMicroservice")
			.headers(headers_0)
			.body(RawFileBody("SoakTest_0000_request.txt")))


	setUp(scn.inject(rampUsersPerSec(1) to countUser*2 during(stressTestTime minutes)).protocols(httpProtocol))

	after {
		getMemory()

	}

	def getMemory() {
		implicit val formats = DefaultFormats
		var url = influxDbURL+"/query?q=SELECT+max(%22used_percent%22)+FROM+%22mem%22+Where+time+%3E%3D+now()+-+"+stressTestTime+"m&db=telegraf"
		val memoryPersentMax = Http(url).asString
		url = influxDbURL+"/query?q=SELECT+min(%22used_percent%22)+FROM+%22mem%22+Where+time+%3E%3D+now()+-+"+stressTestTime+"m&db=telegraf"
		val memoryPersentMin = Http(url).asString
		url = influxDbURL+"/query?q=SELECT+mean(%22used_percent%22)+FROM+%22mem%22+Where+time+%3E%3D+now()+-+"+stressTestTime+"m&db=telegraf"
		val memoryPersentAvg = Http(url).asString



		val max = parse(memoryPersentMax.body).extract[InfluxResponse]
		val min = parse(memoryPersentMin.body).extract[InfluxResponse]
		val avg = parse(memoryPersentAvg.body).extract[InfluxResponse]

		println("******Memory******")
		println("Memory MAX % "+max.results(0).series(0).values(0).get(1))
		println("Memory MIN % "+min.results(0).series(0).values(0).get(1))
		println("Memory AVG % "+avg.results(0).series(0).values(0).get(1))
		println("******************")

	}
}