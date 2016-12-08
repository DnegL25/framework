/*
 * Demoiselle Framework
 *
 * License: GNU Lesser General Public License (LGPL), version 3 or later.
 * See the lgpl.txt file in the root directory or <https://www.gnu.org/licenses/lgpl.html>.
 */
package org.demoiselle.jee.security;

import static java.util.UUID.randomUUID;
import static javax.ws.rs.Priorities.AUTHENTICATION;

import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Priority;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.demoiselle.jee.core.api.security.DemoisellePrincipal;
import org.demoiselle.jee.core.api.security.Token;
import org.demoiselle.jee.core.api.security.TokenManager;

/**
 *
 * @author 70744416353
 */
@ApplicationScoped
@Priority(AUTHENTICATION)
public class TokenManagerMock implements TokenManager {

    private ConcurrentHashMap<String, DemoisellePrincipal> repo = new ConcurrentHashMap<>();

    @Inject
    private Token token;

    /**
     *
     * @return
     */
    @Override
    public DemoisellePrincipal getUser() {
        if (token.getKey() != null && !token.getKey().isEmpty()) {
            return repo.get(token.getKey());
        }
        return null;
    }

    /**
     *
     * @param user
     */
    @Override
    public void setUser(DemoisellePrincipal user) {
        token.setKey(null);

        repo.entrySet().stream().filter((entry) -> (entry.getValue().getIdentity().equalsIgnoreCase(user.getIdentity()))).forEach((entry) -> {
            token.setKey(entry.getKey());
        });

        if (token.getKey() == null) {
            String value = randomUUID().toString();
            repo.putIfAbsent(value, user.clone());
            token.setKey(value);
        }

        token.setType("Token");
    }

    /**
     *
     * @return
     */
    @Override
    public boolean validate() {
        return getUser() != null;
    }

    public void removeToken() {
        repo.remove(token.getKey());
    }

    @Override
    public void removeUser(DemoisellePrincipal user) {
        repo.entrySet().stream().filter((entry) -> (entry.getValue().getIdentity().equalsIgnoreCase(user.getIdentity()))).forEach((entry) -> {
            token.setKey(entry.getKey());
        });
        removeToken();
    }

}
