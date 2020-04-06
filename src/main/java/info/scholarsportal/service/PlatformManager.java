package info.scholarsportal.service;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;

import info.scholarsportal.config.PlatformConfig;

@Component
public class PlatformManager {

    @Autowired
    PlatformConfig platformConfig;

    private static final String STATS_JSON = "stats.json";
    private static final String INFO_JSON = "info.json";

    /**
     * Read JSON file with Information details.
     * 
     * @return Map
     */
    public Map<String, Map<String, Object>> getInformation() {
        File json = new File(getPlatformDir(), INFO_JSON);
        Map<String, Map<String, Object>> info = jsonFileToMap(json);
        return info;
    }

    /**
     * Read JSON file with Statistics
     * 
     * @return Map
     */
    public Map<String, Map<String, Object>> getStatistics() {
        File json = new File(getPlatformDir(), STATS_JSON);
        Map<String, Map<String, Object>> stats = jsonFileToMap(json);
        return stats;
    }

    /**
     * Return Fact sheet external URL to redirect
     * 
     * @return
     */
    public String getFactsheetUrl() {
        return platformConfig.getFactsheetUrl();
    }

    /**
     * Return Documentation external URL to redirect
     * 
     * @return
     */
    public String getDocumentationUrl() {
        return platformConfig.getDocumentationUrl();
    }

    /**
     * Return Licence external URL to redirect
     * 
     * @return
     */
    public String getLicenceUrl() {
        return platformConfig.getLicenceUrl();
    }

    /**
     * Return Provenance external URL to redirect
     * 
     * @return
     */
    public String getProvenanceUrl() {
        return platformConfig.getProvenanceUrl();
    }

    /**
     * Return Release notes external URL to redirect
     * 
     * @return
     */
    public String getReleasenotesUrl() {
        return platformConfig.getReleasenotesUrl();
    }

    /**
     * Return Source code external URL to redirect
     * 
     * @return
     */
    public String getSourceUrl() {
        return platformConfig.getSourceUrl();
    }

    /**
     * Return Siupport external URL to redirect
     * 
     * @return
     */
    public String getSupportUrl() {
        return platformConfig.getSupportUrl();
    }

    /**
     * Return Trial external URL to redirect
     * 
     * @return
     */
    public String getTrymeUrl() {
        return platformConfig.getTrymeUrl();
    }

    /**
     * Return email displayed in support page
     * 
     * @return
     */
    public String getSupportEmail() {
        return platformConfig.getSupportEmail();
    }

    /**
     * Return platform home url
     * 
     * @return
     */
    public String getHomeUrl() {
        return platformConfig.getHomeUrl();
    }

    /**
     * Return header text
     * 
     * @return
     */
    public String getHeaderText() {
        return platformConfig.getHeaderText();
    }

    public Map<String, Object> getHeader() {
        Map<String, Object> header = new LinkedHashMap<String, Object>();
        header.put("platformHeader", getHeaderText());
        header.put("platformHomeUrl", getHomeUrl());
        return header;
    }

    /**
     * Get platform directory and create stats directory.
     * 
     * @return stats file object
     */
    public File getStatsDir() {
        String fileDirPath = new File(getPlatformDir(), "stats").getAbsolutePath();
        File statsDirectory = createDirectoryIfNotExists(fileDirPath);
        return statsDirectory;
    }

    /**
     * Get platform directory
     * 
     * @return platform file object
     */
    public File getPlatformDir() {
        String platformDirPath = platformConfig.getPlatformRootDir();
        if (platformDirPath == null) {
            String error = " getPlatformDir: Platform application root directory not set.";
            throw new RuntimeException(error);
        }

        File platformDir = createDirectoryIfNotExists(platformDirPath);
        if (!platformDir.exists()) {
            platformDir.mkdirs();
        }
        return platformDir;
    }

    /**
     * Create directory path
     * 
     * @param path
     * @return file object of file path
     */
    private static File createDirectoryIfNotExists(String path) {
        File fileDir = new File(path);
        if (!fileDir.exists()) {
            System.out.println("Creating directory: " + path);
            if (!fileDir.mkdirs()) {
                String error = " createDirectoryIfNotExists: Error creating directories.";
                throw new RuntimeException(error);
            }
        }
        return fileDir;
    }

    /**
     * Convert nested JSON to Map
     * 
     * @param jsonFile
     * @return
     */
    private static Map<String, Map<String, Object>> jsonFileToMap(File jsonFile) {
        Map<String, Map<String, Object>> map = new LinkedHashMap<String, Map<String, Object>>();
        ObjectReader reader = new ObjectMapper().readerFor(Map.class);

        try {
            map = reader.readValue(jsonFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }
}
