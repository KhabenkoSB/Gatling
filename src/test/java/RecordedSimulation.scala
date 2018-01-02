
import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class RecordedSimulation extends Simulation {

	val httpProtocol = http
		.baseURL("http://ua1-paas-tv:9999")
		.inferHtmlResources()
		.acceptHeader("*/*")
		.contentTypeHeader("text/plain")
		.userAgentHeader("PostmanRuntime/7.1.1")

	val headers_0 = Map(
		"Postman-Token" -> "2c8b358e-bc29-4588-852f-7528989322fd",
		"accept-encoding" -> "gzip, deflate",
		"cache-control" -> "no-cache",
		"content-length" -> "42")

    val uri1 = "http://ua1-paas-tv:9999/Sql/DemoAccountingMicroservice/healthCheck"

	val scn = scenario("RecordedSimulation")
		.exec(http("request_0")
			.post("/Sql/DemoAccountingMicroservice/healthCheck")
			.headers(headers_0)
			.body(RawFileBody("RecordedSimulation_0000_request.txt"))
			.check(status.is(200)))

	setUp(scn.inject(atOnceUsers(1))).protocols(httpProtocol)
}