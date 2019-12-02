package testing;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.RunWith;
import org.junit.runner.notification.Failure;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import junit.runner.*;

/**
 * @author Boxiao Yu 40070128
 * @author Yilun Sun 40092802
 * @author Yuhua Jiang 40083453
 * @author Jiuxiang Chen 40086723
 * @author Chao Ye 40055665
 */

@RunWith(Suite.class)
@SuiteClasses({PlayerTest.class,LoadmapTest.class,FortifyTest.class
	,VictoryTest.class,ControllerTest.class,ContinentTest.class,CardTest.class,CountryTest.class})
public class TestSuite{
	public static void main(String[] args) {
		Result result = JUnitCore.runClasses(TestSuite.class);
		for(Failure failure : result.getFailures()) {
			System.out.print(failure.toString());
		}
		System.out.print(result.wasSuccessful());
	}
	
	
}

