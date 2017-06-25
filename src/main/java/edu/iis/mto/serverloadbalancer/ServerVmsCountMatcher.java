package edu.iis.mto.serverloadbalancer;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

/**
 * Created by grusz on 25.06.2017.
 */
public class ServerVmsCountMatcher extends TypeSafeMatcher<Server> {
    private double expectedVmCount;

    public ServerVmsCountMatcher(double expectedVmCount) {
        this.expectedVmCount = expectedVmCount;
    }

    protected boolean matchesSafely(Server server) {
        return expectedVmCount == server.countVms();
    }

    public void describeTo(Description description) {
        description.appendText("A server with vms count of").appendValue(expectedVmCount);
    }

    @Override
    protected void describeMismatchSafely(Server item, Description mismatchDescription) {
        mismatchDescription.appendText("A server with vms count of").appendValue(item.countVms());
    }
}
