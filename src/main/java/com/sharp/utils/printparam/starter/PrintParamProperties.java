package com.sharp.utils.printparam.starter;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * <p>Title: PrintParamProperties</p>
 * <p>Description：报文打印部分的配置参数管理</p>
 * <p>Copyright: Copyright (c) 2019</p>
 * @author yuanhongwei
 * @version 1.0
 */
@ConfigurationProperties(
        prefix = "print-param"
)
public class PrintParamProperties {
    private boolean enablePrint = false;
    private boolean enableInputParam = true;
    private boolean enableOutputResult = true;
    private String filterIncludePattern = "/*";
    private String filterExcludePattern = "(/webjars/.*|/css/.*|/images/.*|/fonts/.*|/js/.*)";

    public PrintParamProperties() {
    }

    public boolean isEnablePrint() {
        return enablePrint;
    }

    public void setEnablePrint(boolean enablePrint) {
        this.enablePrint = enablePrint;
    }

    public boolean isEnableInputParam() {
        return enableInputParam;
    }

    public void setEnableInputParam(boolean enableInputParam) {
        this.enableInputParam = enableInputParam;
    }

    public boolean isEnableOutputResult() {
        return enableOutputResult;
    }

    public void setEnableOutputResult(boolean enableOutputResult) {
        this.enableOutputResult = enableOutputResult;
    }

    public Boolean getEnableInputParam() {
        return enableInputParam;
    }

    public void setEnableInputParam(Boolean enableInputParam) {
        this.enableInputParam = enableInputParam;
    }

    public Boolean getEnableOutputResult() {
        return enableOutputResult;
    }

    public void setEnableOutputResult(Boolean enableOutputResult) {
        this.enableOutputResult = enableOutputResult;
    }

    public String getFilterIncludePattern() {
        return filterIncludePattern;
    }

    public void setFilterIncludePattern(String filterIncludePattern) {
        this.filterIncludePattern = filterIncludePattern;
    }

    public String getFilterExcludePattern() {
        return filterExcludePattern;
    }

    public void setFilterExcludePattern(String filterExcludePattern) {
        this.filterExcludePattern = filterExcludePattern;
    }
}
