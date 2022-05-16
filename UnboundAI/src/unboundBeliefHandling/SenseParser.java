package unboundBeliefHandling;
import java.util.*;
import java.io.*;
import unboundStruct.*;
public class SenseParser {
	public static List<Sense> parse(String filePathIn) throws Exception{
		File senseResultSource = new File(filePathIn);
		List<Sense> sensesFound = new ArrayList<Sense>();
		Scanner senseScan = new Scanner(senseResultSource);
		senseScan.useDelimiter("UUUUU");
		while (senseScan.hasNext()) {
			List<Property> props = new ArrayList<Property>();
			String senseString = senseScan.next();
			Scanner indSenseScan = new Scanner(senseString);
			String senseLabel = indSenseScan.next();
			System.out.println("SenseLabel: " + senseLabel);
			indSenseScan.useDelimiter(",,,"); //senseScan turns into propertyScanner after retrieving label
			while (indSenseScan.hasNext()) {
				String indPropStr = indSenseScan.next();
				while (indPropStr.substring(0, 1).equals(" ")) {
					indPropStr = indPropStr.substring(1);
				}
				System.out.println("indPropStr: " + indPropStr);
				Scanner indPropScanner = new Scanner(indPropStr);
				indPropScanner.useDelimiter(",");
				String propType = indPropScanner.next();
				System.out.println("PropType: " + propType);
				String propName = indPropScanner.next();
				System.out.println("PropLabel: " + propName);
				String propValueString = indPropScanner.next();
				System.out.println("PropValueString: " + propValueString);
				Property prop = new Property(propType, propName, propValueString);
				props.add(prop);
				indPropScanner.close();
			}
			indSenseScan.close();
			Sense currSense = new Sense(senseLabel, props);
			sensesFound.add(currSense);		
		}
		senseScan.close();
		return sensesFound;
	}
	
	public static void main(String[] args) throws Exception {
		String path = "/home/agi/Desktop/eclipse/UnboundAI/bin/testParser.txt";
		List<Sense> sensesFound = SenseParser.parse(path);
		System.out.println(sensesFound.size());
		System.out.println(sensesFound.get(0).properties.size());
		
	}
}
