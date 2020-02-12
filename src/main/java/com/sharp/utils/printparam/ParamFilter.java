package com.sharp.utils.printparam;

import com.sharp.utils.ObjUtil;
import com.sharp.utils.printparam.starter.PrintParamProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * <p>Title: ParamFilter</p>
 * <p>Description: 实现打印报文，只可以打印做为服务端的报文</p>
 * <p>Copyright: Copyright (c) 2019</p>
 * @author yuanhongwei
 * @version 1.0
 */
public class ParamFilter implements Filter {
    private static final Logger LOG = LoggerFactory.getLogger("print-param:");
    private PrintParamProperties properties;

    public ParamFilter(PrintParamProperties properties) {
        this.properties = properties;
    }
    @Override
    public void init(FilterConfig filterConfig) {
    }
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (properties == null) {
            properties = new PrintParamProperties();
        }
        HttpServletRequest r = (HttpServletRequest) servletRequest;

        if (isRequestExcluded(r)){
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            ParamRequestWrapper requestWrapper = new ParamRequestWrapper((HttpServletRequest) servletRequest);
            
            String random = ObjUtil.getRandom();
            if (properties.getEnableInputParam()) {
                String path = r.getQueryString();
                if (path == null) {
                    Map<String, String> map = new HashMap<String, String>();
                    Enumeration headerNames = ((HttpServletRequest) servletRequest).getHeaderNames();
                    while (headerNames.hasMoreElements()) {
                        String key = (String) headerNames.nextElement();
                        String value = ((HttpServletRequest) servletRequest).getHeader(key);
                        map.put(key, value);
                    }
                    path = map.toString();
                }
                String url = r.getRequestURI();
                LOG.info("[&&&&&&-{}] request uri:{}",random, url);
                LOG.info("[&&&&&&-{}] request header:{}", random,path);
                try {
                    Map map = servletRequest.getParameterMap();
                    LOG.info("[&&&&&&-{}] request form:{}",random, map);
                    BufferedReader bufferedReader = requestWrapper.getReader();
                    String line;
                    StringBuilder sb = new StringBuilder();
                    while ((line = bufferedReader.readLine()) != null) {
                        sb.append(line);
                    }
                    LOG.info("[&&&&&&-{}] request body:{}",random, sb.toString());
                } catch (Exception e) {
                    LOG.warn("[&&&&&&] request error:", e);
                }
            }
            ResponseWrapper responseWrapper = new ResponseWrapper((HttpServletResponse) servletResponse);
            filterChain.doFilter(requestWrapper, responseWrapper);

            String result = new String(responseWrapper.getResponseData());
            servletResponse.setContentLength(-1);
            servletResponse.setCharacterEncoding("UTF-8");
            PrintWriter out = null;
            try {
                out = servletResponse.getWriter();
                out.write(result);
                out.flush();
            } finally {
                if (out != null){
                    out.close();
                }
            }
            if (properties.getEnableOutputResult()) {
                LOG.info("[&&&&&&-{}] response return data:{}",random,result);
            }
        }
    }
    @Override
    public void destroy() {
    }

    private boolean isRequestExcluded(HttpServletRequest httpRequest) {
        return this.properties.getFilterExcludePattern() != null
                && Pattern.compile(this.properties.getFilterExcludePattern())
                        .matcher(httpRequest.getRequestURI().substring(httpRequest.getContextPath().length()))
                        .matches();
    }
}
