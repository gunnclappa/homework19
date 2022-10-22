package browserstack.drivers;

import com.codeborne.selenide.WebDriverProvider;
import lombok.SneakyThrows;
import org.aeonbits.owner.ConfigFactory;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;

public final class BrowserstackMobileDriver implements WebDriverProvider {

    public static final ProjectConfig CONFIG = ConfigFactory.create(ProjectConfig.class, System.getProperties());

    @SneakyThrows
    @Override
    public WebDriver createDriver(final Capabilities capabilities) {

        MutableCapabilities mutableCapabilities = new MutableCapabilities();
        mutableCapabilities.merge(capabilities);

        ProjectConfig config = ConfigFactory.create(ProjectConfig.class, System.getProperties());

        // Set your access credentials
        mutableCapabilities.setCapability("browserstack.user", config.browserstackUser());
        mutableCapabilities.setCapability("browserstack.key", config.browserstackKey());

        // Set URL of the application under test
        mutableCapabilities.setCapability("app", config.app());

        // Specify device and os_version for testing
        mutableCapabilities.setCapability("device", config.device());
        mutableCapabilities.setCapability("os_version", config.osVersion());

        // Set other BrowserStack capabilities
        mutableCapabilities.setCapability("project", config.project());
        mutableCapabilities.setCapability("build", config.build());
        mutableCapabilities.setCapability("name", config.name());

        // Initialise the remote Webdriver using BrowserStack remote URL
        // and desired capabilities defined above
        return new RemoteWebDriver(new URL(config.remoteUrl()), mutableCapabilities);
    }
}