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
public interface UploadStats {

    /**
     * Obtém a porcentagem de bytes já carregados.
     *
     * @return Porcentagem de bytes carregados.
     */
    public double getPercentComplete();

    /**
     * Obtém a estimativa de tempo restante em milisegundos
     * para terminar o upload.
     *
     * @return Estimatica de tempo.
     */
    public long estimatedMillisecondsLeft();

    /**
     * Obtém o tempo decorrido em milisegundos do upload.
     *
     * @return Tempo decorrido em milisegundos
     */
    public long getElapsedTimeInMilliseconds();

}
