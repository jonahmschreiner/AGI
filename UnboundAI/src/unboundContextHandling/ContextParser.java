package unboundContextHandling;
import unboundStruct.*;
import java.util.*;
import java.awt.Point;
import java.io.*;
public class ContextParser {
	public static Context parse() throws Exception {
		File contextSource = new File("/home/agi/Desktop/eclipse/UnboundAI/bin/contextSource.txt");
		Scanner scan = new Scanner(contextSource);
		scan.useDelimiter(",,,,,");
		//Satisfaction
		int satisfaction = scan.nextInt();
		//prevExecAction
		String prevExecActionName = scan.next();
		//Env
		String currentEnv = scan.next();	
		Scanner scanEnv = new Scanner(currentEnv);
		scanEnv.useDelimiter(",");
		Env env = new Env();
		env.mouseLocation = new Point(scanEnv.nextInt(), scanEnv.nextInt());
		env.keyPressed = scanEnv.next();
		env.numOfMouseButtons = scanEnv.nextInt();
		env.leftMouseButtonPressed = Boolean.valueOf(scanEnv.next());
		env.middleMouseButtonPressed = Boolean.valueOf(scanEnv.next());
		env.rightMouseButtonPressed = Boolean.valueOf(scanEnv.next());
		env.calendar = java.time.LocalDateTime.parse(scanEnv.next());
		env.currentCpuUsage = scanEnv.nextDouble();
		scanEnv.close();
		//Errors
		List<Instruction> errors = new ArrayList<Instruction>();
		String errorsString = scan.next();
		scan.close();
		Scanner errorScanner = new Scanner(errorsString);
		while (errorScanner.hasNextLine()) {
			String errorString = errorScanner.nextLine();
			if (errorString.equals("ENDERRORS")) {
				break;
			}
			if (!errorString.isEmpty()) {
				Scanner errorScan = new Scanner(errorString);
				errorScan.useDelimiter(",,,");
				String parentClass = errorScan.next();
				int lineNumber = errorScan.nextInt();
				String instruction = errorScan.next();
				Instruction error = new Instruction(parentClass, lineNumber, instruction);
				errors.add(error);
				errorScan.close();
			}
		}
		env.errorLocations = errors;
		//Hypothesis Info
		List<Hypothesis> hypos = new ArrayList<Hypothesis>();
		while (errorScanner.hasNextLine()) {
			String hypoGenStr = errorScanner.nextLine();
			Scanner hypoScan = new Scanner(hypoGenStr);
			hypoScan.useDelimiter(",");
			String actionFilePath = hypoScan.next();
			String actionName = hypoScan.next();
			String ratingsStr = hypoScan.next();
			Scanner ratingScan = new Scanner(ratingsStr);
			List<Rating> ratings = new ArrayList<Rating>();
			while (ratingScan.hasNext()) {
				String goalFilePath = ratingScan.next();
				if (ratingScan.hasNext()) {
					String goalName = ratingScan.next();
					if (ratingScan.hasNext()) {
						int ratingValue = Integer.valueOf(ratingScan.next());
						Rating rating = new Rating(goalFilePath, goalName, ratingValue);
						ratings.add(rating);
					}
				}
			}
			ratingScan.close();
			hypoScan.close();
			Hypothesis hypo = new Hypothesis(actionFilePath, actionName, ratings);
			hypos.add(hypo);
		}
		errorScanner.close();	
		Context context = new Context(env, satisfaction, prevExecActionName, hypos);
		return context;
	}
}
