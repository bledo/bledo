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


import java.io.PrintWriter;

import org.json.JSONException;
import org.json.JSONObject;

public class BaseRpcResponse implements RpcResponse
{
	protected String id;
	protected Object result;
	
	public BaseRpcResponse(String id, Object result)
	{
		this.id = id;
		this.result = result;
	}
	
	public void respond(PrintWriter writer)
	{
		// create response
		try {
			JSONObject json = new JSONObject();
			json.put("jsonrpc", "2.0");
			json.put("id", id);
			json.put("result", result);
			
			writer.write( json.toString()  );
				
		} catch (JSONException e) {
			(new ErrorResponse(id, -32603, "Server error")).respond(writer);
		}
		
	}
}
