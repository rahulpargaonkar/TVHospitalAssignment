import java.io.FileNotFoundException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.controller.ObjectBuilder;
import com.entity.Hospital;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.utility.TestHelper;

public class TestHospital {
	private Hospital hospital;
	private SimpleDateFormat sdf;

	@BeforeClass
	public void setup() throws JsonSyntaxException, JsonIOException, FileNotFoundException {
		hospital = (Hospital) ObjectBuilder.buildObject(Hospital.class, "testPatient.json");
		sdf = new SimpleDateFormat("dd/MM/yyyy");

	}

	@BeforeMethod
	public void LogMethod(Method method) {
		Test test = method.getAnnotation(Test.class);
		System.out.println("Test description== " + test.description());
	}

	@Test(description = "Test Patient count for city within specified Appointment Date Range")
	public void testPatientCountForCityAndWithinAppointMentDateRange() throws ParseException {
		Date fromDate = sdf.parse("01/05/2021");
		Date toDate = sdf.parse("15/05/2021");
		Assert.assertEquals(TestHelper.getPatientCountforLocationWithDateRange(hospital, "Pune", fromDate, toDate), 1);

	}

	@Test(description = "Test Bangalore Patient percentage vs OutstationPatient Percentage")
	public void testPatientPercentageForOtherCityNameVsBangalore() {
		int totalPatientCount = hospital.getPatient().size();
		int bangaloreCount = TestHelper.getPatientCountforCity(hospital, "Bangalore");
		int outstationCount = totalPatientCount - bangaloreCount;
		String bangaloreVsOutstationPercentage = bangaloreCount * 100 / totalPatientCount + "% Vs "
				+ outstationCount * 100 / totalPatientCount + "%";
		Assert.assertEquals(bangaloreVsOutstationPercentage, "66% Vs 33%");
	}

	@Test(description = "Test Banglore Patient percentage vs OutstationPatient Percentage within specified Appointment Date Range")
	public void testPatientPercentageForOtherCityNameVsBangaloreWithinAppointmentDateRange() throws ParseException {
		int totalPatientCount = hospital.getPatient().size();
		Date fromDate = sdf.parse("01/05/2021");
		Date toDate = sdf.parse("20/05/2021");
		int bangaloreCount = TestHelper.getPatientCountforLocationWithDateRange(hospital, "Bangalore", fromDate,
				toDate);
		int outstationCount = TestHelper.getOutstationPatientCount(hospital, "Bangalore");
		String bangaloreVsOutstationPercentage = bangaloreCount * 100 / totalPatientCount + "% Vs "
				+ outstationCount * 100 / totalPatientCount + "%";
		Assert.assertEquals(bangaloreVsOutstationPercentage, "33% Vs 33%");
	}

	@Test(description = "Test Patient count for city within specified Registration Date Range")
	public void testPatientCountForCityAndWithinRegistrationDateRange() throws ParseException {
		Date fromDate = sdf.parse("01/04/2021");
		Date toDate = sdf.parse("25/04/2021");
		Assert.assertEquals(TestHelper.getPatientCountforLocationWithRegDateRange(hospital, "Pune", fromDate, toDate),
				1);

	}

	@Test(description = "Test Patient count for city")
	public void testPatientCountForCityName() {
		Assert.assertEquals(TestHelper.getPatientCountforCity(hospital, "Bangalore"), 2);
	}

}
