

package org.brandao.brutos.web;


public class URIParameter {

        private String start;
        private String end;
        private String id;
        private String regex;
        private int index;
        
        public URIParameter( int index, String id, String regex, String start, String end ){
            this.id    = id;
            this.start = start;
            this.end   = end;
            this.regex = regex;
            this.index = index;
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

    public String getRegex() {
        return regex;
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

}
