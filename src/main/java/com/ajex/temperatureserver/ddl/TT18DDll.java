package com.ajex.temperatureserver.ddl;

import com.sun.jna.Library;
import com.sun.jna.Native;

@Deprecated
public interface TT18DDll extends Library {
    TT18DDll INSTANCE = Native.load("TZONE.TT18D.dll", TT18DDll.class);

    String Analysis(byte[] data);


}