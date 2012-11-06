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


/**
 *
 * @author Ricardo Ramirez <ricardo@bledo.co>
 */
public class HttpError500 extends HttpError {

	private static final long serialVersionUID = -329530235057396792L;

	public HttpError500(Throwable cause) {
		super(cause);
	}

	public HttpError500(String message, Throwable cause) {
		super(message, cause);
	}

	public HttpError500(String message) {
		super(message);
	}

	public HttpError500() {
		super();
	}
	
}
