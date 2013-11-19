package co.bledo.mvc;
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


import java.util.Map;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Ricardo Ramirez <ricardo@bledo.co>
 */
public interface Request extends HttpServletRequest
{
	
	/**
	 * Returns full request URI
	 * E.g.
	 * 	http://example.com/path/base/controller/action
	 * 
	 */
	public String getUri();
	public String getAction();
	public Map<String, Object> getParamMap();
	public String getParam(String k);
	public String getParam(String k, String def_val);

	@Override
	public String getScheme();
	public String getHost();
	public int getPort();
	@Override
	public Session getSession();
	@Override
	public Session getSession(boolean create);
}




