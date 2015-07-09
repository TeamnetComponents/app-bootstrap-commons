package ro.teamnet.bootstrap.plugin.security;


import org.springframework.plugin.core.Plugin;
import ro.teamnet.bootstrap.domain.util.ModuleRightTypeEnum;

public interface UserAuthorizationPlugin extends Plugin<SecurityType> {

    public Boolean grantAccessToResource(String resource, ModuleRightTypeEnum accessType);
}
