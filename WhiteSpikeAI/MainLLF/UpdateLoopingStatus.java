package MainLLF;

import java.sql.ResultSet;

import Structure.LFInputWrapper;

public class UpdateLoopingStatus {
	public static LFInputWrapper exec(LFInputWrapper LFInput) {
		try {
			ResultSet rs = LFInput.sw.myStatement.executeQuery("SELECT * FROM Activity WHERE SolvedStatus=0 LIMIT 1;");
			try {
				rs.next();
				@SuppressWarnings("unused")
				String id = rs.getString("id");
			} catch (Exception e) {
				LFInput.continueLooping = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return LFInput;
	}
}
