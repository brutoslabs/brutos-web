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

package org.brandao.brutos.ioc;

import java.beans.PropertyEditor;
import java.util.HashMap;
import java.util.Map;
import org.brandao.brutos.BrutosContext;
import org.brandao.brutos.ioc.editors.*;
import org.brandao.brutos.old.programatic.IOCManager;

/**
 *
 * @author Afonso Brandao
 */
public class EditorConfigurer {

    private static Map editors;

    static{
        editors = new HashMap<Class, PropertyEditor>();
        editors.put( boolean.class.getName(),   new BooleanEditorSupport() );
        editors.put( Boolean.class.getName(),   new BooleanEditorSupport() );
        editors.put( byte.class.getName(),      new ByteEditorSupport() );
        editors.put( Byte.class.getName(),      new ByteEditorSupport() );
        editors.put( Character.class.getName(), new CharacterEditorSupport() );
        editors.put( char.class.getName(),      new CharacterEditorSupport() );
        editors.put( Class.class.getName(),     new ClassEditorSupport() );
        editors.put( Double.class.getName(),    new DoubleEditorSupport() );
        editors.put( double.class.getName(),    new DoubleEditorSupport() );
        editors.put( Float.class.getName(),     new FloatEditorSupport() );
        editors.put( float.class.getName(),     new FloatEditorSupport() );
        editors.put( Integer.class.getName(),   new IntegerEditorSupport() );
        editors.put( int.class.getName(),       new IntegerEditorSupport() );
        editors.put( Long.class.getName(),      new LongEditorSupport() );
        editors.put( long.class.getName(),      new LongEditorSupport() );
        editors.put( Short.class.getName(),     new ShortEditorSupport() );
        editors.put( short.class.getName(),     new ShortEditorSupport() );
        editors.put( String.class.getName(),    new StringEditorSupport() );
    }

    public static void registerPropertyEditor( String name, PropertyEditor editor ){
        editors.put(name, editor);
    }
    
    public static PropertyEditor getPropertyEditor( String name ){
        PropertyEditor editor = (PropertyEditor) editors.get(name);
        /*
        if( editor == null ){
            BrutosCore core = BrutosCore.getCurrentInstance();
            IOCManager iocManager = core.getIocManager();
            if( iocManager.getProvider().containsBeanDefinition("customEditors") ){
                CustomEditorConfigurer custom = (CustomEditorConfigurer)iocManager.getInstance("customEditors");
                if( custom != null )
                    editor = custom.getEditor( clazz );
            }
        }
        */
        return editor;
    }
    
}
