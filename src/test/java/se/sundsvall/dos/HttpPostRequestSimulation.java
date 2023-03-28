package se.sundsvall.dos;

import static io.gatling.javaapi.core.CoreDsl.StringBody;
import static io.gatling.javaapi.core.CoreDsl.constantUsersPerSec;
import static io.gatling.javaapi.core.CoreDsl.scenario;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;
import static java.nio.file.Files.readString;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.time.Duration;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;
import java.util.stream.Stream;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpDsl;
import io.gatling.javaapi.http.HttpProtocolBuilder;


public class HttpPostRequestSimulation extends Simulation {

	private final String SIMULATION_NAME = this.getClass().getSimpleName();
	private Config conf = ConfigFactory.load("simulation.conf");

	AtomicInteger id = new AtomicInteger(1000000);

	Iterator<Map<String, Object>> feeder = Stream.generate((Supplier<Map<String, Object>>) () -> {
		Integer i = id.getAndIncrement();
		return Collections.singletonMap("replace", i); // "${replace}" in body will be replaced unique id if feeder is added to scenario
	}).iterator();

	// Protocol Definition
	private HttpProtocolBuilder httpProtocol = HttpDsl.http
		.baseUrl(conf.getString(SIMULATION_NAME + ".url"))
		.acceptHeader(conf.getString(SIMULATION_NAME + ".acceptHeader"))
		.contentTypeHeader(conf.getString(SIMULATION_NAME + ".contentTypeHeader"))
		.authorizationHeader(conf.getString(SIMULATION_NAME + ".authorizationHeader"))
		.userAgentHeader("Gatling/Performance Test");

	// Scenario
	private ScenarioBuilder scenario = scenario(conf.getString(SIMULATION_NAME + ".scenarioName"))
		//.feed(feeder)
		.exec(http("request_1")
			.post("")
			.body(StringBody(conf.getString(SIMULATION_NAME + ".requestBody")))
			.check(status().is(conf.getInt(SIMULATION_NAME + ".expextedStatusCode"))));

	public HttpPostRequestSimulation() {
		this.setUp(scenario.injectOpen(constantUsersPerSec(conf.getInt(SIMULATION_NAME + ".constant.usersPerSec"))
			.during(Duration.ofSeconds(conf.getInt(SIMULATION_NAME + ".constant.duringSeconds")))))
			.protocols(httpProtocol);
	}

	//Helper method to read payload from file if it is too big for config files
	private String fromFile(final String resourceName) {
		try {
			return readString(Path.of(ClassLoader.getSystemResource(resourceName).toURI()));
		} catch (IOException e) {
			return null;
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}
}
