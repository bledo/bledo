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


import co.bledo.rpc.json.ProxyService.MethodDefinition;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;

/**
 * Servlet implementation class TestJsonRpc
 */
@WebServlet("/TestJsonRpc")
public class RpcServlet extends HttpServlet
{
	private static org.apache.logging.log4j.Logger log = org.apache.logging.log4j.LogManager.getLogger(RpcServlet.class);
	
	private static final long serialVersionUID = 1L;
	protected ProxyService proxy;
       
	@Override
    public void init()
    {
		proxy = new ProxyService(this);
    }
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
    	log.info("doGet called");
		StringBuilder sb = new StringBuilder();
		List<MethodDefinition> defList = proxy.getMethodDefinitios();
		sb.append("<html><body>");
		if (defList.size() > 0)
		{
			sb.append("<ul>");
			for (MethodDefinition def : defList)
			{
				sb.append("<li>");
				sb.append(def.toString());
				sb.append("</li>");
			}
			sb.append("</ul>");
		}
		else
		{
			sb.append("<b>No methods found</b>");
		}
		sb.append("<body></html>");
		response.getWriter().print(sb.toString());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
    	log.info("doPost called");
		int sPathLen = req.getContextPath().length();
		String path = req.getRequestURI();
		path = path.substring(sPathLen);
		
		String jsonInput = IOUtils.toString(req.getInputStream());
		RpcResponse rpcResponder = getRpcResponder(path, jsonInput);
		
		resp.setContentType("application/json");
		rpcResponder.respond( resp.getWriter() );
	}
	
	protected RpcResponse getRpcResponder(String path, String jsonInput)
	{
		// request object
		RpcRequest request = null;
		try {
			request = new RpcRequest(jsonInput);
		} catch (RpcException e) {
			log.error("{0}", e);
			return new ErrorResponse(-32700, "Parse error");
		}
		
		// check method
		if (!proxy.methodExists(request.getMethod())) {
			return new ErrorResponse(request.getId(), -32601, "Method not found");
		}
		
		// response
		try {
			Object result = proxy.call(request.getMethod(), request.getParams());
			return new BaseRpcResponse(request.getId(), result);
		} catch (RpcException e) {
			return new ErrorResponse(request.getId(), -32000, "Server error");
		}
	}

}
