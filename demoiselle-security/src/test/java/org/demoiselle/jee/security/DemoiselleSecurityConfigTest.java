/*
 * Demoiselle Framework
 *
 * License: GNU Lesser General Public License (LGPL), version 3 or later.
 * See the lgpl.txt file in the root directory or <https://www.gnu.org/licenses/lgpl.html>.
 */
package org.demoiselle.jee.security;

import static org.junit.Assert.assertEquals;

import javax.inject.Inject;

import org.apache.deltaspike.testcontrol.api.junit.CdiTestRunner;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 *
 * @author 70744416353
 */
@RunWith(CdiTestRunner.class)
public class DemoiselleSecurityConfigTest {

    @Inject
    private DemoiselleSecurityConfig instance;

    public DemoiselleSecurityConfigTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of isCorsEnabled method, of class DemoiselleSecurityConfig.
     */
    @Test
    public void testIsCorsEnabled() {
        System.out.println("isCorsEnabled");
        boolean expResult = true;
        boolean result = instance.isCorsEnabled();
        assertEquals(expResult, result);
    }

    /**
     * Test of setCorsEnabled method, of class DemoiselleSecurityConfig.
     */
    @Test
    public void testSetCorsEnabled() {
        System.out.println("setCorsEnabled");
        boolean corsEnabled = false;
        instance.setCorsEnabled(corsEnabled);
        assertEquals(instance.isCorsEnabled(), corsEnabled);
    }

}
