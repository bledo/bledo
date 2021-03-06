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


import co.bledo.mvc.Cookie;
import co.bledo.mvc.Request;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;


/**
 *
 * @author Ricardo Ramirez <ricardo@bledo.co>
 */
public interface Response {
	public Map<String, String> getHeaders(Request req);
	public void printBody(Request req, HttpServletResponse resp) throws Exception;
	public List<Cookie> getCookies(Request req);
	public int getStatus();
}
