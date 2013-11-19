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

import java.util.Enumeration;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;

public class Session implements HttpSession
{
	private HttpSession sess;

	public Object get(String key) {
		return sess.getAttribute(key);
	}

	public Object get(String key, Object def) {
		Object ret =  sess.getAttribute(key);
		if (ret == null) {
			return def;
		}
		return ret;
	}

	public String getString(String key) {
		return (String) sess.getAttribute(key);
	}

	public String getString(String key, String def) {
		return (String) get(key, def);
	}

	public void put(String key, Object val) {
		sess.setAttribute(key, val);
	}

	public Session(HttpSession sess)
	{
		this.sess = sess;
	}

	@Override
	public long getCreationTime() {
		return sess.getCreationTime();
	}

	@Override
	public String getId() {
		return sess.getId();
	}

	@Override
	public long getLastAccessedTime() {
		return sess.getLastAccessedTime();
	}

	@Override
	public ServletContext getServletContext() {
		return sess.getServletContext();
	}

	@Override
	public void setMaxInactiveInterval(int interval) {
		sess.setMaxInactiveInterval(interval);
	}

	@Override
	public int getMaxInactiveInterval() {
		return sess.getMaxInactiveInterval();
	}

	@Override
	public HttpSessionContext getSessionContext() {
		return sess.getSessionContext();
	}

	@Override
	public Object getAttribute(String name) {
		return sess.getAttribute(name);
	}

	@Override
	public Object getValue(String name) {
		return sess.getValue(name);
	}

	@Override
	public Enumeration<String> getAttributeNames() {
		return sess.getAttributeNames();
	}

	@Override
	public String[] getValueNames() {
		return sess.getValueNames();
	}

	@Override
	public void setAttribute(String name, Object value) {
		sess.setAttribute(name, value);
	}

	@Override
	public void putValue(String name, Object value) {
		sess.putValue(name, value);
	}

	@Override
	public void removeAttribute(String name) {
		sess.removeAttribute(name);
	}

	@Override
	public void removeValue(String name) {
		sess.removeValue(name);
	}

	@Override
	public void invalidate() {
		sess.invalidate();
	}

	@Override
	public boolean isNew() {
		return sess.isNew();
	}
}






