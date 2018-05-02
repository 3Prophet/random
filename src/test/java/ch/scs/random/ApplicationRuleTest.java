package ch.scs.random;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.LabeledMatchers.hasText;

import java.util.concurrent.TimeoutException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;

import javafx.application.Application;
import javafx.application.Platform;

class ApplicationRuleTest extends ApplicationTest {

	private Application application = null;

	@BeforeAll
	public static void setupSpec() throws TimeoutException, InterruptedException {

		if (Boolean.getBoolean("headless")) {
			System.setProperty("testfx.robot", "glass");
			System.setProperty("testfx.headless", "true");
			System.setProperty("prism.order", "sw");
			System.setProperty("prism.text", "t2k");
			System.setProperty("java.awt.headless", "true");
		}

		FxToolkit.registerPrimaryStage();
		FxToolkit.showStage();
	}

	@BeforeEach
	public void setUp() throws TimeoutException, InterruptedException {
		application = FxToolkit.setupApplication(ClickApplication.class);

	}

	@Test
	void should_contain_button() {
		// expect:
		verifyThat("#button", hasText("click me!"));
	}

	@Test
	void should_click_on_button() {
		// when:
		clickOn("#button");

		// then:
		verifyThat("#button", hasText("clicked!"));
	}

	@AfterEach
	public void cleanAfterTest() throws TimeoutException {
		// FxToolkit.hideStage();
		// FxToolkit.cleanupApplication(application);
	}

	@AfterAll
	public static void cleanAfterAllTests() {
		Platform.exit();
	}

}