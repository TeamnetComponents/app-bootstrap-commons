package ro.teamnet.bootstrap.plugin.security;

import org.springframework.plugin.core.Plugin;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Created by Marian.Spoiala on 9/24/2015.
 */
public interface UserDetailsDecoratorPlugin extends Plugin<UserDetailsDecoratorType> {

    public UserDetails extendUserDetails(UserDetails userDetails);
}
