package co.bledo.rpc.json;
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


import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProxyService
{
	private static org.apache.logging.log4j.Logger log = org.apache.logging.log4j.LogManager.getLogger(ProxyService.class);
	
	protected Object _obj;
	protected Map<String, Method> methods = new HashMap<String, Method>();
	
	public class MethodDefinition
	{
		public String name;
		public String returnType;
		public List<String> params = new ArrayList<String>();
		
		public String toString()
		{
			StringBuilder sb = new StringBuilder();
			sb.append(returnType);
			sb.append(" ");
			sb.append(name);
			sb.append("(");
			int i = 0;
			for (String param : params)
			{
				if (i!=0) { sb.append(", "); }
				sb.append(param);
				i++;
			}
			sb.append(")");
			return sb.toString();
		}
	};
	
	
	public ProxyService(Object obj)
	{
		log.info("new ProxyService ... {0}", obj);
		
		
		_obj = obj;
		Method[] methodsArr = obj.getClass().getMethods();
		log.debug("found {0} methods", methodsArr.length);
		for (Method method : methodsArr)
		{
			String methodName = method.getName();
			
			JsonRpc jrpc = method.getAnnotation(JsonRpc.class);
			if (jrpc == null) {
				continue;
			}
			
			int mod = method.getModifiers();
			if ( ! Modifier.isPublic(mod) ) {
				log.debug("method {0} not public", methodName);
				continue;
			}
			if ( Modifier.isAbstract(mod) )
			{
				log.debug("method {0} is abstract", methodName);
				continue;
			}
			if ( Modifier.isStatic(mod) )
			{
				log.debug("method {0} is static", methodName);
				continue;
			}
			if ( Modifier.isInterface(mod) )
			{
				log.debug("method {0} is interface", methodName);
				continue;
			}
			if ( Modifier.isNative(mod) )
			{
				log.debug("method {0} is native", methodName);
				continue;
			}
			log.info("Found method {0}", methodName);
			methods.put(methodName, method);
		}
	}
	
	public List<MethodDefinition> getMethodDefinitios()
	{
		List<MethodDefinition> defs = new ArrayList<MethodDefinition>();
		for (String mName : methods.keySet())
		{
			Method m = methods.get(mName);
			MethodDefinition def = new MethodDefinition();
			def.name = mName;
			def.returnType = m.getReturnType().getName();
			for (Class cls : m.getParameterTypes())
			{
				def.params.add( cls.getName() );
			}
			
			defs.add(def);
		}
		
		return defs;
	}
	
	
	public boolean methodExists(String name)
	{
		return methods.containsKey(name);
	}
	
	public Object call(String methodName, List<Object> params) throws RpcException
	{
		Method method = methods.get(methodName);
		if (method == null) throw new RpcException("Method " + methodName + " not found");
		Object ret = null;
		try {
			ret = method.invoke(_obj, params.toArray());
		} catch (Exception e) {
			throw new RpcException(e);
		}
		return ret;
	}
}
