
import io.gatling.core.Predef._
import io.gatling.http.Predef._

class StressDemoSimpleCalculationsMicroservice extends Simulation {

	val httpProtocol = http
		.baseURL("http://kyiv-win-inf01:9999")
		.inferHtmlResources()
		.acceptHeader("*/*")
		.contentTypeHeader("text/plain")
		.userAgentHeader("PostmanRuntime/7.1.1")

	val headers_0 = Map(
		"Postman-Token" -> "05569577-d644-4ed0-b380-0a92997a04a9",
		"accept-encoding" -> "gzip, deflate",
		"cache-control" -> "no-cache",
		"content-length" -> "41",
		"cookie" -> "__NCTRACE=97c1caa2-8766-43ac-9837-8bbf103705f7")

    val uri1 = "http://kyiv-win-inf01:9999/DemoSimpleCalculationsMicroservice/Multiply"

	val scn = scenario("DemoSimpleCalculationsMicroservice")
		.exec(http("request_0")
			.post("/DemoSimpleCalculationsMicroservice/Multiply")
			.headers(headers_0)
			.body(RawFileBody("DemoSimpleCalculationsMicroservice_0000_request.txt")))

	setUp(scn.inject(atOnceUsers(10))).protocols(httpProtocol)
}