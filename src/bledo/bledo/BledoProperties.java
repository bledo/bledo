package bledo;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class BledoProperties
{
	protected Properties _props;
	
	public BledoProperties(Properties props)
	{
		_props = props;
	}
	
	public BledoProperties propPath(String path)
	{
		String _path = Util.trim(path, ".") + ".";
		Properties sub = new Properties();
		
		for (Object key : _props.keySet())
		{
			if ( ((String) key).startsWith(_path) )
			{
				String newKey = ((String) key).replace(_path, "");
				sub.setProperty(newKey, _props.getProperty((String)key) );
			}
		}
		
		return new BledoProperties(sub);
	}
	
	public List<String> getRootKeys()
	{
		List<String> list = new ArrayList<String>();
		
		for (Object oKey : _props.keySet())
		{
			String key = (String) oKey;
			String[] parts = key.split("\\.");
			if (parts.length < 1) { continue; }
			list.add(parts[0]);
		}
		
		return list;
	}
	
	public String get(String key, String def)
	{
		return _props.getProperty(key, def);
	}
	
	public String get(String key)
	{
		return _props.getProperty(key);
	}
}






