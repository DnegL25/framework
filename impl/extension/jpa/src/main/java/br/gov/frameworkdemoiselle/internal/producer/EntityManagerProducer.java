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
package br.gov.frameworkdemoiselle.internal.producer;

import java.io.Serializable;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.gov.frameworkdemoiselle.annotation.Name;
import br.gov.frameworkdemoiselle.internal.configuration.EntityManagerConfig;
import br.gov.frameworkdemoiselle.internal.proxy.EntityManagerProxy;

/**
 * <p>
 * Factory class responsible to produces instances of EntityManager. Produces instances based on informations defined in
 * persistence.xml, demoiselle.properties or @PersistenceUnit annotation.
 * </p>
 */
@ApplicationScoped
public class EntityManagerProducer implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Instance<EntityManagerStore> storeInstance;

	@Inject
	private EntityManagerConfig config;

	@Inject
	private Persistences persistenceUnitReader;

	/**
	 * <p>
	 * Default EntityManager factory. Tries two strategies to produces EntityManager instances.
	 * <li>The first one is based on informations available on demoiselle properties file
	 * ("frameworkdemoiselle.persistence.unit.name" key).</li>
	 * <li>The second one is based on persistence.xml file. If exists only one Persistence Unit defined, this one is
	 * used.</li>
	 * 
	 * @param config
	 *            Suplies informations about EntityManager defined in properties file.
	 * @return Produced EntityManager.
	 */
	@Default
	@Produces
	protected EntityManager createDefault() {
		String persistenceUnit = persistenceUnitReader.getFromProperties(config);

		if (persistenceUnit == null) {
			persistenceUnit = persistenceUnitReader.getFromXML();
		}

		return new EntityManagerProxy(persistenceUnit);
	}

	/**
	 * <p>
	 * Factory that reads the {@link AmbiguousQualifier} qualifier and creates an entity manager with a matching
	 * persistence unit name.
	 * </p>
	 * 
	 * @param config
	 *            Suplies informations about EntityManager defined in properties file.
	 * @return Produced EntityManager.
	 */
	@Name("")
	@Produces
	protected EntityManager createNamed(InjectionPoint ip) {
		String persistenceUnit = ip.getAnnotated().getAnnotation(Name.class).value();
		return new EntityManagerProxy(persistenceUnit);
	}

	public EntityManager getEntityManager(String persistenceUnit) {
		return getStore().getEntityManager(persistenceUnit);
	}

	public Map<String, EntityManager> getCache() {
		return getStore().getCache();
	}

	private EntityManagerStore getStore() {
		switch (config.getEntityManagerScope()) {
			case REQUEST:
				return storeInstance.select(RequestEntityManagerStore.class).get();
			case APPLICATION:
				return storeInstance.select(ApplicationEntityManagerStore.class).get();
			case CONVERSATION:
				return storeInstance.select(ConversationEntityManagerStore.class).get();
			case NOSCOPE:
				return storeInstance.select(DependentEntityManagerStore.class).get();
			case SESSION:
				return storeInstance.select(SessionEntityManagerStore.class).get();
			case VIEW:
				return storeInstance.select(ViewEntityManagerStore.class).get();
			default:
				return storeInstance.select(RequestEntityManagerStore.class).get();
		}
	}

}
