package org.artemis.gateway.server.filters;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.MessageFormat;
import java.util.UUID;

public class ArtemisResponseZuulFilter extends ZuulFilter {

    @Override
    public String filterType() {
        return FilterConstants.POST_TYPE;
    }

    @Override
    public int filterOrder() {
        return FilterConstants.SEND_RESPONSE_FILTER_ORDER - 1;
    }

    @Override
    public boolean shouldFilter() {
        //判断上一个过滤器结果为true，否则就不走下面过滤器，直接跳过后面的所有过滤器并返回 上一个过滤器不通过的结果。
        //RequestContext ctx = RequestContext.getCurrentContext();
        //return (boolean) ctx.get("isSuccess");
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();
        HttpServletResponse response = currentContext.getResponse();

        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        String artemis = MessageFormat.format("{0}-author：{1}", uuid, "流年·公子");
        Cookie cookie = new Cookie("ArtemisMarks", artemis);
        cookie.setPath(request.getContextPath());
        cookie.setMaxAge(-1);
        response.addCookie(cookie);

        return null;
    }
}
