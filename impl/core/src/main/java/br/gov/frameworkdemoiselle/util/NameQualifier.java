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
package br.gov.frameworkdemoiselle.util;

import javax.enterprise.util.AnnotationLiteral;

import util.beans.ambiguous.AmbiguousQualifier;
import br.gov.frameworkdemoiselle.annotation.Name;

/**
 * Annotation litteral that allows to create instances of the {@link Name} qualifier.
 * The created instance can then be used to call {@link Beans#getReference(Class type, Annotation... qualifiers)}.
 *  
 * @see Beans
 * @see AmbiguousQualifier
 * 
 * @author SERPRO
 */
@SuppressWarnings("all")
public class NameQualifier extends AnnotationLiteral<Name> implements Name {

	private static final long serialVersionUID = 1L;

	private final String value;

	/**
	 * Constructor with string value of name qualifier.
	 * 
	 * @param value
	 * 			value of name qualifier.
	 */
	public NameQualifier(String value) {
		this.value = value;
	}

	@Override
	public String value() {
		return this.value;
	}
}
