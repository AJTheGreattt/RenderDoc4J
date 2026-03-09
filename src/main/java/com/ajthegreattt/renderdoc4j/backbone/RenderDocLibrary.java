package com.ajthegreattt.renderdoc4j.backbone;

import com.ajthegreattt.renderdoc4j.annotations.OpenGLIdentifiers;
import com.ajthegreattt.renderdoc4j.options.RenderDocInputButton;
import com.sun.jna.Callback;
import com.sun.jna.Library;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.sun.jna.Union;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.LongByReference;
import com.sun.jna.ptr.PointerByReference;
import com.ajthegreattt.renderdoc4j.options.capture.BooleanCaptureOption;
import com.ajthegreattt.renderdoc4j.options.capture.FloatingPointCaptureOption;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.nio.ByteBuffer;

public interface RenderDocLibrary extends Library {

    // RDOC - 1.6.0 & RDOC4J - 1.0.0

    int RENDERDOC_GetAPI(RenderDocAPIVersion version, PointerByReference outAPIPointers);

    interface pRENDERDOC_GetAPIVersion extends Callback {
        void invoke(IntByReference major, IntByReference minor, IntByReference patch);
    }

    interface pRENDERDOC_SetCaptureOptionU32 extends Callback {
        int invoke(BooleanCaptureOption opt, int val);
    }

    interface pRENDERDOC_SetCaptureOptionF32 extends Callback {
        int invoke(FloatingPointCaptureOption opt, float val);
    }

    interface pRENDERDOC_GetCaptureOptionU32 extends Callback {
        int invoke(BooleanCaptureOption value);
    }

    interface pRENDERDOC_GetCaptureOptionF32 extends Callback {
        float invoke(FloatingPointCaptureOption value);
    }

    interface pRENDERDOC_SetFocusToggleKeys extends Callback {
        void invoke(RenderDocInputButton[] keys, int num);
    }

    interface pRENDERDOC_SetCaptureKeys extends Callback {
        void invoke(RenderDocInputButton[] keys, int num);
    }

    interface pRENDERDOC_GetOverlayBits extends Callback {
        int invoke();
    }

    interface pRENDERDOC_MaskOverlayBits extends Callback {
        void invoke(int and, int or);
    }

    class ShutdownUnion extends Union {
        public pRENDERDOC_RemoveHooks RemoveHooks;

        public ShutdownUnion() {
            super();
        }

        public interface pRENDERDOC_RemoveHooks extends Callback {
            void invoke();
        }
    }

    interface pRENDERDOC_UnloadCrashHandler extends Callback {
        void invoke();
    }

    class SetCaptureFilePathUnion extends Union {

        public pRENDERDOC_SetCaptureFilePathTemplate SetCaptureFilePathTemplate;

        public SetCaptureFilePathUnion() {
            super();
        }

        public interface pRENDERDOC_SetCaptureFilePathTemplate extends Callback {
            void invoke(String pathTemplate);
        }
    }

    class GetCaptureFilePathUnion extends Union {

        public pRENDERDOC_GetCaptureFilePathTemplate GetCaptureFilePathTemplate;

        public GetCaptureFilePathUnion() {
            super();
        }

        public interface pRENDERDOC_GetCaptureFilePathTemplate extends Callback {
            String invoke();
        }
    }

    interface pRENDERDOC_GetNumCaptures extends Callback {
        int invoke();
    }

    interface pRENDERDOC_GetCapture extends Callback {
        int invoke(int idx,
                   @Nullable ByteBuffer fileName,
                   @Nullable IntByReference pathLength,
                   @Nullable LongByReference timestamp);
    }

    interface pRENDERDOC_TriggerCapture extends Callback {
        void invoke();
    }

    class IsTargetControlConnectedUnion extends Union {

        public pRENDERDOC_IsTargetControlConnected IsTargetControlConnected;

        public IsTargetControlConnectedUnion() {
            super();
        }

        public interface pRENDERDOC_IsTargetControlConnected extends Callback {
            int invoke();
        }
    }

    interface pRENDERDOC_LaunchReplayUI extends Callback {
        int invoke(int connectTargetControl, String cmdline);
    }

