package Structure;

import java.io.FileWriter;
import java.sql.Connection;
import java.sql.Statement;

public class StartupWrapper {
	public FileWriter fw;
	public Connection myConnection;
	public Env env;
	public Statement myStatement;
	public StartupWrapper() {}
}
