package it.jugpadova;

import it.jugpadova.dao.*;
import it.jugpadova.po.Event;
import it.jugpadova.po.Jugger;
import org.parancoe.plugins.security.Authority;
import org.parancoe.plugins.security.User;
import org.parancoe.plugins.security.UserAuthority;
import org.parancoe.plugins.world.Continent;
import org.parancoe.plugins.world.Country;
import org.parancoe.web.test.BaseTest;

/**
 * Base test class for test of JUG Events.
 *
 * @author lucio
 */
public abstract class JugEventsBaseTest extends BaseTest {

    @Override
    public Class[] getFixtureClasses() {
        return new Class[]{Continent.class, Country.class, User.class, Authority.class, UserAuthority.class, Jugger.class, Event.class};
    }
}
