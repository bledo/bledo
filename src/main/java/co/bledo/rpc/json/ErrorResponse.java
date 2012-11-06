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


public class ErrorResponse implements RpcResponse
{
	protected String id;
	protected String msg;
	protected int code;
	public ErrorResponse(int code, String msg)
	{
		this.id = null;
		this.code = code;
		this.msg = msg;
	}
	
	public ErrorResponse(String id, int code, String msg)
	{
		this.id = id;
		this.code = code;
		this.msg = msg;
	}
	
	public void respond(PrintWriter writer)
	{
		String lmsg = org.apache.commons.lang3.StringEscapeUtils.escapeEcmaScript(msg);
		String lid = org.apache.commons.lang3.StringEscapeUtils.escapeEcmaScript(id);
		
		String resp;
		if (id == null)
		{
			resp = "{\"jsonrpc\":\"2.0\",\"error\":{\"code\":"+code+",\"message\":\""+ lmsg +"\"}}";
		}
		else
		{
			resp = "{\"jsonrpc\":\"2.0\", \"id\":\""+ lid +"\", \"error\":{\"code\":"+code+",\"message\":\""+ lmsg +"\"}}";
		}
		
		writer.write(resp);
	}
}
