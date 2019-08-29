package org.artemis.gateway.server.filters;

import com.google.common.util.concurrent.RateLimiter;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;

/**
 * Zuul网关实现限流
 */
public class ArtemisRateLimitZuulFilter extends ZuulFilter {

    /**
     * 创建一个限流的过滤器RateLimitFilter，我们通过google提供好的一个RateLimiter类来处理限流，我们运行每秒100个令牌来做限流
     * 限流的过滤器要放在最前面，所以使用SERVLET_DETECTION_FILTER_ORDER - 1
     */
    private static final RateLimiter RATE_LIMITER = RateLimiter.create(100);

    /**
     * 设置 过滤器的类型
     * FilterConstants.PRE_TYPE     在请求被路由之前调用
     * FilterConstants.POST_TYPE    在routing和error过滤器之后被调用
     */
    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    /**
     * 设置 过滤器执行的顺序
     * 通过int值来定义过滤器的执行顺序，数值越小优先级越高。
     * filterOrders不需要是顺序的（只要保持一个大概的顺序就行了）
     */
    @Override
    public int filterOrder() {
        //限流的过滤器要放在最前面，所以使用 SERVLET_DETECTION_FILTER_ORDER - 1
        return FilterConstants.SERVLET_DETECTION_FILTER_ORDER - 1;
    }

    /**
     * 返回一个boolean值来判断该过滤器是否要执行。
     * 我们可以通过此方法来指定过滤器的有效范围。
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * 过滤器的具体逻辑
     * 在该函数中，我们可以实现自定义的过滤逻辑，来确定是否要拦截当前的请求，不对其进行后续的路由，
     * 或是在请求路由返回结果之后，对处理结果做一些加工等。
     */
    @Override
    public Object run() throws ZuulException {
        if(!RATE_LIMITER.tryAcquire()){
            throw new RuntimeException();
        }
        return null;
    }

}
