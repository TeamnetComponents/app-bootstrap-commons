package ro.teamnet.bootstrap.plugin.security;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;

/**
 * Created by Oana.Mihai on 10/7/2015.
 */
public interface UserDetailsExtension extends UserDetails {
    Map<String, Object> getExtensions();

    void addExtensions(Map<String, Object> extensions);
}
