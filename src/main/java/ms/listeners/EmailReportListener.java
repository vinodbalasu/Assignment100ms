package ms.listeners;

import ms.reporting.EmailReport;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.*;
import org.testng.xml.XmlSuite;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class EmailReportListener implements IReporter, IExecutionListener {
    private static Logger logger = LogManager.getLogger(EmailReportListener.class);
    private static final String ROW_TEMPLATE = "<tr class=\"%s\"><td>%s</td><td>%s</td><td>%s</td><td>%s</td><td>%s</td></tr>";


    public void onExecutionFinish() {
        logger.debug("Sending report to Email ");
        EmailReport.sendReportToEmail();
    }

    @Override
    public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {

        String reportTemplate = initReportTemplate();

        String body = suites
                .stream()
                .flatMap(suiteToResults())
                .collect(Collectors.joining());

        String report
                = reportTemplate.replaceFirst("</tbody>", String.format("%s</tbody>", body));
        saveReportTemplate(outputDirectory, report);
    }

    private String initReportTemplate() {
        String template = null;
        byte[] reportTemplate;
        try {
            reportTemplate = Files.readAllBytes(Paths.get("src/main/resources/reporttemplates/reportTemplate.html"));
            template = new String(reportTemplate, "UTF-8");
        } catch (IOException e) {
            logger.debug("Problem initializing template", e);
        }
        return template;
    }

    private Function<ISuite, Stream<? extends String>> suiteToResults() {
        return suite -> suite.getResults().entrySet()
                .stream()
                .flatMap(resultsToRows(suite));
    }

    private Function<Map.Entry<String, ISuiteResult>, Stream<? extends String>> resultsToRows(ISuite suite) {
        return e -> {
            ITestContext testContext = e.getValue().getTestContext();

            Set<ITestResult> failedTests = testContext
                    .getFailedTests()
                    .getAllResults();
            Set<ITestResult> passedTests = testContext
                    .getPassedTests()
                    .getAllResults();
            Set<ITestResult> skippedTests = testContext
                    .getSkippedTests()
                    .getAllResults();

            String suiteName = suite.getName();

            return Stream
                    .of(failedTests, passedTests, skippedTests)
                    .flatMap(results -> generateReportRows(e.getKey(), suiteName, results).stream());
        };
    }

    private List<String> generateReportRows(String testName, String suiteName, Set<ITestResult> allTestResults) {
        return allTestResults.stream()
                .map(testResultToResultRow(testName, suiteName))
                .collect(toList());
    }

    private Function<ITestResult, String> testResultToResultRow(String testName, String suiteName) {
        return testResult -> {
            switch (testResult.getStatus()) {
                case ITestResult.FAILURE:
                    return String.format(ROW_TEMPLATE, "danger", suiteName, testName, testResult.getName(), "FAILED", "NA");

                case ITestResult.SUCCESS:
                    return String.format(ROW_TEMPLATE, "success", suiteName, testName, testResult.getName(), "PASSED", String.valueOf(testResult.getEndMillis() - testResult.getStartMillis()));

                case ITestResult.SKIP:
                    return String.format(ROW_TEMPLATE, "warning", suiteName, testName, testResult.getName(), "SKIPPED", "NA");

                default:
                    return "";
            }
        };
    }

    private void saveReportTemplate(String outputDirectory, String reportTemplate) {
        new File(outputDirectory).mkdirs();
        try {
            PrintWriter reportWriter = new PrintWriter(new BufferedWriter(new FileWriter(new File(outputDirectory, "my-report.html"))));
            reportWriter.println(reportTemplate);
            reportWriter.flush();
            reportWriter.close();
        } catch (IOException e) {
            logger.debug("Problem saving template", e);
        }
    }
}
