package ro.teamnet.bootstrap.web.filter;

import ro.teamnet.bootstrap.plugin.security.UserAuthorizationPlugin;

import javax.servlet.Filter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class BootstrapFilterBase implements Filter {

    private List<UserAuthorizationPlugin> userAuthorizationPlugins=new ArrayList<>();

    public List<UserAuthorizationPlugin> getUserAuthorizationPlugins() {
        return userAuthorizationPlugins;
    }

    public boolean addAll(Collection<? extends UserAuthorizationPlugin> c) {
        return userAuthorizationPlugins.addAll(c);
    }

    public boolean add(UserAuthorizationPlugin userAuthorizationPlugin) {
        return userAuthorizationPlugins.add(userAuthorizationPlugin);
    }
}
