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
package management.testclasses;

import java.util.UUID;

import javax.validation.constraints.NotNull;

import br.gov.frameworkdemoiselle.management.ManagedOperation;
import br.gov.frameworkdemoiselle.management.ManagedProperty;
import br.gov.frameworkdemoiselle.management.ManagedProperty.ManagedPropertyAccess;
import br.gov.frameworkdemoiselle.stereotype.ManagementController;
import br.gov.frameworkdemoiselle.util.Beans;

@ManagementController
public class DummyManagedClass {
	
	@ManagedProperty
	private String name;
	
	@ManagedProperty
	@NotNull
	private Integer id;
	
	@ManagedProperty
	@DummyValidatorAnnotation(allows={"f","m","F","M"})
	private String gender;
	
	@ManagedProperty
	private Integer firstFactor , secondFactor;
	
	@ManagedProperty
	private String uuid;
	
	@ManagedProperty
	private String writeOnlyProperty;
	
	@ManagedProperty
	private String readOnlyProperty = "Default Value";
	
	@ManagedProperty(accessLevel=ManagedPropertyAccess.READ_ONLY)
	private String readOnlyPropertyWithSetMethod = "Default Value";
	
	@ManagedProperty(accessLevel=ManagedPropertyAccess.WRITE_ONLY)
	private String writeOnlyPropertyWithGetMethod;
	
	/**
	 * Propriedade para testar detecção de métodos GET e SET quando propriedade tem apenas uma letra.
	 */
	@ManagedProperty
	private Integer a;
	
	/**
	 * Propriedade para testar detecção de métodos GET e SET quando propriedade tem apenas letras maiúsculas.
	 */
	@ManagedProperty
	private Integer MAIUSCULO;

	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getUuid() {
		return uuid;
	}
	
	public void setWriteOnlyProperty(String newValue){
		this.writeOnlyProperty = newValue;
	}
	
	public Integer getA() {
		return a;
	}

	public void setA(Integer a) {
		this.a = a;
	}
	
	public Integer getMAIUSCULO() {
		return MAIUSCULO;
	}

	
	public void setMAIUSCULO(Integer mAIUSCULO) {
		MAIUSCULO = mAIUSCULO;
	}

	@ManagedOperation(description="Generates a random UUID")
	public String generateUUID(){
		this.uuid = UUID.randomUUID().toString();
		return this.uuid;
	}

	
	public String getName() {
		return name;
	}

	
	public void setName(String name) {
		this.name = name;
	}

	
	public String getReadOnlyProperty() {
		return readOnlyProperty;
	}

	
	public Integer getFirstFactor() {
		return firstFactor;
	}

	
	public void setFirstFactor(Integer firstFactor) {
		this.firstFactor = firstFactor;
	}

	
	public Integer getSecondFactor() {
		return secondFactor;
	}

	
	public void setSecondFactor(Integer secondFactor) {
		this.secondFactor = secondFactor;
	}
	
	@ManagedOperation
	public Integer sumFactors(){
		return (firstFactor!=null ? firstFactor.intValue() : 0) + (secondFactor!=null ? secondFactor.intValue() : 0);
	}
	
	@ManagedOperation
	public synchronized Integer calculateFactorsSynchronized(Integer firstFactor , Integer secondFactor){
		setFirstFactor(firstFactor);
		setSecondFactor(secondFactor);
		
		try {
			int temp = firstFactor + secondFactor;
			Thread.sleep( (long)(Math.random() * 100));
			
			temp = temp + firstFactor;
			Thread.sleep( (long)(Math.random() * 100));
			
			temp = temp + secondFactor;
			Thread.sleep( (long)(Math.random() * 100));
			
			return temp;
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void nonOperationAnnotatedMethod(){
		System.out.println("Test");
	}

	
	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}
	
	public String getReadOnlyPropertyWithSetMethod() {
		return readOnlyPropertyWithSetMethod;
	}

	public void setReadOnlyPropertyWithSetMethod(String readOnlyPropertyWithSetMethod) {
		this.readOnlyPropertyWithSetMethod = readOnlyPropertyWithSetMethod;
	}

	public String getWriteOnlyPropertyWithGetMethod() {
		return writeOnlyPropertyWithGetMethod;
	}

	public void setWriteOnlyPropertyWithGetMethod(String writeOnlyPropertyWithGetMethod) {
		this.writeOnlyPropertyWithGetMethod = writeOnlyPropertyWithGetMethod;
	}

	@ManagedOperation
	public String requestScopedOperation(){
		RequestScopeBeanClient client = Beans.getReference(RequestScopeBeanClient.class);
		client.operationOne();
		client.operationTwo();
		
		RequestScopedClass bean = Beans.getReference(RequestScopedClass.class);
		return bean.getInfo();
	}
	
	
}
