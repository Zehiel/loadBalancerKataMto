package edu.iis.mto.serverloadbalancer;


import static edu.iis.mto.serverloadbalancer.CurrentLoadPercentageMatcher.hasCurrentLoadOf;
import static edu.iis.mto.serverloadbalancer.ServerBuilder.server;
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
    public void balanceServerWithNoVms_serverStaysEmpty() throws Exception {

	    Server theServer = a(server().withCapacity(1));

	    balancing(ServerListWith(theServer),anEmptyVmsList());

	    assertThat(theServer,hasCurrentLoadOf(0.00d));

    }

    @Test
    public void balanceServerWithOneSlotCapacity_andOneSlotVm_fillsServerWithTheVm() throws Exception {

	    Server theServer = a(server().withCapacity(1));
	    Vm theVm = a(vm().ofSize(1));

	    balancing(ServerListWith(theServer),vmListWith(theVm));

	    assertThat(theServer,hasCurrentLoadOf(100.00d));
	    assertThat("Server should contain the vm",theServer.contains(theVm));

    }



    private void balancing(Server[] servers, Vm[] vms) {
	    new ServerLoadBalancer().balance(servers,vms);
    }

    private Vm[] anEmptyVmsList() {
	    return new Vm[0];
    }

    private Server[] ServerListWith(Server... servers) {
	    return servers;
    }

    private Server a(ServerBuilder serverBuilder) {
	    return serverBuilder.build();
    }


}
