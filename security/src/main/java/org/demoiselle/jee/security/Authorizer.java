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
package org.demoiselle.jee.security;

import java.io.Serializable;

/**
 * <p>
 * Defines the methods that should be implemented by anyone who wants an authorization mechanism.
 * </p>
 *
 * @author SERPRO
 */
public interface Authorizer extends Serializable {

	/**
	 * <p>
	 * Checks if the logged user has a specific role.
	 * </p>
	 *
	 * @param role role to be checked.
	 * @return {@code true} if the user has the role.
	 * @throws Exception if the underlying permission checking mechanism throwns any other exception,
	 * 					just throw it and leave the security context implementation to handle it.
	 */
	boolean hasRole(String role) throws Exception;

	/**
	 * <p>
	 * Checks if the logged user has permission to execute a specific operation on a specific resource.
	 * </p>
	 *
	 * @param resource resource to be checked.
	 * @param operation operation to be checked.
	 * @return {@code true} if the user has the permission.
	 * @throws Exception if the underlying permission checking mechanism throwns any other exception,
	 * 					just throw it and leave the security context implementation to handle it.
	 */
	boolean hasPermission(String resource, String operation) throws Exception;

}
