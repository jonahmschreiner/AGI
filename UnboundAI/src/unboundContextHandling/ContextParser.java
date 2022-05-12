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
		errorScanner.close();	
		Context context = new Context(env, satisfaction, prevExecActionName, errors);
		return context;
	}
}
