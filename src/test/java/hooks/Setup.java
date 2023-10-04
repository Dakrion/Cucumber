package hooks;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.cucumber.java.BeforeAll;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.extern.slf4j.Slf4j;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class Setup {

    private static Config config;

    /**
     * Настройка браузера, селеноида и селенида
     */
    @BeforeAll
    public static void setUpBeforeAllScenarios() {
        loadConfiguration();
        Configuration.timeout = config.base.timeout;
        Configuration.remote = config.base.url;
        Configuration.browser = config.browser.name;
        Configuration.browserVersion = config.browser.version;
        Configuration.browserSize = config.browser.size;
        Map<String, Object> selenoidOptions = new HashMap<>();
        selenoidOptions.put("enableVNC", config.browser.enableVNC);
        Configuration.browserCapabilities.setCapability("selenoid:options", selenoidOptions);
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide()
                .includeSelenideSteps(false)
                .screenshots(true)
                .savePageSource(false));
    }

    private static void loadConfiguration() {
        String profile = System.getProperty("profile");
        if (profile == null || profile.isEmpty()) {
            log.warn("Profile not found, set default profile 'local'");
            profile = "local";
        }
        try (InputStream inputStream = Files.newInputStream(Paths.get(String.format("src/test/resources/config-%s.yaml", profile)))) {
            Yaml yaml = new Yaml();
            config = yaml.loadAs(inputStream, Config.class);
            log.info("Configuration was loaded successfully. Current profile - {}", profile);
        } catch (IOException e) {
            log.error("Error while load yaml config!\n{}", e.getMessage());
        }
    }
}
