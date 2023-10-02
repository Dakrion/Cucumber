package hooks;

import com.codeborne.selenide.Configuration;
import io.cucumber.java.Before;

import java.util.HashMap;
import java.util.Map;

public class DriverHooks {
    /**
     * Настройка таймаута для взаимодействия с элементами перед запуском тестов
     */
    @Before
    public void setUpDriverBeforeScenario() {
        Configuration.timeout = 60000;
        Configuration.remote = "http://selenoid:4444/wd/hub";
        Configuration.browser = "chrome";
        Configuration.browserVersion = "116.0";
        Configuration.browserSize = "1920x1080";
        Map<String, Object> selenoidOptions = new HashMap<>();
        selenoidOptions.put("enableVNC", true);
        Configuration.browserCapabilities.setCapability("selenoid:options", selenoidOptions);
    }
}
