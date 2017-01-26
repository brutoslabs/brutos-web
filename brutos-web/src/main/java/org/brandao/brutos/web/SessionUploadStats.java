

package org.brandao.brutos.web;

import java.util.Map;
import org.brandao.brutos.BrutosConstants;
import org.brandao.brutos.scope.Scope;
import org.brandao.brutos.web.http.UploadStats;


public class SessionUploadStats {

    public UploadStats getUploadStats( String requestId ){
        WebApplicationContext context =
                ContextLoader.getCurrentWebApplicationContext();
        Scope scope = context.getScopes().get(WebScopeType.SESSION);
        Map mappedUploadStats =
                (Map) scope.get( BrutosConstants.SESSION_UPLOAD_STATS );

        return (UploadStats) mappedUploadStats.get(requestId);
    }
}
