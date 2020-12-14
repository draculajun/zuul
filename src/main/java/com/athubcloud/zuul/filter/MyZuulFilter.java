package com.athubcloud.zuul.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

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
        return true;
    }

    @Override
    public Object run() throws ZuulException {

        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletResponse response = ctx.getResponse();
        HttpServletRequest request = ctx.getRequest();
        String url = request.getRequestURL().toString();

        Boolean pass = true;

        String serviceName = null;
        Route route = simpleRouteLocator.getMatchingRoute(request.getRequestURI());
        if (route != null) {
            serviceName = route.getLocation();
        }

        if (HttpMethod.OPTIONS.matches(request.getMethod())) {
            return null;
        }

        if (url == null) {
            return null;
        }
        String[] filterUrl = new String[] { "info"};
        for (int i = 0; i < filterUrl.length; i++) {
            if (url.contains(filterUrl[i])) {
                return null;
            }
        }

        //TODO JWT


        return null;
    }
}
