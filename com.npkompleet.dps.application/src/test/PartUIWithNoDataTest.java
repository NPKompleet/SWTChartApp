package test;

import org.eclipse.swt.widgets.Text;
import org.eclipse.swtbot.e4.finder.matchers.WidgetMatcherFactory;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.junit.SWTBotJunit4ClassRunner;
import org.eclipse.swtchart.Chart;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(SWTBotJunit4ClassRunner.class)
public class PartUIWithNoDataTest {

	private static SWTBot bot;

	@BeforeClass
	public static void beforeClass() throws Exception {
		bot = new SWTBot();
	}

	@Test
	public void testThatEverythingDisplays() {
		bot.cTabItem("Main Page");
		bot.cTabItem("Label Size");
		bot.cTabItem("Activation Pattern");
		bot.cTabItem("Main Page").activate();
		Assert.assertTrue(bot.cTabItem("Main Page").isActive());
	}

	@Test
	public void testMainPage() {
		bot.cTabItem("Main Page").activate();
		bot.label("Please load the file to read from");
		Text text = bot.widget(WidgetMatcherFactory.widgetOfType(Text.class));
		Assert.assertNotNull(text);
		Assert.assertEquals("Choose file...", text.getMessage());
		bot.button("File");
	}

	@Test
	public void testLabelSize() {
		bot.cTabItem("Label Size").activate();
		Assert.assertEquals(1, bot.widgets(WidgetMatcherFactory.widgetOfType(Chart.class)).size());
	}

	@Test
	public void testActivationPattern() {
		bot.cTabItem("Activation Pattern").activate();
		Assert.assertEquals(1, bot.widgets(WidgetMatcherFactory.widgetOfType(Chart.class)).size());
		Chart chart = bot.widget(WidgetMatcherFactory.widgetOfType(Chart.class));
		Assert.assertEquals(0, chart.getSeriesSet().getSeries().length);
	}

	@AfterClass
	public static void sleep() {
		bot.sleep(1000);
	}
}