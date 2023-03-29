package com.ajex.temperatureserver.ddl;

import com.sun.jna.Library;
import com.sun.jna.Native;
@Deprecated
public interface RD07Dll extends Library {
    RD07Dll INSTANCE = Native.load("TZONE.RD07.dll", RD07Dll.class);

    String Analysis(byte[] data);


}