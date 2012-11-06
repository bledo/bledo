package bledo.logger;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class ScreenLoggerWriter implements LoggerWritter
{
	private Level _level = Level.ALL;
	private DateFormat df = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
	
	
	@Override
	public void setLevel(Level level) {
		_level = level;
	}

	@Override
	public void writeLog(Level level, String className, String methodName, String msg) {
		if ( Level.loggable(_level, level) )
		{
			System.out.println("[" + df.format(new Date()) + "] " +  className + "." + methodName + "(" + level.getLabel()+ ") : " + msg);
		}
	}
}


