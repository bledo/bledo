package bledo.logger;

import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import bledo.BledoProperties;


public final class Logger {
	
	private static Level _level = Level.WARN;
	
	private static boolean _cfg_loaded = false;


	private static List<LoggerWritter> _writers = new ArrayList<LoggerWritter>();

	public static Logger getLogger(Class<?> clazz)
	{
		return new Logger(clazz.getName());
	}

	public static Logger getLogger(String name)
	{
		return new Logger(name);
	}

	private Logger(String name)
	{
		_init();
	}

	private synchronized void _init()
	{
		//
		if (_cfg_loaded) { return; }

		Properties rawProps = null;
		//
		try {
			rawProps = bledo.Util.loadProperties("bledo.properties");
		} catch (Exception e) {
			rawProps = new Properties();
		}
		BledoProperties props = (new BledoProperties(rawProps)).propPath("bledo.logger");

		// Debug Level
		_level = Level.parseLevel(
					props.get("level", Level.WARN.getLabel())
		);
		

		
		// Writers
		BledoProperties writersProps = props.propPath("writer");
		for (String writerLabel : writersProps.getRootKeys())
		{
			BledoProperties classProps = writersProps.propPath(writerLabel);
			String writerClass = classProps.get("class");
			if (writerClass == null) { continue; }
			
			// Writer Class
			LoggerWritter writer = null;
			try { writer = (LoggerWritter) bledo.Util.loadClass(writerClass, new Object[]{}); } catch (Exception e) { continue; }
			
			// Set level
			writer.setLevel( Level.parseLevel( classProps.get("level", _level.getLabel() ) ) );
			
			BledoProperties writerProps = classProps.propPath("prop");
			for (String methodName : writerProps.getRootKeys())
			{
				String methodArg = writerProps.get(methodName);
				if (methodArg == null) { continue; }
				
				// set
				Method method = null;
				try { method = writer.getClass().getMethod(methodName, String.class); } catch (Exception e) { continue; }
				try { method.invoke(writer, methodArg); } catch (Exception e) { continue; }
			}

			_writers.add(writer); // List<LoggerWriter>();
		}
		

		_cfg_loaded = true;
	}

	private void _log(Level level, String message, Object...args)
	{
		if ( Level.loggable(level, _level) )
		{
			Throwable t = new Throwable();
			StackTraceElement[] stackTrace = t.getStackTrace();
			String className;
			String methodName;
			if (stackTrace.length > 2)
			{
				className = stackTrace[2].getClassName();
				methodName = stackTrace[2].getMethodName();
			}
			else
			{
				className = "";
				methodName = "";
			}

			for (LoggerWritter w : _writers)
			{
				w.writeLog(level, className, methodName, MessageFormat.format(message, args) );
			}
		}
	}

	private void _loge(Level level, String message, Throwable e)
	{
		if ( Level.loggable(_level, level) )
		{
			final String NL = System.getProperty("line.separator");
			StringBuilder sb = new StringBuilder(e.getMessage());
			sb.append(NL);
			for (StackTraceElement element : e.getStackTrace() ){
				sb.append("\t");
				sb.append( element );
				sb.append( NL );
			}

			_log(level, message, new Object[]{sb.toString()});
		}
	}


	public void debug(String message, Object...args) { _log(Level.DEBUG, message, args); }
	public void debug(String message, Exception e) { _loge(Level.DEBUG, message, e); } 
	
	public void info(String message, Object...args) { _log(Level.INFO, message, args); } 
	public void info(String message, Exception e) { _loge(Level.INFO, message, e); } 
	
	public void warn(String message, Object...args) { _log(Level.WARN, message, args); } 
	public void warn(String message, Exception e) { _loge(Level.WARN, message, e); }
	
	public void error(String message, Object...args) { _log(Level.ERROR, message, args); }
	public void error(String message, Exception e) { _loge(Level.ERROR, message, e); } 
}









