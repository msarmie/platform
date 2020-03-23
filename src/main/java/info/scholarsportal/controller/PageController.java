package info.scholarsportal.controller;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import info.scholarsportal.service.PlatformManager;

@Controller
public class PageController {

    @Autowired
    private PlatformManager platformManager;

    @Autowired
    public PageController(PlatformManager platformManager) {
        this.platformManager = platformManager;
    }

    private static final String REDIRECT_PREFIX = "redirect:";

    @RequestMapping("/")
    public String showPage() {
        return "index";
    }

    @RequestMapping(value = "doc", method = { RequestMethod.GET, RequestMethod.HEAD })
    public ModelAndView docRedirect() {
        String redirectUrl = platformManager.getDocumentationUrl();
        if (!redirectUrl.isEmpty()) {
            return new ModelAndView(REDIRECT_PREFIX + redirectUrl);
        }
        return new ModelAndView("doc");
    }

    @RequestMapping(value = "factsheet", method = { RequestMethod.GET, RequestMethod.HEAD })
    public ModelAndView showFactSheet() {
        String redirectUrl = platformManager.getFactsheetUrl();
        if (!redirectUrl.isEmpty()) {
            return new ModelAndView(REDIRECT_PREFIX + redirectUrl);
        }
        return new ModelAndView("factsheet");
    }

    @RequestMapping(value = "licence", method = { RequestMethod.GET, RequestMethod.HEAD })
    public ModelAndView showLicence() {
        String redirectUrl = platformManager.getLicenceUrl();
        if (!redirectUrl.isEmpty()) {
            return new ModelAndView(REDIRECT_PREFIX + redirectUrl);
        }
        return new ModelAndView("licence");
    }

    @RequestMapping(value = "provenance", method = { RequestMethod.GET, RequestMethod.HEAD })
    public ModelAndView showProvenance() {
        String redirectUrl = platformManager.getProvenanceUrl();
        if (!redirectUrl.isEmpty()) {
            return new ModelAndView(REDIRECT_PREFIX + redirectUrl);
        }
        return new ModelAndView("provenance");
    }

    @RequestMapping(value = "releasenotes", method = { RequestMethod.GET, RequestMethod.HEAD })
    public ModelAndView showReleaseNotes() {
        String redirectUrl = platformManager.getReleasenotesUrl();
        if (!redirectUrl.isEmpty()) {
            return new ModelAndView(REDIRECT_PREFIX + redirectUrl);
        }
        return new ModelAndView("releasenotes");
    }

    @RequestMapping(value = "source", method = { RequestMethod.GET, RequestMethod.HEAD })
    public ModelAndView showSourceCode() {
        String redirectUrl = platformManager.getSourceUrl();
        if (!redirectUrl.isEmpty()) {
            return new ModelAndView(REDIRECT_PREFIX + redirectUrl);
        }
        return new ModelAndView("source");
    }

    @RequestMapping(value = "support", method = { RequestMethod.GET, RequestMethod.HEAD })
    public ModelAndView showSupport() {
        String redirectUrl = platformManager.getSupportUrl();
        if (!redirectUrl.isEmpty()) {
            return new ModelAndView(REDIRECT_PREFIX + redirectUrl);
        }
        return new ModelAndView("support");
    }

    @RequestMapping(value = "tryme", method = { RequestMethod.GET, RequestMethod.HEAD })
    public ModelAndView showTryMe() {
        String redirectUrl = platformManager.getTrymeUrl();
        if (!redirectUrl.isEmpty()) {
            return new ModelAndView(REDIRECT_PREFIX + redirectUrl);
        }
        return new ModelAndView("tryme");
    }

    /**
     * Information /platform/info HTML
     * @param model
     * @return
     */
    @RequestMapping(value = "info", method = { RequestMethod.GET, RequestMethod.HEAD })
    public String showInfoHtml(Model model) {
        System.out.println("Show HTML");
        model.addAttribute("infoMap", getInfo("html"));
        return "info";
    }

    /**
     * Information /platform/info JSON
     * @return
     */
    @RequestMapping(value = "info", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, Object> showInfoJson() {
        System.out.println("Show JSON");
        Map<String, Object> info = getInfo("json");
        Map<String, Object> jsonMap = new LinkedHashMap<String, Object>();
        jsonMap.putAll(info);
        // String to Array
        jsonMap.replace("tags", info.get("tags").toString().split(","));
        return jsonMap;
    }

    /**
     * Statistics /platform/stats HTML
     * @param model
     * @return
     */
    @RequestMapping(value = "stats", method = { RequestMethod.GET, RequestMethod.HEAD })
    public ModelAndView showStatsHtml(Model model) {
        ModelAndView vm = new ModelAndView("stats");
        Map<String, Object> stats = getStats("html");
        if (stats.containsKey("error")) {
            vm.setViewName("error-503");
            vm.setStatus(HttpStatus.SERVICE_UNAVAILABLE);
        } else {
            vm.addObject("statsMap", stats);
            model.addAttribute("statsMap", stats);
        }
        return vm;
    }

    /**
     * Statistics /platform/stats JSON
     * @return
     */
    @RequestMapping(value = "stats", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Map<String, Object>> showStatsJson() {
        System.out.println("Show JSON");
        Map<String, Object> stats = getStats("json");
        if (stats.containsKey("error")) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(stats);
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(stats);
        }
    }

    /**
     * Prepare Map output for /platform/info
     * @param requestType
     * @return
     */
    protected Map<String, Object> getInfo(String requestType) {
        Map<String, Object> info = new LinkedHashMap<String, Object>();
        Map<String, Map<String, Object>> values = this.platformManager.getInformation();

        if (requestType.equalsIgnoreCase("json")) {
            for (Entry<String, Map<String, Object>> entry : values.entrySet()) {
                info.put(entry.getKey().toString(), entry.getValue().get("value"));
            }
        } else {
            for (Entry<String, Map<String, Object>> entry : values.entrySet()) {
                info.put(entry.getValue().get("name").toString(), entry.getValue().get("value"));
            }
        }
        return info;
    }

    /**
     * Prepare Map output for /platform/stats.
     * @param type
     * @return
     */
    protected Map<String, Object> getStats(String requestType) {
        Map<String, Object> stats = new LinkedHashMap<String, Object>();
        Map<String, Map<String, Object>> values = this.platformManager.getStatistics();

        if (requestType.equalsIgnoreCase("json")) {
            for (Entry<String, Map<String, Object>> entry : values.entrySet()) {
                stats.put(entry.getKey().toString(), entry.getValue().get("value"));
            }
        } else {
            for (Entry<String, Map<String, Object>> entry : values.entrySet()) {
                stats.put(entry.getValue().get("name").toString(), entry.getValue().get("value"));
            }
        }
        return stats;
    }
}
