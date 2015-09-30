package ro.teamnet.bootstrap.plugin.security;

import org.springframework.plugin.core.Plugin;

import javax.servlet.Filter;

/**
 * This plugin provides a pre-authentication Filter that will be evaluated when processing http requests.
 */
public interface PreAuthenticationFilterPlugin extends Plugin<PreAuthenticationFilterType> {
    Filter getFilter();
}
