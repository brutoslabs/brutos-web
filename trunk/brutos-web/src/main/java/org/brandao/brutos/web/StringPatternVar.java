package org.brandao.brutos.web;

import java.util.regex.Pattern;

public class StringPatternVar {

        private String start;
        
        private String end;
        
        private String id;
        
        private Pattern regex;

        private Pattern regexPrefix;
        
        private Pattern regexSuffix;
        
        private int index;
        
        public StringPatternVar( int index, String id, Pattern regex, 
        		String start, String end, Pattern regexPrefix, Pattern regexSuffix ){
            this.id          = id;
            this.start       = start;
            this.end         = end;
            this.regex       = regex;
            this.index       = index;
            this.regexPrefix = regexPrefix;
            this.regexSuffix = regexSuffix;
        }

		public String getStart() {
			return start;
		}

		public void setStart(String start) {
			this.start = start;
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
