package ro.teamnet.bootstrap.web.rest;


import com.codahale.metrics.annotation.Timed;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.plugin.core.PluginRegistry;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ro.teamnet.bootstrap.plugin.security.SecurityType;
import ro.teamnet.bootstrap.plugin.security.UserAuthorizationPlugin;
import ro.teamnet.bootstrap.web.rest.dto.AuthorizationRequestDTO;

import javax.inject.Inject;

import static ro.teamnet.bootstrap.plugin.security.SecurityType.USER_AUTHORIZATION;

@RestController
@RequestMapping("app/sso/authorization")
public class AuthorizationResource {
    @Inject
    @Qualifier("userAuthorizationPluginRegistry")
    private PluginRegistry<UserAuthorizationPlugin, SecurityType> userAuthorizationPluginRegistry;

    @RequestMapping(method = RequestMethod.POST)
    @Timed
    public ResponseEntity authorize(@RequestBody AuthorizationRequestDTO authorizationRequest){
        for (UserAuthorizationPlugin userAuthorizationPlugin : userAuthorizationPluginRegistry.getPluginsFor(USER_AUTHORIZATION)) {
            if (userAuthorizationPlugin.grantAccessToResource(authorizationRequest.getResourceName(), authorizationRequest.getAccessLevel())) {
                return new ResponseEntity(authorizationRequest, HttpStatus.OK);
            }
        }
        return new ResponseEntity(authorizationRequest, HttpStatus.FORBIDDEN);
    }
}
