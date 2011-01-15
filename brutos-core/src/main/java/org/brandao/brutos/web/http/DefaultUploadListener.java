/*
 * Brutos Web MVC http://brutos.sourceforge.net/
 * Copyright (C) 2009 Afonso Brandao. (afonso.rbn@gmail.com)
 *
 * This library is free software. You can redistribute it
 * and/or modify it under the terms of the GNU General Public
 * License (GPL) version 3.0 or (at your option) any later
 * version.
 * You may obtain a copy of the License at
 *
 * http://www.gnu.org/licenses/gpl.html
 *
 * Distributed WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied.
 *
 */

package org.brandao.brutos.web.http;

/**
 *
 * @author Brandao
 */
public class DefaultUploadListener implements UploadListener{

    private UploadEvent event;
    private long start;

    public DefaultUploadListener( UploadEvent event ){
        this.event = event;
    }

    public UploadEvent getUploadEvent() {
        return this.event;
    }

    public void uploadStarted() {
        this.start = System.currentTimeMillis();
    }

    public void uploadFinished() {
    }

    public UploadStats getUploadStats() {
        return new DefaultUploadStats( this.event, this.start );
    }

}
