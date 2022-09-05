package JavaCLStuff;


import java.util.Map;

import JavaCLStuff.BridJ;
import JavaCLStuff.Pointer;
import JavaCLStuff.StructObject;
import JavaCLStuff.JavaCLRuntime;

@JavaCLRuntime(CPPRuntime.class)
public abstract class CPPObject extends StructObject {

    Map<Class<?>, Object[]> templateParameters;

    protected CPPObject() {
    }

    protected CPPObject(Pointer<? extends CPPObject> peer, Object... targs) {
        super(peer, targs);
        //templateParameters = (Map)Collections.singletonMap(getClass(), targs);
    }

    protected CPPObject(Void voidArg, int constructorId, Object... args) {
        super(voidArg, constructorId, args);
    }

    @Override
    protected void finalize() throws Throwable {
      BridJ.setJavaObjectFromNativePeer(peer.getPeer(), null);
    }
}
