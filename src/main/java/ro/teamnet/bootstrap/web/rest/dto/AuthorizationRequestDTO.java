package ro.teamnet.bootstrap.web.rest.dto;

import ro.teamnet.bootstrap.domain.util.ModuleRightTypeEnum;

import java.io.Serializable;

/**
 * The body of an authorization request.
 */
public class AuthorizationRequestDTO implements Serializable{

    private String resourceName;
    private ModuleRightTypeEnum accessLevel;
    private String dataRequest;

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public ModuleRightTypeEnum getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(ModuleRightTypeEnum accessLevel) {
        this.accessLevel = accessLevel;
    }

    public String getDataRequest() {
        return dataRequest;
    }

    public void setDataRequest(String dataRequest) {
        this.dataRequest = dataRequest;
    }
}
