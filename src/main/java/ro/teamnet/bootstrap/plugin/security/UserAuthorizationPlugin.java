package ro.teamnet.bootstrap.plugin.security;


import org.springframework.plugin.core.Plugin;
import ro.teamnet.bootstrap.domain.util.ModuleRightTypeEnum;

public interface UserAuthorizationPlugin extends Plugin<SecurityType> {

    /**
     * Ensures that the authenticated user has the necessary rights to access a resource at a certain level.
     *
     * @param resource    the resource to be accessed
     * @param accessLevel the level of access requested
     * @return true if the user should be granted access, false otherwise.
     */
    Boolean grantAccessToResource(String resource, ModuleRightTypeEnum accessLevel);
}
