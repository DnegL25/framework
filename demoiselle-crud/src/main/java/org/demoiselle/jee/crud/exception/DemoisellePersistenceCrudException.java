/*
 * Demoiselle Framework
 *
 * License: GNU Lesser General Public License (LGPL), version 3 or later.
 * See the lgpl.txt file in the root directory or <https://www.gnu.org/licenses/lgpl.html>.
 */
package org.demoiselle.jee.crud.exception;

import org.demoiselle.jee.rest.exception.DemoiselleRestException;

/**
 *
 * Exception for all persistence errors in Framework.
 *
 * @author SERPRO
 */
public class DemoisellePersistenceCrudException extends DemoiselleRestException {

    private static final long serialVersionUID = 1L;

    public DemoisellePersistenceCrudException() {
    }

    public DemoisellePersistenceCrudException(String message) {
        super(message);
    }

    public DemoisellePersistenceCrudException(Throwable cause) {
        super(cause);
    }

    public DemoisellePersistenceCrudException(String message, Throwable cause) {
        super(message, cause);
    }

}