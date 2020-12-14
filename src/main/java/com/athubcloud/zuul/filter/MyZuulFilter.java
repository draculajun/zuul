package com.athubcloud.zuul.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.athubcloud.util.JwtHelper;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.Route;
import org.springframework.cloud.netflix.zuul.filters.SimpleRouteLocator;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

@Component
public class MyZuulFilter extends ZuulFilter {

    @Autowired
    private SimpleRouteLocator simpleRouteLocator;

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletResponse response = ctx.getResponse();
        HttpServletRequest request = ctx.getRequest();
        String url = request.getRequestURL().toString();

        String serviceName = null;
        Route route = simpleRouteLocator.getMatchingRoute(request.getRequestURI());
        if (route != null) {
            serviceName = route.getLocation();
        }

        if (HttpMethod.OPTIONS.matches(request.getMethod())) {
            return true;
        }

        if (url == null) {
            return true;
        }
        String[] filterUrl = new String[] { "info", "login" };
        for (int i = 0; i < filterUrl.length; i++) {
            if (url.contains(filterUrl[i])) {
                return true;
            }
        }

        if (request.getHeader("jwt") == null
                || (request.getHeader("jwt") != null && JwtHelper.validateLogin(request.getHeader("jwt")) == null)) {
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(HttpStatus.SC_UNAUTHORIZED);
            return false;
        }

        return true;
    }

    @Override
    public Object run() throws ZuulException {
        return null;
    }
}
