package com.micro.netflixzuulapigatewayserver;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class ZuulLogginFilter extends ZuulFilter {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    // pre, post, error, filter on what condition
    // pre: entercept request before execution
    @Override
    public String filterType() {
        return "pre";
    }

    // set priority for filters
    @Override
    public int filterOrder() {
        return 1;
    }

    // filter all request
    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        HttpServletRequest request = RequestContext.getCurrentContext().getRequest();

        logger.info("request -> {} request uri -> {}", request, request.getRequestURI());
        return null;
    }
}
