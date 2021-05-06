import java.io.FileNotFoundException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.controller.ObjectBuilder;
import com.entity.AppointmentDetails;
import com.entity.Disease;
import com.entity.Doctor;
import com.entity.Hospital;
import com.entity.Patient;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;


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
		Assert.assertEquals(hospital.getPatientCountforLocationWithDateRange(hospital, "Pune", fromDate, toDate), 1);

	}

	@Test(description = "Test Bangalore Patient percentage vs OutstationPatient Percentage")
	public void testPatientPercentageForOtherCityNameVsBangalore() {
		int totalPatientCount = hospital.getPatient().size();
		int bangaloreCount = hospital.getPatientCountforCity(hospital, "Bangalore");
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
		int bangaloreCount = hospital.getPatientCountforLocationWithDateRange(hospital, "Bangalore", fromDate, toDate);
		int outstationCount = hospital.getOutstationPatientCount(hospital, "Bangalore");
		String bangaloreVsOutstationPercentage = bangaloreCount * 100 / totalPatientCount + "% Vs "
				+ outstationCount * 100 / totalPatientCount + "%";
		Assert.assertEquals(bangaloreVsOutstationPercentage, "33% Vs 33%");
	}

	@Test(description = "Test Patient count for city within specified Registration Date Range")
	public void testPatientCountForCityAndWithinRegistrationDateRange() throws ParseException {
		Date fromDate = sdf.parse("01/04/2021");
		Date toDate = sdf.parse("25/04/2021");
		Assert.assertEquals(hospital.getPatientCountforLocationWithRegDateRange(hospital, "Pune", fromDate, toDate), 1);

	}

	@Test(description = "Test Patient count for city")
	public void testPatientCountForCityName() {
		Assert.assertEquals(hospital.getPatientCountforCity(hospital, "Bangalore"), 2);
	}

	@Test(description = "Test Patient count for city")
	public void testPatientCountForCityName1() throws ParseException {
		Patient patient1 = Patient.builder().name("Sunil T").city("Bangalore").phoneNumber("234234234")
				.disease(Disease.builder().diseaseName("Throat Infection").isCriticaldisease(false).build())
				.appointment(AppointmentDetails.builder().apointmentDateTime(sdf.parse("19/05/2021"))
						.appointmentCity("Bangalore").registrationDateTime(sdf.parse("16/04/2021"))
						.doctor(Doctor.builder().doctorName("Dr. Ram").doctorCity("Bangalore").build()).build())
				.build();

		Patient patient2 = Patient.builder().name("Sunil T1").city("Mumbai").phoneNumber("2324234234")
				.disease(Disease.builder().diseaseName("Eye Problem").isCriticaldisease(true).build())
				.appointment(AppointmentDetails.builder().apointmentDateTime(sdf.parse("04/05/2021"))
						.appointmentCity("Bangalore").registrationDateTime(sdf.parse("06/04/2021"))
						.doctor(Doctor.builder().doctorName("Dr. Sham").doctorCity("Bangalore").build()).build())
				.build();

		List<Patient> patientList = new ArrayList<>();

		patientList.add(patient1);
		patientList.add(patient2);
		Hospital hos = Hospital.builder().hospitalName("Chinmaya Mission").hospitalCity("Bangalore")
				.patient(patientList).build();

		Assert.assertEquals(hospital.getPatientCountforCity(hos, "Bangalore"), 1);
	}

}
