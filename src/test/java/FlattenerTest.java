import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.jackson.Jackson;
import io.jsonflattener.service.FlattenerService;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FlattenerTest {
    private static final ObjectMapper MAPPER = Jackson.newObjectMapper();
    private static final FlattenerService service = new FlattenerService(MAPPER);

    @Test
    public void sanityTestForFlattener() throws Exception {
        String input = "{\"problems\":[{\"Diabetes\":[{\"medications\":[{\"medicationsClasses\":[{\"className\":[{\"associatedDrug\":[{\"name\":\"asprin\",\"dose\":\"\",\"strength\":\"500 mg\"},{\"name\":\"somethingElse\",\"dose\":\"\",\"strength\":\"500 mg\"}]}],\"B\":[{\"associatedDrug\":[{\"name\":\"asprin\",\"dose\":\"\",\"strength\":\"500 mg\"},{\"name\":\"somethingElse\",\"dose\":\"\",\"strength\":\"500 mg\"}],\"labs\":[{\"missing_field\":\"missing_value\"}]}],\"Asthma\":[{}]}]}]}]}]}";
        assertEquals("{\"problems[0].Diabetes[0].medications[0].medicationsClasses[0].className[0].associatedDrug[0].name\":\"asprin\",\"problems[0].Diabetes[0].medications[0].medicationsClasses[0].className[0].associatedDrug[0].dose\":\"\",\"problems[0].Diabetes[0].medications[0].medicationsClasses[0].className[0].associatedDrug[0].strength\":\"500 mg\",\"problems[0].Diabetes[0].medications[0].medicationsClasses[0].className[0].associatedDrug[1].name\":\"somethingElse\",\"problems[0].Diabetes[0].medications[0].medicationsClasses[0].className[0].associatedDrug[1].dose\":\"\",\"problems[0].Diabetes[0].medications[0].medicationsClasses[0].className[0].associatedDrug[1].strength\":\"500 mg\",\"problems[0].Diabetes[0].medications[0].medicationsClasses[0].B[0].associatedDrug[0].name\":\"asprin\",\"problems[0].Diabetes[0].medications[0].medicationsClasses[0].B[0].associatedDrug[0].dose\":\"\",\"problems[0].Diabetes[0].medications[0].medicationsClasses[0].B[0].associatedDrug[0].strength\":\"500 mg\",\"problems[0].Diabetes[0].medications[0].medicationsClasses[0].B[0].associatedDrug[1].name\":\"somethingElse\",\"problems[0].Diabetes[0].medications[0].medicationsClasses[0].B[0].associatedDrug[1].dose\":\"\",\"problems[0].Diabetes[0].medications[0].medicationsClasses[0].B[0].associatedDrug[1].strength\":\"500 mg\",\"problems[0].Diabetes[0].medications[0].medicationsClasses[0].B[0].labs[0].missing_field\":\"missing_value\"}", MAPPER.writeValueAsString(service.flattenJson(input)));
    }

    @Test
    public void testArrayFlattener() throws Exception {
        String input = "{\"medications\":[{\"aceInhibitors\":[{\"name\":\"lisinopril\",\"strength\":\"10 mg Tab\",\"dose\":\"1 tab\",\"route\":\"PO\",\"sig\":\"daily\",\"pillCount\":\"#90\",\"refills\":\"Refill 3\"}],\"antianginal\":[{\"name\":\"nitroglycerin\",\"strength\":\"0.4 mg Sublingual Tab\",\"dose\":\"1 tab\",\"route\":\"SL\",\"sig\":\"q15min PRN\",\"pillCount\":\"#30\",\"refills\":\"Refill 1\"}],\"anticoagulants\":[{\"name\":\"warfarin sodium\",\"strength\":\"3 mg Tab\",\"dose\":\"1 tab\",\"route\":\"PO\",\"sig\":\"daily\",\"pillCount\":\"#90\",\"refills\":\"Refill 3\"}],\"betaBlocker\":[{\"name\":\"metoprolol tartrate\",\"strength\":\"25 mg Tab\",\"dose\":\"1 tab\",\"route\":\"PO\",\"sig\":\"daily\",\"pillCount\":\"#90\",\"refills\":\"Refill 3\"}],\"diuretic\":[{\"name\":\"furosemide\",\"strength\":\"40 mg Tab\",\"dose\":\"1 tab\",\"route\":\"PO\",\"sig\":\"daily\",\"pillCount\":\"#90\",\"refills\":\"Refill 3\"}],\"mineral\":[{\"name\":\"potassium chloride ER\",\"strength\":\"10 mEq Tab\",\"dose\":\"1 tab\",\"route\":\"PO\",\"sig\":\"daily\",\"pillCount\":\"#90\",\"refills\":\"Refill 3\"}]}],\"labs\":[{\"name\":\"Arterial Blood Gas\",\"time\":\"Today\",\"location\":\"Main Hospital Lab\"},{\"name\":\"BMP\",\"time\":\"Today\",\"location\":\"Primary Care Clinic\"},{\"name\":\"BNP\",\"time\":\"3 Weeks\",\"location\":\"Primary Care Clinic\"},{\"name\":\"BUN\",\"time\":\"1 Year\",\"location\":\"Primary Care Clinic\"},{\"name\":\"Cardiac Enzymes\",\"time\":\"Today\",\"location\":\"Primary Care Clinic\"},{\"name\":\"CBC\",\"time\":\"1 Year\",\"location\":\"Primary Care Clinic\"},{\"name\":\"Creatinine\",\"time\":\"1 Year\",\"location\":\"Main Hospital Lab\"},{\"name\":\"Electrolyte Panel\",\"time\":\"1 Year\",\"location\":\"Primary Care Clinic\"},{\"name\":\"Glucose\",\"time\":\"1 Year\",\"location\":\"Main Hospital Lab\"},{\"name\":\"PT/INR\",\"time\":\"3 Weeks\",\"location\":\"Primary Care Clinic\"},{\"name\":\"PTT\",\"time\":\"3 Weeks\",\"location\":\"Coumadin Clinic\"},{\"name\":\"TSH\",\"time\":\"1 Year\",\"location\":\"Primary Care Clinic\"}],\"imaging\":[{\"name\":\"Chest X-Ray\",\"time\":\"Today\",\"location\":\"Main Hospital Radiology\"},{\"name\":\"Chest X-Ray\",\"time\":\"Today\",\"location\":\"Main Hospital Radiology\"},{\"name\":\"Chest X-Ray\",\"time\":\"Today\",\"location\":\"Main Hospital Radiology\"}]}";
        assertEquals("{\"medications[0].aceInhibitors[0].name\":\"lisinopril\",\"medications[0].aceInhibitors[0].strength\":\"10 mg Tab\",\"medications[0].aceInhibitors[0].dose\":\"1 tab\",\"medications[0].aceInhibitors[0].route\":\"PO\",\"medications[0].aceInhibitors[0].sig\":\"daily\",\"medications[0].aceInhibitors[0].pillCount\":\"#90\",\"medications[0].aceInhibitors[0].refills\":\"Refill 3\",\"medications[0].antianginal[0].name\":\"nitroglycerin\",\"medications[0].antianginal[0].strength\":\"0.4 mg Sublingual Tab\",\"medications[0].antianginal[0].dose\":\"1 tab\",\"medications[0].antianginal[0].route\":\"SL\",\"medications[0].antianginal[0].sig\":\"q15min PRN\",\"medications[0].antianginal[0].pillCount\":\"#30\",\"medications[0].antianginal[0].refills\":\"Refill 1\",\"medications[0].anticoagulants[0].name\":\"warfarin sodium\",\"medications[0].anticoagulants[0].strength\":\"3 mg Tab\",\"medications[0].anticoagulants[0].dose\":\"1 tab\",\"medications[0].anticoagulants[0].route\":\"PO\",\"medications[0].anticoagulants[0].sig\":\"daily\",\"medications[0].anticoagulants[0].pillCount\":\"#90\",\"medications[0].anticoagulants[0].refills\":\"Refill 3\",\"medications[0].betaBlocker[0].name\":\"metoprolol tartrate\",\"medications[0].betaBlocker[0].strength\":\"25 mg Tab\",\"medications[0].betaBlocker[0].dose\":\"1 tab\",\"medications[0].betaBlocker[0].route\":\"PO\",\"medications[0].betaBlocker[0].sig\":\"daily\",\"medications[0].betaBlocker[0].pillCount\":\"#90\",\"medications[0].betaBlocker[0].refills\":\"Refill 3\",\"medications[0].diuretic[0].name\":\"furosemide\",\"medications[0].diuretic[0].strength\":\"40 mg Tab\",\"medications[0].diuretic[0].dose\":\"1 tab\",\"medications[0].diuretic[0].route\":\"PO\",\"medications[0].diuretic[0].sig\":\"daily\",\"medications[0].diuretic[0].pillCount\":\"#90\",\"medications[0].diuretic[0].refills\":\"Refill 3\",\"medications[0].mineral[0].name\":\"potassium chloride ER\",\"medications[0].mineral[0].strength\":\"10 mEq Tab\",\"medications[0].mineral[0].dose\":\"1 tab\",\"medications[0].mineral[0].route\":\"PO\",\"medications[0].mineral[0].sig\":\"daily\",\"medications[0].mineral[0].pillCount\":\"#90\",\"medications[0].mineral[0].refills\":\"Refill 3\",\"labs[0].name\":\"Arterial Blood Gas\",\"labs[0].time\":\"Today\",\"labs[0].location\":\"Main Hospital Lab\",\"labs[1].name\":\"BMP\",\"labs[1].time\":\"Today\",\"labs[1].location\":\"Primary Care Clinic\",\"labs[2].name\":\"BNP\",\"labs[2].time\":\"3 Weeks\",\"labs[2].location\":\"Primary Care Clinic\",\"labs[3].name\":\"BUN\",\"labs[3].time\":\"1 Year\",\"labs[3].location\":\"Primary Care Clinic\",\"labs[4].name\":\"Cardiac Enzymes\",\"labs[4].time\":\"Today\",\"labs[4].location\":\"Primary Care Clinic\",\"labs[5].name\":\"CBC\",\"labs[5].time\":\"1 Year\",\"labs[5].location\":\"Primary Care Clinic\",\"labs[6].name\":\"Creatinine\",\"labs[6].time\":\"1 Year\",\"labs[6].location\":\"Main Hospital Lab\",\"labs[7].name\":\"Electrolyte Panel\",\"labs[7].time\":\"1 Year\",\"labs[7].location\":\"Primary Care Clinic\",\"labs[8].name\":\"Glucose\",\"labs[8].time\":\"1 Year\",\"labs[8].location\":\"Main Hospital Lab\",\"labs[9].name\":\"PT/INR\",\"labs[9].time\":\"3 Weeks\",\"labs[9].location\":\"Primary Care Clinic\",\"labs[10].name\":\"PTT\",\"labs[10].time\":\"3 Weeks\",\"labs[10].location\":\"Coumadin Clinic\",\"labs[11].name\":\"TSH\",\"labs[11].time\":\"1 Year\",\"labs[11].location\":\"Primary Care Clinic\",\"imaging[0].name\":\"Chest X-Ray\",\"imaging[0].time\":\"Today\",\"imaging[0].location\":\"Main Hospital Radiology\",\"imaging[1].name\":\"Chest X-Ray\",\"imaging[1].time\":\"Today\",\"imaging[1].location\":\"Main Hospital Radiology\",\"imaging[2].name\":\"Chest X-Ray\",\"imaging[2].time\":\"Today\",\"imaging[2].location\":\"Main Hospital Radiology\"}", MAPPER.writeValueAsString(service.flattenJson(input)));
    }

    @Test
    public void testObjectFlattener() throws Exception {
        String input = "{\"cluster\":{\"flights\":4,\"profit\":5245,\"clv\":2364,\"segment\":{\"flights\":2,\"profit\":2150,\"clv\":1564,\"node\":{\"xpos\":1,\"ypos\":2}}}}";
        assertEquals("{\"cluster.flights\":4,\"cluster.profit\":5245,\"cluster.clv\":2364,\"cluster.segment.flights\":2,\"cluster.segment.profit\":2150,\"cluster.segment.clv\":1564,\"cluster.segment.node.xpos\":1,\"cluster.segment.node.ypos\":2}", MAPPER.writeValueAsString(service.flattenJson(input)));
    }

    @Test
    public void sanityTestForUnFlattener() throws Exception {
        String input = "{\n" +
                "    \"ArrayAttribute1[0].alertMessage\": \"You have consumed all of your data allowance\",\n" +
                "    \"ArrayAttribute1[0].promoName\": \"MyPromoTest\",\n" +
                "    \"ArrayAttribute2[0].showmorepromosbutton\": \"true\",\n" +
                "    \"ArrayAttribute1[0].promoPrice\": \"P 149.00\",\n" +
                "    \"userType\": 1,\n" +
                "    \"Attribute1\": \"Jan 28 2016 . 3:09PM\",\n" +
                "    \"Attribute2.validityColor\": \"RED\",\n" +
                "    \"Attribute2.subscriberBal\": \"P 29.5\"\n" +
                "}";
        assertEquals("{\"ArrayAttribute1\":[{\"alertMessage\":\"You have consumed all of your data allowance\",\"promoName\":\"MyPromoTest\",\"promoPrice\":\"P 149.00\"}],\"ArrayAttribute2\":[{\"showmorepromosbutton\":\"true\"}],\"userType\":1,\"Attribute1\":\"Jan 28 2016 . 3:09PM\",\"Attribute2\":{\"validityColor\":\"RED\",\"subscriberBal\":\"P 29.5\"}}", MAPPER.writeValueAsString(service.unflattenJson(input)));
    }

    @Test
    public void testArrayUnFlattener() throws Exception {
        String input = "{\n" +
                "    \"ArrayAttribute1[0].alertMessage\": \"You have consumed all of your data allowance\",\n" +
                "    \"ArrayAttribute1[0].promoName\": \"MyPromoTest\",\n" +
                "    \"ArrayAttribute2[0].showmorepromosbutton\": \"true\",\n" +
                "    \"ArrayAttribute1[0].promoPrice\": \"P 149.00\"\n" +
                "}";
        assertEquals("{\"ArrayAttribute1\":[{\"alertMessage\":\"You have consumed all of your data allowance\",\"promoName\":\"MyPromoTest\",\"promoPrice\":\"P 149.00\"}],\"ArrayAttribute2\":[{\"showmorepromosbutton\":\"true\"}]}", MAPPER.writeValueAsString(service.unflattenJson(input)));
    }

    @Test
    public void testObjectUnFlattener() throws Exception {
        String input = "{\n" +
                "    \"userType\": 1,\n" +
                "    \"Attribute1\": \"Jan 28 2016 . 3:09PM\",\n" +
                "    \"Attribute2.validityColor\": \"RED\",\n" +
                "    \"Attribute2.subscriberBal\": \"P 29.5\"\n" +
                "}";
        assertEquals("{\"userType\":1,\"Attribute1\":\"Jan 28 2016 . 3:09PM\",\"Attribute2\":{\"validityColor\":\"RED\",\"subscriberBal\":\"P 29.5\"}}", MAPPER.writeValueAsString(service.unflattenJson(input)));
    }

}
