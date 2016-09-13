package com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.manager.map.toolbar;


import com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.manager.map.UserMapsActivity;

/**
 * Created by Operator on 21.07.2016.
 */

/*
  Factory pattern
*
* */

public class ToolbarFactoryMethod {

    private UserMapsActivity mapContext;
    public ToolbarFactoryMethod(UserMapsActivity context) {
        this.mapContext = context;
    }


    public Toolbar4Map getToolbar (Object object) {

        Toolbar4Map toolbarFace = null;


     if (object instanceof ToolbarStart) {

            toolbarFace = new ToolbarStart(mapContext);

        } else if (object instanceof ToolbarGetLocationFail) {

            toolbarFace = new ToolbarGetLocationFail(mapContext);

        } else if (object instanceof ToolbarGetLocationWin) {

        toolbarFace = new ToolbarGetLocationWin(mapContext);

         }else if (object instanceof ToolbarShowTips) {

        toolbarFace = new ToolbarShowTips(mapContext);

        }
        return toolbarFace;
    }

}
