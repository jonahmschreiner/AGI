package JavaCLStuff;

class NativeConstants {

    /**
     * BridJ constants
     */
    enum ValueType {

        eVoidValue,
        eWCharValue,
        eCLongValue,
        eCLongObjectValue,
        eSizeTValue,
        eSizeTObjectValue,
        eIntValue,
        eShortValue,
        eByteValue,
        eBooleanValue,
        eLongValue,
        eDoubleValue,
        eFloatValue,
        ePointerValue,
        eEllipsis,
        eIntFlagSet,
        eNativeObjectValue,
        eTimeTObjectValue
    }

    /**
     * BridJ constants
     */
    enum CallbackType {

        eJavaCallbackToNativeFunction,
        eNativeToJavaCallback,
        eJavaToNativeFunction,
        eJavaToVirtualMethod
    }
}
