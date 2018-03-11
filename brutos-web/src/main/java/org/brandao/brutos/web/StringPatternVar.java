/*
 * Brutos Web MVC http://www.brutosframework.com.br/
 * Copyright (C) 2009-2017 Afonso Brandao. (afonso.rbn@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.brandao.brutos.web;

import java.util.regex.Pattern;

/**
 * 
 * @author Brandao
 */
public class StringPatternVar {

        private String start;
        
        private String end;
        
        private String id;
        
        private Pattern regex;

        private Pattern regexPrefix;
        
        private Pattern regexSuffix;
        
        private int index;
        
        public StringPatternVar(int index, String id, Pattern regex, 
        		String start, String end, Pattern regexPrefix, Pattern regexSuffix ){
            this.id          = id;
            this.start       = start;
            this.end         = end;
            this.regex       = regex;
            this.index       = index;
            this.regexPrefix = regexPrefix;
            this.regexSuffix = regexSuffix;
        }

        public boolean isEmptyStart(){
        	return start == null || start.isEmpty();
        }
        
		public String getStart() {
			return start;
		}

		public void setStart(String start) {
			this.start = start;
		}

        public boolean isEmptyEnd(){
        	return end == null || end.isEmpty();
        }
        
		public String getEnd() {
			return end;
		}

		public void setEnd(String end) {
			this.end = end;
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public Pattern getRegex() {
			return regex;
		}

		public void setRegex(Pattern regex) {
			this.regex = regex;
		}

		public Pattern getRegexPrefix() {
			return regexPrefix;
		}

		public void setRegexPrefix(Pattern regexPrefix) {
			this.regexPrefix = regexPrefix;
		}

		public Pattern getRegexSuffix() {
			return regexSuffix;
		}

		public void setRegexSuffix(Pattern regexSuffix) {
			this.regexSuffix = regexSuffix;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}


}
