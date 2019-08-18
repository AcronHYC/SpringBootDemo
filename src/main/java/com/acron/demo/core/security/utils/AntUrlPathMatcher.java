package com.acron.demo.core.security.utils;

import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

/**
 * @author Acron
 * @ClassName AntUrlPathMatcher
 * @Description TODO
 * @since 2019/08/04 13:51
 */
@Component
public class AntUrlPathMatcher implements IUrlMatcher {
    private Boolean requiresLowerCaseUrl;
    private PathMatcher pathMatcher;

    public AntUrlPathMatcher() {
        this(true);

    }

    public AntUrlPathMatcher(boolean requiresLowerCaseUrl) {
        this.requiresLowerCaseUrl = true;
        this.pathMatcher = new AntPathMatcher();
        this.requiresLowerCaseUrl = requiresLowerCaseUrl;
    }

    @Override
    public Object compile(String path) {
        if (this.requiresLowerCaseUrl) {
            return path.toLowerCase();
        }
        return path;
    }

    public void setRequiresLowerCaseUrl(boolean requiresLowerCaseUrl) {
        this.requiresLowerCaseUrl = requiresLowerCaseUrl;
    }

    @Override
    public boolean pathMatchesUrl(Object path, String url) {
        if (("/**".equals(path)) || ("**".equals(path))) {
            return true;
        }
        return this.pathMatcher.match((String) path, url);
    }

    @Override
    public String getUniversalMatchPattern() {
        return "/**";
    }

    @Override
    public boolean requiresLowerCaseUrl() {
        return this.requiresLowerCaseUrl;
    }

    @Override
    public String toString() {
        return super.getClass().getName() + "[requiresLowerCase='"
                + this.requiresLowerCaseUrl + "']";
    }
}
