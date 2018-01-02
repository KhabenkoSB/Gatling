
import io.gatling.core.Predef._
import io.gatling.http.Predef._

class RecordedSimulation2 extends Simulation {

	val httpProtocol = http
		.baseURL("http://localhost:8080")
		.inferHtmlResources()
		.acceptHeader("*/*")
		.userAgentHeader("PostmanRuntime/7.1.1")

	val headers_0 = Map(
		"Postman-Token" -> "243f167e-c113-4e7a-ac14-f36fe73296ef",
		"accept-encoding" -> "gzip, deflate",
		"cache-control" -> "no-cache",
		"cookie" -> "JSESSIONID.542cce0f=node01rv2s4mo2roq9koqmamkxo5hs1.node0; JSESSIONID.7dae53d7=node0112kx2h2vhkvs1oggorqdvspbg1.node0")



	val scn = scenario("RecordedSimulation2")
		.exec(http("request_0")
			.get("/")
			.headers(headers_0)
			.check(status.is(403)))

	setUp(scn.inject(atOnceUsers(1))).protocols(httpProtocol)
}