package ro.teamnet.bootstrap.plugin.security;

/**
 * The type of the pre-authentication filter.
 */
public enum PreAuthenticationFilterType {
    /**
     * Use this type when the filter does not provide authentication. If no other implementation is provided, the
     * default authentication mechanism will be used.
     */
    REQUIRES_AUTHENTICATION,
    /**
     * Use this type when the filter provides authentication. This type will ensure the default authentication mechanism is skipped.
     */
    PROVIDES_AUTHENTICATION
}
