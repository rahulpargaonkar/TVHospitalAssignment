import java.io.FileNotFoundException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.entity.City;
import com.entity.Hospital;
import com.entity.Patient;
import com.entity.Visit;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

public class TestHospital {
	private Hospital hospital;

	@BeforeClass
	public void setup() throws JsonSyntaxException, JsonIOException, FileNotFoundException {

		LocalDate today = LocalDate.now();
		System.out.println(today.minusDays(3));

		Visit patient1Visit1 = Visit.builder().doctorName("Dr. Krishna").visitDateTime(today).build();
		Visit patient1Visit2 = Visit.builder().doctorName("Dr. Mohan").visitDateTime(today.minusDays(1)).build();

		Visit patient2Visit1 = Visit.builder().doctorName("Dr. Diya").visitDateTime(today).build();
		Visit patient2Visit2 = Visit.builder().doctorName("Dr. Suraj").visitDateTime(today.minusDays(2)).build();
		Visit patient2Visit3 = Visit.builder().doctorName("Dr. Kiran").visitDateTime(today.minusDays(3)).build();

		Visit patient3Visit1 = Visit.builder().doctorName("Dr. Krishna").visitDateTime(today).build();
		Visit patient3Visit2 = Visit.builder().doctorName("Dr. Mohan").visitDateTime(today.minusDays(1)).build();

		Visit patient4Visit1 = Visit.builder().doctorName("Dr. Jagan").visitDateTime(today.minusDays(4)).build();
		Visit patient4Visit2 = Visit.builder().doctorName("Dr. Ram").visitDateTime(today.minusDays(3)).build();

		List<Visit> patient1Visit = new ArrayList<>();
		patient1Visit.add(patient1Visit1);
		patient1Visit.add(patient1Visit2);

		List<Visit> patient2Visit = new ArrayList<>();
		patient2Visit.add(patient2Visit1);
		patient2Visit.add(patient2Visit2);
		patient2Visit.add(patient2Visit3);

		List<Visit> patient3Visit = new ArrayList<>();
		patient3Visit.add(patient3Visit1);
		patient3Visit.add(patient3Visit2);

		List<Visit> patient4Visit = new ArrayList<>();
		patient4Visit.add(patient4Visit1);
		patient4Visit.add(patient4Visit2);

		Patient patient1 = Patient.builder().name("Ajay A").city(City.Pune).registrationDate(today).visit(patient1Visit)
				.build();

		Patient patient2 = Patient.builder().name("Sujay B").city(City.Bangalore).registrationDate(today.minusDays(1))
				.visit(patient2Visit).build();

		Patient patient3 = Patient.builder().name("Vijay C").city(City.Bangalore).registrationDate(today.minusDays(3))
				.visit(patient3Visit).build();

		Patient patient4 = Patient.builder().name("jay D").city(City.Bangalore).registrationDate(today.minusDays(4))
				.visit(patient4Visit).build();

		List<Patient> patientList = new ArrayList<Patient>();
		patientList.add(patient1);
		patientList.add(patient2);
		patientList.add(patient3);
		patientList.add(patient4);

		hospital = Hospital.builder().hospitalCity(City.Bangalore).patient(patientList).build();

	}

	@BeforeMethod
	public void LogMethod(Method method) {
		Test test = method.getAnnotation(Test.class);
		System.out.println("Test description== " + test.description());
	}

	@Test(description = "Test Local Patient percentage vs OutstationPatient Percentage")
	public void testLocalVsOutstationPatientPercentage() {
		String localVsOutstationPercentage = hospital.getLocalVsOutstationPercentage();
		Assert.assertEquals(localVsOutstationPercentage, "75.00 % Vs 25.00 %");
	}

	@Test(description = "Test Local Patient percentage vs OutstationPatient Percentage within Last N Registration Days")
	public void testLocalVsOutstationPatientPercentageWithinLastNRegitrationDays() throws ParseException {
		String localVsOutstationPercentage = hospital.getLocalVsOutstationPercentageWithinLastNdays(3);

		Assert.assertEquals(localVsOutstationPercentage, "50.00 % Vs 50.00 %");
	}

	@Test(description = "Test local Patients visited  in  Last N Visit Days")
	public void testNumberOfLocalPatientsVisitedInLastNdays() throws ParseException {

		int localpatientVisitedCountWithinLastNdays = hospital.getLocalpatientVisitedCountWithinLastNdays(3);
		Assert.assertEquals(localpatientVisitedCountWithinLastNdays, 2);

	}

}