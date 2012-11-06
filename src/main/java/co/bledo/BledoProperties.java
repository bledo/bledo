package co.bledo;
/*
 *
 * Copyright 2012 The ClickPro.com LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *
*/


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






