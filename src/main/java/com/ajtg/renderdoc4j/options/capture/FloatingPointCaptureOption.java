package com.ajtg.renderdoc4j.options.capture;

import com.ajtg.renderdoc4j.util.EnumIntNativeMapped;

/**
* The different "floating-point" options that RenderDoc supports.
 *
 * <p>This excludes the AllowUnsupportedVendorExtensions option, as it is undocumented <i>for a reason</i> via the official RenderDocAPI Header documentation.</p>
* */
//TEST: PASSING
public enum FloatingPointCaptureOption implements EnumIntNativeMapped {

    /**
     * Specify a delay in seconds to wait for a debugger to attach, after
     * creating or injecting into a process, before continuing to allow it to run.
     *
     * <p>0 indicates no delay, and the process will run immediately after injection
     *
     * <p>Default - 0 seconds
     */
    DelayForDebugger(5),

    /**
     * Define a soft memory limit which some APIs may aim to keep overhead under where
     * possible.
     *
     * <p>Anything above this limit will where possible be saved directly to disk during
     * capture.
     *
     * <p>This will cause increased disk space use (which may cause a capture to fail if disk space is
     * exhausted) as well as slower capture times.
     * Not all memory allocations may be deferred like this so it is not a guarantee of a memory
     * limit.
     *
     * <p>Units are in MBs, suggested values would range from 200MB to 1000MB.
     *
     * <p>Default - 0 Megabytes
     */
    SoftMemoryLimit(13);

    private final int optionNum;

    FloatingPointCaptureOption(int optionNum) {
        this.optionNum = optionNum;
    }

    @Override
    public Object toNative() {
        return this.optionNum;
    }
}
