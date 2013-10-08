/*
 * Demoiselle Framework
 * Copyright (C) 2010 SERPRO
 * ----------------------------------------------------------------------------
 * This file is part of Demoiselle Framework.
 * 
 * Demoiselle Framework is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License version 3
 * as published by the Free Software Foundation.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License version 3
 * along with this program; if not,  see <http://www.gnu.org/licenses/>
 * or write to the Free Software Foundation, Inc., 51 Franklin Street,
 * Fifth Floor, Boston, MA  02110-1301, USA.
 * ----------------------------------------------------------------------------
 * Este arquivo é parte do Framework Demoiselle.
 * 
 * O Framework Demoiselle é um software livre; você pode redistribuí-lo e/ou
 * modificá-lo dentro dos termos da GNU LGPL versão 3 como publicada pela Fundação
 * do Software Livre (FSF).
 * 
 * Este programa é distribuído na esperança que possa ser útil, mas SEM NENHUMA
 * GARANTIA; sem uma garantia implícita de ADEQUAÇÃO a qualquer MERCADO ou
 * APLICAÇÃO EM PARTICULAR. Veja a Licença Pública Geral GNU/LGPL em português
 * para maiores detalhes.
 * 
 * Você deve ter recebido uma cópia da GNU LGPL versão 3, sob o título
 * "LICENCA.txt", junto com esse programa. Se não, acesse <http://www.gnu.org/licenses/>
 * ou escreva para a Fundação do Software Livre (FSF) Inc.,
 * 51 Franklin St, Fifth Floor, Boston, MA 02111-1301, USA.
 */
package security.authorization.custom;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import security.athentication.custom.CustomAuthenticator;
import test.Tests;
import br.gov.frameworkdemoiselle.security.SecurityContext;
import configuration.resource.ConfigurationResourceTest;

@RunWith(Arquillian.class)
public class CustomAuthorizerTest {

	@Inject
	private SecurityContext context;

	@Deployment
	public static JavaArchive createDeployment() {
		JavaArchive deployment = Tests.createDeployment(ConfigurationResourceTest.class);
		deployment.addClass(CustomAuthenticator.class);
		deployment.addClass(CustomAuthorizer.class);
		return deployment;
	}

	@Before
	public void loginToTest() {
		context.login();
	}

	@Test
	public void hasPermission() {
		assertTrue(context.hasPermission("resource", "operation"));
	}

	@Test
	public void hasRole() {
		assertTrue(context.hasRole("role"));
	}

	/**
	 * Verify if when already exist an authorizer, the things keeps working fine.
	 */
	@Test
	public void hasPermitionAndHasRole() {
		assertTrue(context.hasPermission("resource", "operation"));
		assertTrue(context.hasRole("role"));
	}

	@Test
	public void denyPermission() {
		assertFalse(context.hasPermission("falseresource", "falseoperation"));
	}

	@Test
	public void denyRole() {
		assertFalse(context.hasRole("falserole"));
	}

	@After
	public void logoutAfterTest() {
		context.logout();
	}
}
