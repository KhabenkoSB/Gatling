
import io.gatling.core.Predef._
import io.gatling.http.Predef._

class HealthCheck extends Simulation {

	val httpProtocol = http
		.baseURL("http://kyiv-win-inf01:9999")
		.inferHtmlResources()
		.acceptHeader("*/*")
		.userAgentHeader("PostmanRuntime/7.1.1")

	val headers_0 = Map(
		"Postman-Token" -> "8ecacc76-1752-4152-ae60-1b9becbbf1c2",
		"accept-encoding" -> "gzip, deflate",
		"cache-control" -> "no-cache")

    val uri1 = "http://kyiv-win-inf01:9999/Sql/DemoAccountingMicroservice/healthCheck"

	val scn = scenario("HealthCheck")
		.exec(http("request_0")
			.get("/Sql/DemoAccountingMicroservice/healthCheck")
			.headers(headers_0))

	setUp(scn.inject(atOnceUsers(10000))).protocols(httpProtocol)
}