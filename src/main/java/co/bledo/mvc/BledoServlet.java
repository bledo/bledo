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
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class BledoServlet extends HttpServlet
{
	private final static co.bledo.logger.Logger log = co.bledo.logger.Logger.getLogger(BledoServlet.class);

	protected String default_method_name = "index";

	private Map<String, Method> actionMap = null;
	
	@Override protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processServletRequest(request, response);
	}

	@Override protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processServletRequest(request, response);
	}

	protected void processServletRequest(HttpServletRequest servletRequest, HttpServletResponse servletResponse) throws ServletException, IOException
	{
		_init();
		Request request = new HttpRequest(servletRequest, default_method_name);
		Response response = null;

		try
		{
			try
			{
				response = processRequest(request);
			}
			catch (Exception e)
			{
				response = processRequestError(request, e);
			}

			//
			if (response == null) {
				response = processRequestError(request, new ServletException("Empty Response") );
			}
		}
		catch (Exception e)
		{
			throw new ServletException(e);
		}

		// Status
		log.info("status : {0}", response.getStatus());
		servletResponse.setStatus( response.getStatus() );
		
		// Headers
		log.info("sending headers...");
		for (Map.Entry<String, String> entry : response.getHeaders(request).entrySet())
		{
			log.debug("header : {0} : {1}", entry.getKey(), entry.getValue());
			servletResponse.setHeader(entry.getKey(), entry.getValue());
		}
		//if (request.getPath().equals("/admin")) { servletResponse.sendRedirect("/admin/admin/index"); }
		
		// Cookies
		log.info("sending cookies...");
		List<co.bledo.mvc.Cookie> cookies = response.getCookies(request);
		for (co.bledo.mvc.Cookie _c : cookies)
		{
			javax.servlet.http.Cookie c  = new javax.servlet.http.Cookie(_c.getName(), _c.getValue());
			
			c.setHttpOnly(_c.getHttpOnly());
			c.setMaxAge(_c.getMaxAge() );
			c.setSecure( _c.getSecure() );
			
			String tmp = _c.getDomain();
			if (tmp != null && !tmp.isEmpty()) {
				c.setDomain(_c.getDomain());
			}
			
			tmp = _c.getPath();
			if (tmp != null && !tmp.isEmpty()) {
				c.setPath( _c.getPath() );
			}
			
			servletResponse.addCookie(c);
		}
		
		//Output
		log.info("sending body...");
		try {
			response.printBody(request, servletResponse);
		} catch (Exception e) {
			log.error("{0}", e);
			throw new ServletException(e);
		}
	}

	/**
	 * Override this method to pre/post process requests
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	protected Response processRequest(Request request) throws Exception
	{
		String action = request.getAction();

		//
		Response response = null;
		try
		{
			//
			Method method = actionMap.get( action );
			if (method == null) {
				log.warn("page not found : {0}", action );
				throw new HttpError404();
			}
			
			log.info("Invoking {0}", action);
			response = (Response) method.invoke(this, new Object[]{request});
			
		}
		catch (Exception ex)
		{
			log.error("{0}", ex);
			throw new ServletException(ex);
		}
		
		return response;
	}
	
	protected Response processRequestError(Request request, Exception e) throws Exception
	{
		return new co.bledo.mvc.response.Error(request, e);
	}
	
	private void _init()
	{
		if (actionMap != null)
		{
			return;
		}


		actionMap = new HashMap<String, Method>();

		log.info("Initializing Servlet {0}", this);
		for (Method method : this.getClass().getMethods())
		{
			String actionName = method.getName();
			log.debug("\tfound method {0}", actionName);

			// check visibility
			int mod = method.getModifiers();
			if (!Modifier.isPublic(mod)) { continue; }
			if (Modifier.isAbstract(mod)) { continue; }
			if (Modifier.isStatic(mod)) { continue; }
			
			
			//
			// make sure method has a request paramenter type
			@SuppressWarnings("rawtypes")
				Class[] params = method.getParameterTypes();
			if (params.length != 1 ) { continue; }
			if (params[0].isInstance(Request.class)) { continue; }

			
			// make sure method returns a response
			if ( method.getReturnType().isInstance(Response.class) || method.getReturnType().equals(Response.class))
			{
				// Add to method map
				log.info("\tadded action: {0}", actionName);
				actionMap.put(actionName, method);
			}
		}
	}

}
