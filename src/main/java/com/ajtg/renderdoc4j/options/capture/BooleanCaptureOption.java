package com.ajtg.renderdoc4j.options.capture;

import com.ajtg.renderdoc4j.util.EnumIntNativeMapped;

/**
 * The different "boolean" options that RenderDoc supports.
 *
 * <p>As of right now, this excludes the {@code AllowUnsupportedVendorExtensions} option, as it is undocumented <i>for a reason</i> via the official RenderDoc API Header documentation.</p>
 *
 */
//TEST: PASSING
public enum BooleanCaptureOption implements EnumIntNativeMapped {

    /**
     * From the <a href="https://github.com/baldurk/renderdoc/blob/v1.x/renderdoc/api/app/renderdoc_app.h">Official RenderDoc Header File</a>:
     *
     * <blockquote>
     * Allow the application to enable vsync
     *
     * <p>Default - enabled
     *
     * <p>{@code true} The application can enable or disable vsync at will
     *
     * <p>{@code false} vsync is force disabled
     * </blockquote>
     */
    AllowVSync(0),

    /**
     * From the <a href="https://github.com/baldurk/renderdoc/blob/v1.x/renderdoc/api/app/renderdoc_app.h">Official RenderDoc Header File</a>:
     *
     * <blockquote>
     * Allow the application to enable fullscreen
     *
     * <p>Default - enabled
     *
     * <p>{@code true} The application can enable or disable fullscreen at will
     *
     * <p>{@code false} fullscreen is force disabled
     * </blockquote>
     */
    AllowFullscreen(1),

    /**
     * From the <a href="https://github.com/baldurk/renderdoc/blob/v1.x/renderdoc/api/app/renderdoc_app.h">Official RenderDoc Header File</a>:
     *
     * <blockquote>
     * Record API debugging events and messages
     *
     * <p>Default - disabled
     *
     * <p>{@code true} Enable built-in API debugging features and records the results into
     * the capture, which is matched up with events on replay
     *
     * <p>{@code false} no API debugging is forcibly enabled
     * </blockquote>
     */
    APIValidation(2),

    /**
     * From the <a href="https://github.com/baldurk/renderdoc/blob/v1.x/renderdoc/api/app/renderdoc_app.h">Official RenderDoc Header File</a>:
     *
     * <blockquote>
     * Capture CPU callstacks for API events
     *
     * <p>Default - disabled
     *
     * <p>{@code true} Enables capturing of callstacks
     *
     * <p>{@code false} no callstacks are captured
     * </blockquote>
     *
     */
    CaptureCallstacks(3),

    /**
     * From the <a href="https://github.com/baldurk/renderdoc/blob/v1.x/renderdoc/api/app/renderdoc_app.h">Official RenderDoc Header File</a>:
     *
     * <blockquote>
     * When capturing CPU callstacks, only capture them from actions.
     * This option does nothing without the above option being enabled
     *
     * <p>Default - disabled
     *
     * <p>{@code true} Only captures callstacks for actions.
     * Ignored if CaptureCallstacks is disabled
     *
     * <p>{@code false} Callstacks, if enabled, are captured for every event.
     * </blockquote>
     *
     */
    CaptureCallstacksOnlyActions(4),

    /**
     * From the <a href="https://github.com/baldurk/renderdoc/blob/v1.x/renderdoc/api/app/renderdoc_app.h">Official RenderDoc Header File</a>:
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
     * <p>{@code true} Verify buffer access
     *
     * <p>{@code false} No verification is performed, and overwriting bounds may cause crashes or corruption in
     * RenderDoc.
     * </blockquote>
     */
    VerifyBufferAccess(6),

    /**
     * From the <a href="https://github.com/baldurk/renderdoc/blob/v1.x/renderdoc/api/app/renderdoc_app.h">Official RenderDoc Header File</a>:
     *
     * <blockquote>
     * Hooks any system API calls that create child processes, and injects
     * RenderDoc into them recursively with the same options.
     *
     * <p>Default - disabled
     *
     * <p>{@code true} Hooks into spawned child processes
     *
     * <p>{@code false} Child processes are not hooked by RenderDoc
     * </blockquote>
     */
    HookIntoChildren(7),

    /**
     * From the <a href="https://github.com/baldurk/renderdoc/blob/v1.x/renderdoc/api/app/renderdoc_app.h">Official RenderDoc Header File</a>:
     *
     * <blockquote>
     * By default RenderDoc only includes resources in the final capture necessary
     * for that frame, this allows you to override that behaviour.
     *
     * <p>Default - disabled
     *
     * <p>{@code true} all live resources at the time of capture are included in the capture
     * and available for inspection
     *
     * <p>{@code false} only the resources referenced by the captured frame are included
     * </blockquote>
     */
    RefAllResources(8),

    /**
     * From the <a href="https://github.com/baldurk/renderdoc/blob/v1.x/renderdoc/api/app/renderdoc_app.h">Official RenderDoc Header File</a>:
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
     * <p>{@code true} initial contents at the start of each captured frame are saved, even if
     * they are later overwritten or cleared before being used.
     *
     * <p>{@code false} unless a read is detected, initial contents will not be saved and will
     * appear as black or empty data.
     * </blockquote>
     *
     */
    @Deprecated(forRemoval = true)
    SaveAllInitials(9),

    /**
     * From the <a href="https://github.com/baldurk/renderdoc/blob/v1.x/renderdoc/api/app/renderdoc_app.h">Official RenderDoc Header File</a>:
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
     * <p>{@code true} All command lists are captured from the start of the application
     *
     * <p>{@code false} Command lists are only captured if their recording begins during
     * the period when a frame capture is in progress.
     * </blockquote>
     *
     *
     */
    CaptureAllCmdLists(10),

    /**
     * From the <a href="https://github.com/baldurk/renderdoc/blob/v1.x/renderdoc/api/app/renderdoc_app.h">Official RenderDoc Header File</a>:
     *
     * <blockquote>
     * Mute API debugging output when the API validation mode option is enabled
     * <p>Default - enabled
     * <p>{@code true} Mute any API debug messages from being displayed or passed through
     * <p>{@code false} API debugging is displayed as normal
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
