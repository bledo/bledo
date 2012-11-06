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


import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RpcRequest
{
	protected String method;
	protected String id;
	protected List<Object> params = new ArrayList<Object>();
	
	public RpcRequest(String jsonStr) throws RpcException
	{
		JSONObject json= null;
		try {
			// request object
			json = new JSONObject(jsonStr);
			
			method = json.getString("method");
			id = json.getString("id");
			JSONArray _params = json.getJSONArray("params");
			int len = _params.length();
			for (int i = 0; i < len; i++)
			{
				params.add(_params.get(i));
			}
		} catch (JSONException e) {
			throw new RpcException(e);
		}
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<Object> getParams() {
		return params;
	}

	public void setParams(List<Object> params) {
		this.params = params;
	}
}
