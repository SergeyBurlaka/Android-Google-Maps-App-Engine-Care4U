package com.quest.uni2b.appengine.gsm.demo.usertouserdemo2svn020316.manager.map.circle;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Circle;

/**
 * Created by Operator on 22.07.2016.
 */
/*
*    Strategy pattern.
*    In Strategy pattern, a class behavior or its algorithm can be changed at run time.
* */
public interface CSetStrategy {
      public Circle setCircle (GoogleMap map);
}
