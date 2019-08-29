package org.artemis.gateway.server.filters;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

public class ArtemisTokenZuulFilter extends ZuulFilter {

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return FilterConstants.PRE_DECORATION_FILTER_ORDER - 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();
        System.out.println(String.format("%s AccessUserNameFilter request to %s", request.getMethod(), request.getRequestURL().toString()));

        // 获取请求的参数
        String token = request.getParameter("token");
        // 如果请求的参数不为空，且值为chhliu时，则通过
        if (null != token && token.equals("123456")) {
            Principal principal = request.getUserPrincipal();
            if (null != principal) {
                //获取用户的登录id
                String userId = principal.getName();
                currentContext.addZuulRequestHeader("X-AUTH-ID", userId);
            }

            //将获取的登录用户id设置到了请求头中传递给内部服务，内部服务可以通过下面的代码进行获取
            //String user = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader("X-AUTH-ID");

            // 对该请求进行路由
            currentContext.setSendZuulResponse(true);
            currentContext.setResponseStatusCode(200);
            // 设值，让下一个Filter看到上一个Filter的状态
            currentContext.set("isSuccess", true);
            return null;
        }

        // 过滤该请求，不对其进行路由
        currentContext.setSendZuulResponse(false);
        // 返回错误码，表示没有权限(状态码:401)
        currentContext.setResponseStatusCode(401);
        // 返回错误内容
        currentContext.setResponseBody("{\"result\":\"token is not correct!\"}");
        currentContext.set("isSuccess", false);


        Object originalRequestPath = currentContext.get(FilterConstants.REQUEST_URI_KEY);
        System.out.println(originalRequestPath);
        //String modifiedRequestPath = "/api/prefix" + originalRequestPath;
        //currentContext.put(FilterConstants.REQUEST_URI_KEY, modifiedRequestPath);
        return null;
    }

}
