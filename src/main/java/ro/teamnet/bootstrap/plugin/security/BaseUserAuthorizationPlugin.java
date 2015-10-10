package ro.teamnet.bootstrap.plugin.security;

import ro.teamnet.bootstrap.domain.ModuleRight;
import ro.teamnet.bootstrap.domain.util.ModuleRightTypeEnum;

/**
 * Created by Oana.Mihai on 10/9/2015.
 */
public abstract class BaseUserAuthorizationPlugin implements UserAuthorizationPlugin {

    protected boolean moduleRightMatchesRequest(ModuleRight moduleRight, String resource, ModuleRightTypeEnum accessLevel) {
        String moduleCode = moduleRight.getModule().getCode();
        return moduleRight.getRight().equals(accessLevel.getRight()) && (moduleCode.equalsIgnoreCase(resource)
                || plural(moduleCode).equalsIgnoreCase(resource) || moduleCode.equalsIgnoreCase(plural(resource)));
    }


    private String plural(String singular) {
        return singular + 's';
    }
}
