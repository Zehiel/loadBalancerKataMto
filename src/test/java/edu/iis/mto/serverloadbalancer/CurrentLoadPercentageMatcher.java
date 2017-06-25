package edu.iis.mto.serverloadbalancer;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

/**
 * Created by grusz on 25.06.2017.
 */
public class CurrentLoadPercentageMatcher extends TypeSafeMatcher<Server> {
    public static final double EPSILON = 0.01d;
    private double expectedLoadPercentage;

    public CurrentLoadPercentageMatcher(double expectedLoadPercentage) {
        this.expectedLoadPercentage = expectedLoadPercentage;
    }

    protected boolean matchesSafely(Server server) {
        return doublesAreEqual(expectedLoadPercentage, server.currentLoadPercentage);
    }

    private boolean doublesAreEqual(double d1, double d2) {
        return d2 == d1 || Math.abs(d2 - d1) < EPSILON;
    }

    public void describeTo(Description description) {
        description.appendText("A server with current load percentage of").appendValue(expectedLoadPercentage);
    }

    @Override
    protected void describeMismatchSafely(Server item, Description mismatchDescription) {
        mismatchDescription.appendText("A server with current load percentage of").appendValue(item.currentLoadPercentage);
    }

    public static CurrentLoadPercentageMatcher hasCurrentLoadOf(double expectedLoadPercentage) {
        return new CurrentLoadPercentageMatcher(expectedLoadPercentage);
    }
}
