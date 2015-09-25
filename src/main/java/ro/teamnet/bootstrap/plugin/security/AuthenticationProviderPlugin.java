package ro.teamnet.bootstrap.plugin.security;

import org.springframework.plugin.core.Plugin;
import org.springframework.security.authentication.AuthenticationProvider;

/**
 * Created by Marian.Spoiala on 9/22/2015.
 */
public interface AuthenticationProviderPlugin extends Plugin<AuthProviderType> {

    public AuthenticationProvider getAuthenticationProvider();
}