    class RENDERDOC_DevicePointer extends Pointer {
        public RENDERDOC_DevicePointer(long peer) {
            super(peer);
        }
    }

    class RENDERDOC_WindowHandle extends Pointer {
        public RENDERDOC_WindowHandle(long peer) {
            super(peer);
        }
    }

    interface pRENDERDOC_SetActiveWindow extends Callback {
        void invoke(@NotNull RENDERDOC_DevicePointer device, @NotNull RENDERDOC_WindowHandle wndHandle);
    }

    interface pRENDERDOC_StartFrameCapture extends Callback {
        void invoke(@Nullable RENDERDOC_DevicePointer device, @Nullable RENDERDOC_WindowHandle wndHandle);
    }

    interface pRENDERDOC_IsFrameCapturing extends Callback {
        int invoke();
    }

    interface pRENDERDOC_EndFrameCapture extends Callback {
        int invoke(@Nullable RENDERDOC_DevicePointer device, @Nullable RENDERDOC_WindowHandle wndHandle);
    }

    interface pRENDERDOC_TriggerMultiFrameCapture extends Callback {
        void invoke(int numFrames);
    }

    interface pRENDERDOC_SetCaptureFileComments extends Callback {
        void invoke(@Nullable String filePath, String comments);
    }

    interface pRENDERDOC_DiscardFrameCapture extends Callback {
        int invoke(@Nullable RENDERDOC_DevicePointer device, @Nullable RENDERDOC_WindowHandle wndHandle);
    }

    interface pRENDERDOC_ShowReplayUI extends Callback {
        int invoke();
    }

    interface pRENDERDOC_SetCaptureTitle extends Callback {
        void invoke(@NotNull String title);
    }

    // RDOC - 1.7.0 & RDOC4J - 2.0.0

    // We purposefully use bytes instead of Java booleans for proper C/C++ boolean handling.

    class RENDERDOC_AnnotationVectorValue extends Union {
        public   byte[] bools = new byte[4];
        public    int[] int32s = new int[4];
        public   long[] int64s = new long[4];
        public    int[] uint32s = new int[4];
        public   long[] uint64s = new long[4];
        public  float[] float32s = new float[4];
        public double[] float64s = new double[4];

        public RENDERDOC_AnnotationVectorValue() {
            super();
        }

        public static class ByValue extends RENDERDOC_AnnotationVectorValue implements com.sun.jna.Structure.ByValue { }
    }

    class RENDERDOC_AnnotationValue extends Union {
        public byte bool;
        public int int32;
        public long int64;
        public int uint32;
        public long uint64;
        public float float32;
        public double float64;

        public RENDERDOC_AnnotationVectorValue.ByValue vector;

        public String string;
        public Pointer apiObject;

        public RENDERDOC_AnnotationValue() {
            super();
        }

        public static class ByReference extends RENDERDOC_AnnotationValue implements com.sun.jna.Structure.ByReference { }
    }

    @Structure.FieldOrder({"identifier", "name"})
    class RENDERDOC_GLResourceReference extends Structure {
        /**
         * From the <a href="https://github.com/baldurk/renderdoc/blob/v1.x/renderdoc/api/app/renderdoc_app.h">Official RenderDoc Header File</a>:
         * <blockquote>
         *     {@code //this is the same GLenum identifier as passed to glObjectLabel}
         * </blockquote>
         * */
        public int identifier;
        public int name;

        public RENDERDOC_GLResourceReference(OpenGLIdentifiers identifier, int name) {
            super();
            this.identifier = identifier.value;
            this.name = name;
            write();
        }
    }

    interface pRENDERDOC_SetObjectAnnotation extends Callback {
        int invoke(@Nullable RENDERDOC_DevicePointer device,
                   @Nullable Pointer object,
                   String key,
                   int valueType,
                   int valueVectorWidth,
                   @Nullable RENDERDOC_AnnotationValue.ByReference value);
    }

    interface pRENDERDOC_SetCommandAnnotation extends Callback {
        int invoke(@Nullable RENDERDOC_DevicePointer device,
                   @Nullable Pointer queueOrCommandBuffer,
                   String key,
                   int valueType,
                   int valueVectorWidth,
                   @Nullable RENDERDOC_AnnotationValue.ByReference value);
    }
}
