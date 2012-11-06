package co.bledo.logger;
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


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class ScreenLoggerWriter implements LoggerWritter
{
	private Level _level = Level.ALL;
	private DateFormat df = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
	
	
	@Override
	public void setLevel(Level level) {
		_level = level;
	}

	@Override
	public void writeLog(Level level, String className, String methodName, String msg) {
		if ( Level.loggable(_level, level) )
		{
			System.out.println("[" + df.format(new Date()) + "] " +  className + "." + methodName + "(" + level.getLabel()+ ") : " + msg);
		}
	}
}


