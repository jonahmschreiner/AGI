package unboundBeliefHandling;
import java.io.*;
import java.util.*;
import unboundStruct.*;
public class SenseWriter {
	public static void write(Sense senseIn, int parallelIn) throws Exception {
		String outSource = senseIn.label + " ";
		for (int i = 0; i < senseIn.properties.size(); i++) {
			outSource = outSource + senseIn.properties.get(i).propType + ",";
			outSource = outSource + senseIn.properties.get(i).propLabel + ",";
			outSource = outSource + senseIn.properties.get(i).valuesString;
			if ((i + 1) < senseIn.properties.size()) {
				outSource = outSource + "PPPPP";
			}
		}
		outSource = outSource + "UUUUU\n";
		System.out.println("outSource: " + outSource);
		System.out.println(parallelIn);
		try {
			File file = new File("/home/agi/Desktop/eclipse/UnboundAI/bin/sense" + parallelIn);
			FileWriter writer = new FileWriter(file);
			writer.write(outSource);
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public static void main(String[] args) throws Exception {
		Sense sense = new Sense();
		sense.label = "test";
		List<Property> properties = new ArrayList<Property>();
		Property prop1 = new Property("String", "test1", "hello");
		properties.add(prop1);
		Property prop2 = new Property("List<String>", "test2", "String test2 test String test3 yes");
		properties.add(prop2);
		sense.properties = properties;
		SenseWriter.write(sense, 0);
	}
}
