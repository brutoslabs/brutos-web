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

import java.io.Serializable;
import java.util.EventListener;

/**
 *
 * @author Brandao
 */
public interface UploadListener extends EventListener,Serializable{

    /**
     * Obtém o evendo de upload associado.
     *
     * @return Evento de upload.
     */
    public UploadEvent getUploadEvent();

    /**
     * Método chamado no inicio do processamento do upload.
     */
    public void uploadStarted();

    /**
     * Método chamado no fim do processamento do upload.
     */
    public void uploadFinished();

    public UploadStats getUploadStats();
    
}
