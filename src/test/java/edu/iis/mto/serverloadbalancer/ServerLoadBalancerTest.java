package edu.iis.mto.serverloadbalancer;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.hamcrest.Matcher;
import org.junit.Test;

public class ServerLoadBalancerTest {
	@Test
	public void itCompiles() {
		assertThat(true, equalTo(true));
	}

	@Test
	public void balancingServerWithNoVms_serverStaysEmpty() throws Exception {

		Server theServer = a(server().withCapacity(1));

		balancing(aServerListWith(theServer), anEmptyListOfVms());

		assertThat(theServer, hasCurrentLoadOf(0.0d));

	}

	private Matcher<? super Server> hasCurrentLoadOf(double expectedLoadPercentage) {
		return new CurrentLoadPercentageMatcher(expectedLoadPercentage);

	}

	private void balancing(Server[] servers, Vm[] vms) {
		new ServlerLoadBalancer().balance(servers,vms);
	}

	private Vm[] anEmptyListOfVms() {
		return new Vm[0];
	}

	private Server[] aServerListWith(Server... servers) {
		return servers;
	}

	private Server a(ServerBulder serverBulder) {
		return serverBulder.build();
	}

	private ServerBulder server() {
		return new ServerBulder();
	}
}
