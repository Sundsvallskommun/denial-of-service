package se.sundsvall.dos;

import static io.gatling.javaapi.core.CoreDsl.StringBody;
import static io.gatling.javaapi.core.CoreDsl.constantUsersPerSec;
import static io.gatling.javaapi.core.CoreDsl.scenario;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;

import java.time.Duration;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpDsl;
import io.gatling.javaapi.http.HttpProtocolBuilder;

public class HttpPostRequestSimulation extends Simulation {

	private final String SIMULATION_NAME = this.getClass().getSimpleName();
	private Config conf = ConfigFactory.load("simulation.conf");

	// Protocol Definition
	private HttpProtocolBuilder httpProtocol = HttpDsl.http
		.baseUrl(conf.getString(SIMULATION_NAME + ".url"))
		.acceptHeader(conf.getString(SIMULATION_NAME + ".acceptHeader"))
		.contentTypeHeader(conf.getString(SIMULATION_NAME + ".contentTypeHeader"))
		.authorizationHeader(conf.getString(SIMULATION_NAME + ".authorizationHeader"))
		.userAgentHeader("Gatling/Performance Test");

	// Scenario
	private ScenarioBuilder scenario = scenario(conf.getString(SIMULATION_NAME + ".scenarioName"))
		.exec(http("request_1")
			.post("")
			.body(StringBody(conf.getString(SIMULATION_NAME + ".requestBody")))
			.check(status().is(conf.getInt(SIMULATION_NAME + ".expextedStatusCode"))));

	public HttpPostRequestSimulation() {
		this.setUp(scenario.injectOpen(constantUsersPerSec(conf.getInt(SIMULATION_NAME + ".constant.usersPerSec"))
			.during(Duration.ofSeconds(conf.getInt(SIMULATION_NAME + ".constant.duringSeconds")))))
			.protocols(httpProtocol);
	}
}
