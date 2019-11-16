package com.athubcloud.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.stereotype.Component;

@Component
public class TrackingFilter extends ZuulFilter {

    public static final String RELATION_ID = "relation-id";

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
        String relationId = java.util.UUID.randomUUID().toString();
        System.out.println("RelationId: " + relationId);
        ctx.addZuulRequestHeader(TrackingFilter.RELATION_ID, relationId);
        return null;
    }
}
