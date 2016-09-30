/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.demoiselle.jee.security.jwt.impl;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import org.demoiselle.jee.core.interfaces.security.DemoisellePrincipal;
import org.demoiselle.jee.core.interfaces.security.Token;
import org.demoiselle.jee.core.interfaces.security.TokensManager;
import org.jose4j.jwk.JsonWebKey;
import org.jose4j.jwk.RsaJsonWebKey;
import org.jose4j.jwk.RsaJwkGenerator;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.lang.JoseException;

/**
 *
 * @author 70744416353
 */
@RequestScoped
public class TokensManagerImpl implements TokensManager {

    @Inject
    private HttpServletRequest httpRequest;

    private static RsaJsonWebKey rsaJsonWebKey;

    @Inject
    private Logger logger;

    @Inject
    private Token token;

    @Inject
    private DemoisellePrincipal loggedUser;

    public TokensManagerImpl() throws JoseException {
        if (rsaJsonWebKey == null) {
            String chave = RsaJwkGenerator.generateJwk(2048).toJson(JsonWebKey.OutputControlLevel.INCLUDE_PRIVATE);
            rsaJsonWebKey = (RsaJsonWebKey) RsaJsonWebKey.Factory.newPublicJwk(chave);
            rsaJsonWebKey.setKeyId("demoiselle-security-jwt");
        }
    }

    @Override
    public DemoisellePrincipal getUser() {
        if (token.getKey() != null && !token.getKey().isEmpty()) {
            try {
                JwtConsumer jwtConsumer = new JwtConsumerBuilder()
                        .setRequireExpirationTime() // the JWT must have an expiration time
                        .setAllowedClockSkewInSeconds(60) // allow some leeway in validating time based claims to account for clock skew
                        .setExpectedIssuer("demoiselle") // whom the JWT needs to have been issued by
                        .setExpectedAudience("demoiselle") // to whom the JWT is intended for
                        .setDecryptionKey(rsaJsonWebKey.getPrivateKey()) // decrypt with the receiver's private key
                        .setVerificationKey(rsaJsonWebKey.getPublicKey())
                        .build(); // create the JwtConsumer instance
                JwtClaims jwtClaims = jwtConsumer.processToClaims(token.getKey());
                loggedUser.setId((String) jwtClaims.getClaimValue("id"));
                loggedUser.setName((String) jwtClaims.getClaimValue("name"));
                loggedUser.setRoles((List) jwtClaims.getClaimValue("roles"));
                loggedUser.setPermissions((Map) jwtClaims.getClaimValue("permissions"));
                //loggedUser = new Gson().fromJson((String) jwtClaims.getClaimValue("user"), DemoisellePrincipal.class);
                String ip = httpRequest.getRemoteAddr();
                if (!ip.equalsIgnoreCase((String) jwtClaims.getClaimValue("ip"))) {
                    return null;
                }
                return loggedUser;
            } catch (InvalidJwtException ex) {
                loggedUser = null;
                token.setKey(null);
                logger.severe(ex.getMessage());
            }
        }
        return null;
    }

    @Override
    public void setUser(DemoisellePrincipal user) {
        try {
            JwtClaims claims = new JwtClaims();
            claims.setIssuer("demoiselle");
            claims.setAudience("demoiselle");
            claims.setExpirationTimeMinutesInTheFuture(720);
            claims.setGeneratedJwtId();
            claims.setIssuedAtToNow();
            claims.setNotBeforeMinutesInThePast(1);

            claims.setClaim("ip", httpRequest.getRemoteAddr());
            claims.setClaim("id", (user.getId()));
            claims.setClaim("name", (user.getName()));
            claims.setClaim("roles", (user.getRoles()));
            claims.setClaim("permissions", (user.getPermissions()));

            JsonWebSignature jws = new JsonWebSignature();
            jws.setPayload(claims.toJson());
            jws.setKey(rsaJsonWebKey.getRsaPrivateKey());
            jws.setKeyIdHeaderValue(rsaJsonWebKey.getKeyId());
            jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.RSA_USING_SHA256);
            token.setKey(jws.getCompactSerialization());
            token.setType("JWT");
        } catch (JoseException ex) {
            //ex.printStackTrace();
            logger.severe(ex.getMessage());
        }

    }

    @Override
    public boolean validate() {
        return getUser() != null;
    }

}