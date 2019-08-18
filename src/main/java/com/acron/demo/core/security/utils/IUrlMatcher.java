package com.acron.demo.core.security.utils;

/**
 * @author Acron
 * @ClassName IUrlMatcher
 * @Description TODO
 * @since 2019/08/04 13:50
 */
public interface IUrlMatcher {
    Object compile(String paramString);

    boolean pathMatchesUrl(Object paramObject, String paramString);

    String getUniversalMatchPattern();

    boolean requiresLowerCaseUrl();
}
