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


import co.bledo.mvc.response.Response;
import java.util.Map;

public class Session {
	private static final co.bledo.logger.Logger log = co.bledo.logger.Logger.getLogger(Session.class);
	
	public static Session start(Request req)
	{
		return start(req, SessionStoreFile.getInst());
	}
	
	public static Session start(Request req, SessionStore store)
	{
		log.info("session.start");
		boolean isNew = false;
		String sessid = req.getCookie("BSID");
		
		if (sessid == null) {
			isNew = true;
			sessid = _gen_sess_id();
			log.debug("null sessid. generated : {0}", sessid);
		} else if (sessid.isEmpty()) {
			isNew = true;
			sessid = _gen_sess_id();
			log.debug("empty sessid. generated : {0}", sessid);
		}
		log.debug("Instantiating sessid : {0}", sessid);
		return new Session(sessid , store, isNew);
	}
	
	private static String _gen_sess_id()
	{
		return java.util.UUID.randomUUID().toString();
	}
	
	
	private String sessid;
	private boolean isNew;
	private boolean hasChanged = false;
	private SessionStore store;
	private Map<String, Object> _data;
	
	private Session(String id, SessionStore store, boolean isNew)
	{
		this.sessid = id;
		this.isNew = isNew;
		this.store = store;
		_data = store.read(sessid);
	}
	
	public Object get(String key) {
		hasChanged = true;
		return _data.get(key);
	}
	public String getString(String key) {
		return (String) get(key);
	}
	public void put(String key, Object val) {
		hasChanged = true;
		_data.put(key, val);
	}
	
	
	public void stop(Request req, Response resp)
	{
		if (isNew) {
			Cookie c = new Cookie("BSID", sessid);
			resp.getCookies(req).add(c);
		}
		
		if (hasChanged)
		{
			store.write(sessid, _data);
		}
	}
	
	public boolean isNew()
	{
		return isNew;
	}
	
	public void destroy()
	{
		store.destroy(sessid);
	}
}







