package ro.teamnet.bootstrap.plugin.security;


import org.springframework.plugin.core.Plugin;

public interface UserAuthorizationPlugin extends Plugin<SecurityType> {

    public Boolean grantAccessToResource(String resource);
}
