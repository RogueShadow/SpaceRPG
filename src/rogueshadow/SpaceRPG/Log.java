package rogueshadow.SpaceRPG;

import java.io.PrintStream;

public class Log {
	
	public enum Level {
		DEBUG("Debug", 0),
		INFO("Info", 1),
		WARNING("Warning", 2),
		ERROR("Error", 3);
		
		private String name;
		private int priority;
		
		private Level(String name, int priority){
			this.name = name;
			this.priority = priority;
		}
		
		public String getName(){
			return name;
		}
		
		public int getPriority(){
			return priority;
		}
	}
	
	private static Level minLevel = Level.DEBUG;
	
	private static PrintStream ps = null;
	
	public static void debug(String module, String message){
		logMessage(Level.DEBUG, module, message);
	}
	public static void info(String module, String message){
		logMessage(Level.INFO, module, message);
	}
	public static void warning(String module, String message){
		logMessage(Level.WARNING, module, message);
	}
	public static void error(String module, String message){
		logMessage(Level.ERROR, module, message);
	}
	
	public static Level getMinimumPriorityLevel() {
		return minLevel;
	}
	
	public static synchronized void setMinimumPriorityLevel(Level level) {
		Log.minLevel = level;
	}
	
	public static PrintStream getPrintStream() {
		return  Log.ps;
	}
	
	public static synchronized void setPrintStream(PrintStream ps) {
		Log.ps = ps;
	}
	
	public static synchronized void logMessage(Level level, String module, String message) {
		if (level.getPriority() >= minLevel.getPriority()){
			String logMessage = level.getName() + " (" + module + "):\n" + message;
			if (ps == null){
				System.err.println(logMessage);
				System.err.flush();
			}else{
				ps.println(logMessage);
				ps.flush();
			}
		}
	}
	
	

}
