package bledo.logger;

public class Level
{
	private int _value = 0;
	private String _label = "OFF";
	
	private Level(int val, String label)
	{
		_value = val;
		_label = label;
	}
	
	public int getValue()
	{
		return _value;
	}
	
	public String getLabel()
	{
		return _label;
	}
	
	public static Level parseLevel(String level)
	{
		if ( ERROR.getLabel().equalsIgnoreCase(level)) { return ERROR; }
		else if ( WARN.getLabel().equalsIgnoreCase(level)) { return WARN; }
		else if ( INFO.getLabel().equalsIgnoreCase(level)) { return INFO; }
		else if ( DEBUG.getLabel().equalsIgnoreCase(level)) { return DEBUG; }
		else if ( ALL.getLabel().equalsIgnoreCase(level)) { return ALL; }
		
		return OFF;
	}
	
	public static boolean loggable(Level root, Level arg)
	{
		return ((root.getValue() & arg.getValue()) > 0);
	}
	
	public static Level ALL = new Level(-1, "ALL");
	public static Level DEBUG = new Level(8, "DEBUG");
	public static Level INFO = new Level(4, "INFO");
	public static Level WARN = new Level(2, "WARN");
	public static Level ERROR = new Level(1, "ERROR");
	public static Level OFF = new Level(0, "OFF");
}









