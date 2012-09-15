package org.brains3;

import org.junit.After;
import org.junit.Before;
import play.test.FakeApplication;
import play.test.Helpers;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Joel Soderstrom (joel[at]frostdigital[dot]se)
 * Date: 2012-09-09
 * Time: 7:22 PM
 */
public abstract class BaseIntegrationTest {

    public static FakeApplication app;

    @Before
    public void startApp() throws IOException {
        app = Helpers.fakeApplication();
        Helpers.start(app);
    }

    @After
    public void stopApp() {
        Helpers.stop(app);
    }

}
