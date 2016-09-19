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
package org.demoiselle.util;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Set;
import javax.inject.Named;

/**
 * <p>
 * The Demoiselle's ResourceBundle extends the abstraction
 * {@link java.util.ResourceBundle}, and provide the locale and the base name
 * for the bundle.</p>
 *
 * <p>
 * To select which resource properties file to load when injecting beans of this
 * class, qualify the injection point with
 * {@link org.demoiselle.annotation.Name}, using the resource name (without the
 * '.properties' extension) as the value. If the injection point isn't qualified
 * the default file <code>messages.properties</code> will be loaded from the
 * root of the classpath.</p>
 *
 * @author SERPRO
 */
public class ResourceBundle extends java.util.ResourceBundle implements Serializable {

    private static final long serialVersionUID = 1L;

    private String baseName;

    private transient java.util.ResourceBundle delegate;

    private final Locale locale;

    private java.util.ResourceBundle getDelegate() {
        if (delegate == null) {
            try {
                ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
                delegate = ResourceBundle.getBundle(baseName, locale, classLoader);

            } catch (MissingResourceException mre) {
                delegate = ResourceBundle.getBundle(baseName, locale);
            }
        }

        return delegate;
    }

    /**
     * Constructor that set values of baseName and locale.
     *
     * @param baseName the base name to construct the complete bundle name.
     *
     * @param locale locale to define the choosen bundle.
     */
    public ResourceBundle(String baseName, Locale locale) {
        this.baseName = baseName;
        this.locale = locale;
    }

    @Override
    public boolean containsKey(String key) {
        return getDelegate().containsKey(key);
    }

    @Override
    public Enumeration<String> getKeys() {
        return getDelegate().getKeys();
    }

    @Override
    public Locale getLocale() {
        return getDelegate().getLocale();
    }

    @Override
    public Set<String> keySet() {
        return getDelegate().keySet();
    }

    public String getString(String key, Object... params) {
        return Strings.getString(getString(key), params);
    }

    @Override
    protected Object handleGetObject(String key) {
        Object result;

        try {
            Method method = getDelegate().getClass().getMethod("handleGetObject", String.class);

            method.setAccessible(true);
            result = method.invoke(delegate, key);
            method.setAccessible(false);

        } catch (Exception cause) {
            throw new RuntimeException(cause);
        }

        return result;
    }
}
