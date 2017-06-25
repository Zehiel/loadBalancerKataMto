package edu.iis.mto.serverloadbalancer;


import static edu.iis.mto.serverloadbalancer.CurrentLoadPercentageMatcher.hasCurrentLoadOf;
import static edu.iis.mto.serverloadbalancer.ServerBulder.server;
import static edu.iis.mto.serverloadbalancer.ServerVmsCountMatcher.hasAVmsCoundOf;
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
	public void balancingServerWithNoVms_serverStaysEmpty() throws Exception {

		Server theServer = a(server().withCapacity(1));

		balancing(aServerListWith(theServer), anEmptyListOfVms());

		assertThat(theServer, hasCurrentLoadOf(0.0d));

	}

	@Test
	public void balancingServerWithOneSlotCapacity_andOneSlotVm_fillsServerWithTheVm() throws Exception {

		Server theServer = a(server().withCapacity(1));
		Vm theVm = a(vm().ofSize(1));

		balancing(aServerListWith(theServer), aVmsListWith(theVm));

		assertThat(theServer, hasCurrentLoadOf(100.0d));
		assertThat("theServer should contain theVm",theServer.contains(theVm));
	}

	@Test
	public void balancingServerWithTenSlotCapacity_andOneSlotVm_fillsServerWithTenPercents() throws Exception {

		Server theServer = a(server().withCapacity(10));
		Vm theVm = a(vm().ofSize(1));

		balancing(aServerListWith(theServer), aVmsListWith(theVm));

		assertThat(theServer, hasCurrentLoadOf(10.0d));
		assertThat("theServer should contain theVm",theServer.contains(theVm));
	}

	@Test
	public void balancingServerWithEnoughRoom_fillsTheServerWithAllVms() throws Exception {

		Server theServer = a(server().withCapacity(100));
		Vm theFirstVm = a(vm().ofSize(1));
        Vm theSecondVm = a(vm().ofSize(1));

		balancing(aServerListWith(theServer), aVmsListWith(theFirstVm,theFirstVm));

		assertThat(theServer, hasAVmsCoundOf(2));
		assertThat("theServer should contain theFirstVm",theServer.contains(theFirstVm));
		assertThat("theServer should contain theSecondVm",theServer.contains(theSecondVm));

	}

	@Test
	public void vmShouldBeBalancedOnLessLoadedServerFirst() throws Exception {

		Server moreLoadedServer = a(server().withCapacity(100).withCurrentLoadOf(50.0d));
		Server lessLoadedServer = a(server().withCapacity(100).withCurrentLoadOf(45.0d));
		Vm theVm = a(vm().ofSize(10));

		balancing(aServerListWith(moreLoadedServer,lessLoadedServer), aVmsListWith(theVm));

		assertThat(moreLoadedServer, hasAVmsCoundOf(0));
		assertThat(lessLoadedServer, hasAVmsCoundOf(1));
		assertThat("lessLoadedServer should contain theFirstVm",lessLoadedServer.contains(theVm));
		assertThat("moreLoadedServer should not contain theSecondVm",!moreLoadedServer.contains(theVm));

	}




    private void balancing(Server[] servers, Vm[] vms) {
		new ServlerLoadBalancer().balance(servers,vms);
	}

	private Server[] aServerListWith(Server... servers) {
		return servers;
	}

	private Vm[] aVmsListWith(Vm... vms) {
		return vms;
	}

	private Vm[] anEmptyListOfVms() {
		return new Vm[0];
	}

	private <T> T a(Builder<T> builder){
		return builder.build();
	}




}
