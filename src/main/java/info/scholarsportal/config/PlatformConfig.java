package info.scholarsportal.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("${platform.config.file}")
public class PlatformConfig {

    @Value("${platform.root.dir}")
    private String platformRootDir;

    @Value("${platform.factsheet.url}")
    private String factsheetUrl;

    @Value("${platform.documentation.url}")
    private String documentationUrl;

    @Value("${platform.licence.url}")
    private String licenceUrl;

    @Value("${platform.provenance.url}")
    private String provenanceUrl;

    @Value("${platform.releasenotes.url}")
    private String releasenotesUrl;

    @Value("${platform.source.url}")
    private String sourceUrl;

    @Value("${platform.support.url}")
    private String supportUrl;

    @Value("${platform.tryme.url}")
    private String trymeUrl;

    public PlatformConfig() {

    }

    public String getFactsheetUrl() {
        return factsheetUrl;
    }

    public void setFactsheetUrl(String factsheetUrl) {
        this.factsheetUrl = factsheetUrl;
    }

    public String getDocumentationUrl() {
        return documentationUrl;
    }

    public void setDocumentationUrl(String documentationUrl) {
        this.documentationUrl = documentationUrl;
    }

    public String getLicenceUrl() {
        return licenceUrl;
    }

    public void setLicenceUrl(String licenceUrl) {
        this.licenceUrl = licenceUrl;
    }

    public String getProvenanceUrl() {
        return provenanceUrl;
    }

    public void setProvenanceUrl(String provenanceUrl) {
        this.provenanceUrl = provenanceUrl;
    }

    public String getReleasenotesUrl() {
        return releasenotesUrl;
    }

    public void setReleasenotesUrl(String releasenotesUrl) {
        this.releasenotesUrl = releasenotesUrl;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    public String getSupportUrl() {
        return supportUrl;
    }

    public void setSupportUrl(String supportUrl) {
        this.supportUrl = supportUrl;
    }

    public String getTrymeUrl() {
        return trymeUrl;
    }

    public void setTrymeUrl(String trymeUrl) {
        this.trymeUrl = trymeUrl;
    }

    public String getPlatformRootDir() {
        return platformRootDir;
    }

    public void setPlatformRootDir(String platformRootDir) {
        this.platformRootDir = platformRootDir;
    }
}
