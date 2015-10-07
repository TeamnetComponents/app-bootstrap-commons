package ro.teamnet.bootstrap.plugin.security;

import org.springframework.plugin.core.Plugin;

/**
 * Created by Marian.Spoiala on 9/24/2015.
 */
public interface UserDetailsDecoratorPlugin extends Plugin<UserDetailsDecoratorType> {

    UserDetailsExtension extendUserDetails(UserDetailsExtension user, Object authenticationDetails);
}
