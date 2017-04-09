package org.nzbhydra.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Optional;

@ConfigurationProperties("main")
@Component
@Data
public class MainConfig {

    private String apiKey = null;
    private String branch;
    private Integer configVersion;
    private boolean debug;
    private String dereferer;
    private String externalUrl = null;
    private String gitPath;
    private String host;
    private String httpProxy = null;
    private String httpsProxy = null;
    private boolean firstStart;
    private LoggingConfig logging = new LoggingConfig();
    private int keepSearchResultsForDays;
    private int port;
    private String repositoryBase;
    private String secret;
    private boolean shutdownForRestart;
    private String socksProxy = null;
    private boolean ssl;
    private String sslcert = null;
    private String sslkey = null;
    private boolean startupBrowser;
    protected String theme;
    protected String urlBase;
    private boolean updateCheckEnabled;
    private boolean useCsrf;
    private boolean useLocalUrlForApiAccess;

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public Optional<String> getExternalUrl() {
        return Optional.ofNullable(externalUrl);
    }

    public Optional<String> getApiKey() {
        return Optional.ofNullable(apiKey);
    }

    public Optional<String> getHttpProxy() {
        return Optional.ofNullable(httpProxy);
    }

    public Optional<String> getHttpsProxy() {
        return Optional.ofNullable(httpsProxy);
    }

    public Optional<String> getSocksProxy() {
        return Optional.ofNullable(socksProxy);
    }

    public Optional<String> getSslcert() {
        return Optional.ofNullable(sslcert);
    }

    public Optional<String> getSslkey() {
        return Optional.ofNullable(sslkey);
    }

    public Optional<String> getUrlBase() {
        return Optional.ofNullable(urlBase);
    }
}