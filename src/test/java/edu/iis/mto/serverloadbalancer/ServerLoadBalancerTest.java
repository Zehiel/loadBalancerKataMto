package edu.iis.mto.serverloadbalancer;


import static edu.iis.mto.serverloadbalancer.CurrentLoadPercentageMatcher.hasCurrentLoadOf;
import static edu.iis.mto.serverloadbalancer.ServerBuilder.server;
import static edu.iis.mto.serverloadbalancer.ServerVmsCountMatcher.hasVmCountOf;
import static edu.iis.mto.serverloadbalancer.VmBuilder.vm;
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

    @Test
    public void balanceServerWithTenSlotCapacity_andOneSlotVm_fillsServerWithTenPercents() throws Exception {

	    Server theServer = a(server().withCapacity(10));
	    Vm theVm = a(vm().ofSize(1));

	    balancing(ServerListWith(theServer),vmListWith(theVm));

	    assertThat(theServer,hasCurrentLoadOf(10.00d));
	    assertThat("Server should contain the vm",theServer.contains(theVm));

    }


    @Test
    public void balanceServerWithEnoughRoom_fillsServerWithAllVms() throws Exception {

	    Server theServer = a(server().withCapacity(10));
	    Vm theFirstVm = a(vm().ofSize(1));
        Vm theSecondVm = a(vm().ofSize(1));

	    balancing(ServerListWith(theServer),vmListWith(theFirstVm,theSecondVm));

	    assertThat(theServer,hasVmCountOf(2));
	    assertThat("Server should contain the vm",theServer.contains(theFirstVm));
        assertThat("Server should contain the vm",theServer.contains(theSecondVm));

    }

    @Test
    public void vmShouldBeBalancedToServerWithLowerLoad() throws Exception {

	    Server lessLoadedServer = a(server().withCapacity(100).withCurrentLoad(45.0d));
	    Server moreLoadedServer = a(server().withCapacity(100).withCurrentLoad(50.0d));
	    Vm theVm = a(vm().ofSize(10));

	    balancing(ServerListWith(lessLoadedServer,moreLoadedServer), vmListWith(theVm));


        assertThat("Server should contain the vm",lessLoadedServer.contains(theVm));
        assertThat("Server should not contain the vm",!moreLoadedServer.contains(theVm));

    }

    @Test
    public void balancingServerWithNotEnoughRoom_shouldNotBeFilledWithTheVm() throws Exception {
        Server theServer = a(server().withCapacity(10).withCurrentLoad(90.0d));
        Vm theVm = a(vm().ofSize(2));

        balancing(ServerListWith(theServer),vmListWith(theVm));

        assertThat("Server should not contain the vm",!theServer.contains(theVm));
    }

    @Test
    public void balancingServersAndVms() throws Exception {
        Server server1 = a(server().withCapacity(4));
        Server server2 = a(server().withCapacity(6));

        Vm vm1 = a(vm().ofSize(1));
        Vm vm2 = a(vm().ofSize(4));
        Vm vm3 = a(vm().ofSize(2));

        balancing(ServerListWith(server1,server2),vmListWith(vm1,vm2,vm3));

        assertThat("Server should contain the vm",server1.contains(vm1));
        assertThat("Server should contain the vm",server2.contains(vm2));
        assertThat("Server should contain the vm",server1.contains(vm3));

        assertThat(server1,hasCurrentLoadOf(75.0d));
        assertThat(server2,hasCurrentLoadOf(66.66d));
	}

    private void balancing(Server[] servers, Vm[] vms) {
	    new ServerLoadBalancer().balance(servers,vms);
    }

    private Vm[] vmListWith(Vm... vms) {
        return vms;
    }


    private Vm[] anEmptyVmsList() {
	    return new Vm[0];
    }

    private Server[] ServerListWith(Server... servers) {
	    return servers;
    }


    private <T> T a(Builder<T> builder){
        return builder.build();
    }


}
