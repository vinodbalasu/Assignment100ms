package ms.tests;

import ms.constants.TestGroups;
import ms.controllers.MSController;
import ms.models.streaming.StreamingDTO;
import ms.utils.PropertiesLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.testng.ITest;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.internal.BaseTestMethod;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class TestBase extends MSController implements ITest {
    private static Logger logger = LogManager.getLogger(TestBase.class);

    private ThreadLocal<String> testName = new ThreadLocal<>();


    static  {
        PropertiesLoader.loadProperties();
    }

    @BeforeMethod(alwaysRun = true)
    public void beforeMethod(Method method, Object[] testData, ITestContext context) {
        logger.debug("running before method 2");
        if (testData != null && testData.length >0) {
            final String testCaseName = new JSONObject(testData[0]).getString("testName");
            testName.set(testCaseName);
        } else {
            testName.set(method.getName());
        }

        //stop the stream that were running
        makeStreamingCall(StreamingDTO.builder().roomId(PropertiesLoader.properties.get("room_id").toString())
                .operation("stop").build(), false);
    }

    @AfterMethod(alwaysRun = true)
    public void afterMethod(ITestResult result, Method method, Object[] testData) {
        logger.debug("running after method");
        //stop the stream that were running
        makeStreamingCall(StreamingDTO.builder().roomId(PropertiesLoader.properties.get("room_id").toString())
                .operation("stop").build(), false);
        try {
            BaseTestMethod baseTestMethod = (BaseTestMethod) result.getMethod();
            Field f = baseTestMethod.getClass().getSuperclass().getDeclaredField("m_methodName");
            f.setAccessible(true);
            f.set(baseTestMethod, this.getTestName());
        } catch (Exception e) {
            logger.debug("Exception : " + e.getMessage());
        }
    }

    @Override
    public String getTestName() {
        return testName.get();
    }
}
