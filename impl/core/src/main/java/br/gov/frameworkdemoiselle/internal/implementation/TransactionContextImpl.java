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
package br.gov.frameworkdemoiselle.internal.implementation;

import javax.inject.Named;

import br.gov.frameworkdemoiselle.internal.configuration.TransactionConfig;
import br.gov.frameworkdemoiselle.transaction.Transaction;
import br.gov.frameworkdemoiselle.transaction.TransactionContext;
import br.gov.frameworkdemoiselle.util.Beans;
import br.gov.frameworkdemoiselle.util.StrategyQualifier;

/**
 * This is the default implementation of {@link TransactionContext} interface.
 * 
 * @author SERPRO
 */
@Named("transactionContext")
public class TransactionContextImpl implements TransactionContext {

	private static final long serialVersionUID = 1L;

	private Transaction transaction;

	@Override
	public Transaction getCurrentTransaction() {
		if (this.transaction == null) {
			Class<? extends Transaction> type = getConfig().getTransactionClass();

			if (type != null) {
				this.transaction = Beans.getReference(type);
			} else {
				this.transaction = Beans.getReference(Transaction.class, new StrategyQualifier());
			}
		}

		return this.transaction;
	}

	private TransactionConfig getConfig() {
		return Beans.getReference(TransactionConfig.class);
	}
}
