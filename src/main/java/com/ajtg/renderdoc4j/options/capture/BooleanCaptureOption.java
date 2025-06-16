package com.ajtg.renderdoc4j.options.capture;

import com.ajtg.renderdoc4j.util.EnumIntNativeMapped;

/**
 * The different "boolean" options that RenderDoc supports.
 *
 * <p>As of right now, this excludes the {@code AllowUnsupportedVendorExtensions} option, as it is undocumented <i>for a reason</i> via the official RenderDocAPI Header documentation.</p>
 */
//TEST: PASSING
public enum BooleanCaptureOption implements EnumIntNativeMapped {
    /**
     * Copied from <a href="">RenderDoc</a>:
     *
     * <blockquote>
     * Allow the application to enable vsync
     *
     * <p>Default - enabled
     *
     * <p>1 - The application can enable or disable vsync at will
     *
     * <p>0 - vsync is force disabled
     * </blockquote>
     */
    AllowVSync(0),

    /**
     * Copied from <a href="">RenderDoc</a>:
     *
     * <blockquote>
     * Allow the application to enable fullscreen
     *
     * <p>Default - enabled
     *
     * <p>1 - The application can enable or disable fullscreen at will
     *
     * <p>0 - fullscreen is force disabled
     * </blockquote>
     */
    AllowFullscreen(1),

    /**
     * Copied from <a href="">RenderDoc</a>:
     *
     * <blockquote>
     * Record API debugging events and messages
     *
     * <p>Default - disabled
     *
     * <p>1 - Enable built-in API debugging features and records the results into
     * the capture, which is matched up with events on replay
     *
     * <p>0 - no API debugging is forcibly enabled
     * </blockquote>
     */
    APIValidation(2),

    /**
     * Copied from <a href="">RenderDoc</a>:
     *
     * <blockquote>
     * Capture CPU callstacks for API events
     *
     * <p>Default - disabled
     *
     * <p>1 - Enables capturing of callstacks
     *
     * <p>0 - no callstacks are captured
     * </blockquote>
     *
     */
    CaptureCallstacks(3),

    /**
     * Copied from <a href="">RenderDoc</a>:
     *
     * <blockquote>
     * When capturing CPU callstacks, only capture them from actions.
     * This option does nothing without the above option being enabled
     *
     * <p>Default - disabled
     *
     * <p>1 - Only captures callstacks for actions.
     * Ignored if CaptureCallstacks is disabled
     *
     * <p>0 - Callstacks, if enabled, are captured for every event.
     * </blockquote>
     *
     */
    CaptureCallstacksOnlyActions(4),

    /**
     * Copied from <a href="">RenderDoc</a>:
     *
     * <blockquote>
     * This option now controls the filling of uninitialised buffers with 0xdddddddd which was
     * previously always enabled.
     *
     * <p>Verify buffer access. This includes checking the memory returned by a Map() call to
     * detect any out-of-bounds modification, as well as initialising buffers with undefined contents
     * to a marker value to catch use of uninitialised memory.
     *
     * <p>NOTE: This option is only valid for OpenGL and D3D11. Explicit APIs such as D3D12 and Vulkan do
     * not do the same kind of interception and checking and undefined contents are really undefined.
     *
     * <p>Default - disabled
     *
     * <p>1 - Verify buffer access
     *
     * <p>0 - No verification is performed, and overwriting bounds may cause crashes or corruption in
     * RenderDoc.
     * </blockquote>
     */
    VerifyBufferAccess(6),

    /**
     * Copied from <a href="">RenderDoc</a>:
     *
     * <blockquote>
     * Hooks any system API calls that create child processes, and injects
     * RenderDoc into them recursively with the same options.
     *
     * <p>Default - disabled
     *
     * <p>1 - Hooks into spawned child processes
     *
     * <p>0 - Child processes are not hooked by RenderDoc
     * </blockquote>
     */
    HookIntoChildren(7),

    /**
     * Copied from <a href="">RenderDoc</a>:
     *
     * <blockquote>
     * By default RenderDoc only includes resources in the final capture necessary
     * for that frame, this allows you to override that behaviour.
     *
     * <p>Default - disabled
     *
     * <p>1 - all live resources at the time of capture are included in the capture
     * and available for inspection
     *
     * <p>0 - only the resources referenced by the captured frame are included
     * </blockquote>
     */
    RefAllResources(8),

    /**
     * Copied from <a href="">RenderDoc</a>:
     *
     * <blockquote>
     * <b>NOTE</b>: As of RenderDoc v1.1 this option has been deprecated. Setting or
     * getting it will be ignored, to allow compatibility with older versions.
     * In v1.1 the option acts as if it's always enabled.
     * By default RenderDoc skips saving initial states for resources where the
     * previous contents don't appear to be used, assuming that writes before
     * reads indicate previous contents aren't used.
     *
     * <p>Default - disabled
     *
     * <p>1 - initial contents at the start of each captured frame are saved, even if
     * they are later overwritten or cleared before being used.
     *
     * <p>0 - unless a read is detected, initial contents will not be saved and will
     * appear as black or empty data.
     * </blockquote>
     *
     */
    SaveAllInitials(9),

    /**
     * Copied from <a href="">RenderDoc</a>:
     *
     * <blockquote>
     * In APIs that allow for the recording of command lists to be replayed later,
     * RenderDoc may choose to not capture command lists before a frame capture is
     * triggered, to reduce overheads. This means any command lists recorded once
     * and replayed many times will not be available and may cause a failure to
     * capture.
     *
     * <p>NOTE: This is only true for APIs where multithreading is difficult or
     * discouraged. Newer APIs like Vulkan and D3D12 will ignore this option
     * and always capture all command lists since the API is heavily oriented
     * around it and the overheads have been reduced by API design.
     *
     * <p>1 - All command lists are captured from the start of the application
     *
     * <p>0 - Command lists are only captured if their recording begins during
     * the period when a frame capture is in progress.
     * </blockquote>
     *
     *
     */
    CaptureAllCmdLists(10),

    /**
     * Copied from <a href="">RenderDoc</a>:
     *
     * <blockquote>
     * Mute API debugging output when the API validation mode option is enabled
     * <p>Default - enabled
     * <p>1 - Mute any API debug messages from being displayed or passed through
     * <p>0 - API debugging is displayed as normal
     * </blockquote>
     */
    DebugOutputMute(11);

    private final int optionNum;

    BooleanCaptureOption(int value) {
        this.optionNum = value;
    }

    @Override
    public Object toNative() {
        return this.optionNum;
    }
}
