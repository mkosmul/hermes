package pl.allegro.tech.hermes.management.api.auth;

import pl.allegro.tech.hermes.api.OwnerId;
import pl.allegro.tech.hermes.management.api.auth.SecurityProvider.OwnershipResolver;
import pl.allegro.tech.hermes.management.domain.auth.RequestUser;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.SecurityContext;

public class HermesSecurityAwareRequestUser implements RequestUser {
    private final String username;
    private final boolean isAdmin;
    private final OwnershipResolver ownershipResolver;

    public HermesSecurityAwareRequestUser(ContainerRequestContext requestContext) {
        SecurityContext securityContext = requestContext.getSecurityContext();
        username = securityContext.getUserPrincipal().getName();
        isAdmin = securityContext.isUserInRole(Roles.ADMIN);
        ownershipResolver = (OwnershipResolver) requestContext.getProperty(AuthorizationFilter.OWNERSHIP_RESOLVER);
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAdmin() {
        return isAdmin;
    }

    @Override
    public boolean isOwner(OwnerId ownerId) {
        return ownershipResolver.isUserAnOwner(ownerId);
    }
}
