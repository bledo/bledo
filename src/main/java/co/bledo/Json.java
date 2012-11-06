package co.bledo;
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
import java.util.Map;

/**
 *
 * @author Ricardo Ramirez <ricardo@bledo.co>
 */
public class Json extends HashMap<String, Object>
{

	public Json() {
	}

	public Json(Map<? extends String, ? extends Object> m) {
		super(m);
	}

	public Json(int initialCapacity) {
		super(initialCapacity);
	}

	public Json(int initialCapacity, float loadFactor) {
		super(initialCapacity, loadFactor);
	}

	public String getString(String key)
	{
		return (String) get(key);
	}

}
