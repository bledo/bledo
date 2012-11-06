package co.bledo.mvc.response;
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


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

import co.bledo.mvc.Cookie;
import co.bledo.mvc.Request;



public abstract class AbstractResponse implements Response {
	
	protected Map<String, String> headers = new HashMap<String, String>();
	protected List<Cookie> _cookies = new ArrayList<Cookie>();
	public void putHeader(String name, String val) { headers.put(name, val); }
	@Override public Map<String, String> getHeaders(Request req) { return headers; }
	@Override public List<Cookie> getCookies(Request req) { return _cookies; }
	public void addCookie(Cookie c) { _cookies.add(c); }
	
	private int _status = 200;
	public void setStatus(int status) {
		_status = status;
	}
	
	@Override
	public int getStatus()
	{
		return _status;
	}
}
