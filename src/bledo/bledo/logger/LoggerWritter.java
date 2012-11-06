package bledo.logger;

public interface LoggerWritter
{
	public void setLevel(Level level);
	public void writeLog(Level level, String className, String methodName, String msg);
}
