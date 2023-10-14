import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

@Suite
@SelectClasses({TournamentGameUnitTest.class, PlayerUnitTest.class, CardUnitTest.class, GameAcceptanceTest.class})
@SuiteDisplayName("Unit Test Suite")

public class GameUnitTestSuite {
}
