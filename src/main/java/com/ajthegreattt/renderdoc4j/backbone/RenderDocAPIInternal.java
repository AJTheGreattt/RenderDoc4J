package com.ajthegreattt.renderdoc4j.backbone;

import com.ajthegreattt.renderdoc4j.backbone.RenderDocLibrary.GetCaptureFilePathUnion;
import com.ajthegreattt.renderdoc4j.backbone.RenderDocLibrary.IsTargetControlConnectedUnion;
import com.ajthegreattt.renderdoc4j.backbone.RenderDocLibrary.SetCaptureFilePathUnion;
import com.ajthegreattt.renderdoc4j.backbone.RenderDocLibrary.ShutdownUnion;
import com.ajthegreattt.renderdoc4j.backbone.RenderDocLibrary.pRENDERDOC_DiscardFrameCapture;
import com.ajthegreattt.renderdoc4j.backbone.RenderDocLibrary.pRENDERDOC_EndFrameCapture;
import com.ajthegreattt.renderdoc4j.backbone.RenderDocLibrary.pRENDERDOC_GetAPIVersion;
import com.ajthegreattt.renderdoc4j.backbone.RenderDocLibrary.pRENDERDOC_GetCapture;
import com.ajthegreattt.renderdoc4j.backbone.RenderDocLibrary.pRENDERDOC_GetCaptureOptionF32;
import com.ajthegreattt.renderdoc4j.backbone.RenderDocLibrary.pRENDERDOC_GetCaptureOptionU32;
import com.ajthegreattt.renderdoc4j.backbone.RenderDocLibrary.pRENDERDOC_GetNumCaptures;
import com.ajthegreattt.renderdoc4j.backbone.RenderDocLibrary.pRENDERDOC_GetOverlayBits;
import com.ajthegreattt.renderdoc4j.backbone.RenderDocLibrary.pRENDERDOC_IsFrameCapturing;
import com.ajthegreattt.renderdoc4j.backbone.RenderDocLibrary.pRENDERDOC_LaunchReplayUI;
import com.ajthegreattt.renderdoc4j.backbone.RenderDocLibrary.pRENDERDOC_MaskOverlayBits;
import com.ajthegreattt.renderdoc4j.backbone.RenderDocLibrary.pRENDERDOC_SetActiveWindow;
import com.ajthegreattt.renderdoc4j.backbone.RenderDocLibrary.pRENDERDOC_SetCaptureFileComments;
import com.ajthegreattt.renderdoc4j.backbone.RenderDocLibrary.pRENDERDOC_SetCaptureKeys;
import com.ajthegreattt.renderdoc4j.backbone.RenderDocLibrary.pRENDERDOC_SetCaptureOptionF32;
import com.ajthegreattt.renderdoc4j.backbone.RenderDocLibrary.pRENDERDOC_SetCaptureOptionU32;
import com.ajthegreattt.renderdoc4j.backbone.RenderDocLibrary.pRENDERDOC_SetCaptureTitle;
import com.ajthegreattt.renderdoc4j.backbone.RenderDocLibrary.pRENDERDOC_SetFocusToggleKeys;
import com.ajthegreattt.renderdoc4j.backbone.RenderDocLibrary.pRENDERDOC_ShowReplayUI;
import com.ajthegreattt.renderdoc4j.backbone.RenderDocLibrary.pRENDERDOC_StartFrameCapture;
import com.ajthegreattt.renderdoc4j.backbone.RenderDocLibrary.pRENDERDOC_TriggerCapture;
import com.ajthegreattt.renderdoc4j.backbone.RenderDocLibrary.pRENDERDOC_TriggerMultiFrameCapture;
import com.ajthegreattt.renderdoc4j.backbone.RenderDocLibrary.pRENDERDOC_UnloadCrashHandler;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;

/**
 * The internal implementation of the RenderDocAPI {@code struct} that stores all {@code function *} for the underlying RenderDoc API.
 *
 * <p>This class is <b>internal</b>, and will not be exposed at this point in time.</p>
 *
 * <p>All functions in this class can be reached through the external implementation: {@link RenderDocAPI}.</p>
 * @see RenderDocAPI
 * @see <a href="https://renderdoc.org/docs/in_application_api.html">Official RenderDoc Documenation</a> for more information on these pointers...
 *
 **/
@Structure.FieldOrder(value = {"GetAPIVersion",
        "SetCaptureOptionU32",
        "SetCaptureOptionF32",
        "GetCaptureOptionU32",
        "GetCaptureOptionF32",
        "SetFocusToggleKeys",
        "SetCaptureKeys",
        "GetOverlayBits",
        "MaskOverlayBits",
        "ShutdownUnion",
        "UnloadCrashHandler",
        "SetCaptureFilePathUnion",
        "GetCaptureFilePathUnion",
        "GetNumCaptures",
        "GetCapture",
        "TriggerCapture",
        "IsTargetControlConnectedUnion",
        "LaunchReplayUI",
        "SetActiveWindow",
        "StartFrameCapture",
        "IsFrameCapturing",
        "EndFrameCapture",
        "TriggerMultiFrameCapture",
        "SetCaptureFileComments",
        "DiscardFrameCapture",
        "ShowReplayUI",
        "SetCaptureTitle"})
public class RenderDocAPIInternal extends Structure {

    public pRENDERDOC_GetAPIVersion GetAPIVersion;

    public pRENDERDOC_SetCaptureOptionU32 SetCaptureOptionU32;
    public pRENDERDOC_SetCaptureOptionF32 SetCaptureOptionF32;

    public pRENDERDOC_GetCaptureOptionU32 GetCaptureOptionU32;
    public pRENDERDOC_GetCaptureOptionF32 GetCaptureOptionF32;

    public pRENDERDOC_SetFocusToggleKeys SetFocusToggleKeys;
    public pRENDERDOC_SetCaptureKeys SetCaptureKeys;

    public pRENDERDOC_GetOverlayBits GetOverlayBits;
    public pRENDERDOC_MaskOverlayBits MaskOverlayBits;

    public ShutdownUnion ShutdownUnion;

    public pRENDERDOC_UnloadCrashHandler UnloadCrashHandler;

    public SetCaptureFilePathUnion SetCaptureFilePathUnion;
    public GetCaptureFilePathUnion GetCaptureFilePathUnion;

    public pRENDERDOC_GetNumCaptures GetNumCaptures;

    public pRENDERDOC_GetCapture GetCapture;

    public pRENDERDOC_TriggerCapture TriggerCapture;

    public IsTargetControlConnectedUnion IsTargetControlConnectedUnion;

    public pRENDERDOC_LaunchReplayUI LaunchReplayUI;

    public pRENDERDOC_SetActiveWindow SetActiveWindow;

    public pRENDERDOC_StartFrameCapture StartFrameCapture;
    public pRENDERDOC_IsFrameCapturing IsFrameCapturing;
    public pRENDERDOC_EndFrameCapture EndFrameCapture;

    public pRENDERDOC_TriggerMultiFrameCapture TriggerMultiFrameCapture;

    public pRENDERDOC_SetCaptureFileComments SetCaptureFileComments;
    public pRENDERDOC_DiscardFrameCapture DiscardFrameCapture;

    public pRENDERDOC_ShowReplayUI ShowReplayUI;

    public pRENDERDOC_SetCaptureTitle SetCaptureTitle;

    RenderDocAPIInternal(Pointer pointer) {
        super(pointer);
        read();
    }
}

