package ro.teamnet.bootstrap.plugin.security;

import org.springframework.plugin.core.Plugin;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserAuthenticationPlugin extends Plugin<SecurityType> {

    public UserDetails authenticate(UserDetails userDetails);
}
