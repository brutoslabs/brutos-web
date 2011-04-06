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

package org.brandao.brutos.web;

import java.util.Map;
import org.brandao.brutos.BrutosConstants;
import org.brandao.brutos.ScopeType;
import org.brandao.brutos.scope.Scope;
import org.brandao.brutos.web.http.UploadStats;

/**
 *
 * @author Brandao
 */
public class SessionUploadStats {

    public UploadStats getUploadStats( String requestId ){
        WebApplicationContext context =
                ContextLoader.getCurrentWebApplicationContext();
        Scope scope = context.getScopes().get(ScopeType.SESSION);
        Map mappedUploadStats =
                (Map) scope.get( BrutosConstants.SESSION_UPLOAD_STATS );

        return (UploadStats) mappedUploadStats.get(requestId);
    }
}
