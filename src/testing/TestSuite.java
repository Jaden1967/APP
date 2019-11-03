package testing;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.RunWith;
import org.junit.runner.notification.Failure;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import junit.runner.*;

@RunWith(Suite.class)
@SuiteClasses({StartupTest.class,ReinforceTest.class,PlayerTest.class,LoadmapTest.class,FortifyTest.class
	,AttackTest.class})
public class TestSuite{
	public static void main(String[] args) {
		Result result = JUnitCore.runClasses(TestSuite.class);
		for(Failure failure : result.getFailures()) {
			System.out.print(failure.toString());
		}
		System.out.print(result.wasSuccessful());
	}
	
	
}

