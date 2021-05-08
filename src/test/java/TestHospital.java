import java.io.FileNotFoundException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.controller.ObjectBuilder;
import com.entity.Hospital;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.utility.DateHelper;

public class TestHospital {
	private Hospital hospital;
	private DateHelper dateHelper;

	@BeforeClass
	public void setup() throws JsonSyntaxException, JsonIOException, FileNotFoundException {
		hospital = (Hospital) ObjectBuilder.buildObject(Hospital.class, "testPatient.json");
		dateHelper = new DateHelper();

	}

	@BeforeMethod
	public void LogMethod(Method method) {
		Test test = method.getAnnotation(Test.class);
		System.out.println("Test description== " + test.description());
	}

	@Test(description = "Test Local Patient percentage vs OutstationPatient Percentage")
	public void testPatientPercentageForOtherCityNameVslocal() {
		String localVsOutstationPercentage = hospital.getLocalVsOutstationPatientPercentage();
		Assert.assertEquals(localVsOutstationPercentage, "75.00 % Vs 25.00 %");
	}

	@Test(description = "Test Local Patient percentage vs OutstationPatient Percentage within Last N Registration Days")
	public void testPatientPercentageForOtherCityNameVsLocalWithinLastNDays() throws ParseException {
		List<Date> dateRangeList = dateHelper.getFormatedDatesForRange(3);
		Date fromDate = dateRangeList.get(0);
		Date toDate = dateRangeList.get(1);

		int localCount = hospital.getPatientCountforLocalWithRegistrationDateRange(fromDate, toDate);
		int outstationCount = hospital.getOutstationPatientCount();

		String localVsOutstationPercentage = hospital.getformattedLocalVsOutstationCount(localCount, outstationCount);

		Assert.assertEquals(localVsOutstationPercentage, "50.00 % Vs 25.00 %");
	}

	@Test(description = "Test local Patient visit in  Last N Visit Days")
	public void testNumberOfLocalPatientVisitedInLastNdays() throws ParseException {
		List<Date> dateRangeList = dateHelper.getFormatedDatesForRange(3);
		Date fromDate = dateRangeList.get(0);
		Date toDate = dateRangeList.get(1);

		int localpatientVisitedCountWithinLastNdays = hospital.getLocalpatientVisitedCountWithinLastNdays(fromDate,
				toDate);
		Assert.assertEquals(localpatientVisitedCountWithinLastNdays, 3);

	}

}