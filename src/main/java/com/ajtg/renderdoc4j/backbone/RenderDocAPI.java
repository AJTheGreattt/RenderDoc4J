package com.ajtg.renderdoc4j.backbone;

import com.ajtg.renderdoc4j.util.CaptureListener;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.LongByReference;
import com.sun.jna.ptr.PointerByReference;
import com.ajtg.renderdoc4j.options.RenderDocInputButton;
import com.ajtg.renderdoc4j.options.capture.BooleanCaptureOption;
import com.ajtg.renderdoc4j.options.capture.FloatingPointCaptureOption;
import com.ajtg.renderdoc4j.options.overlay.OverlaySettingBits;
import com.ajtg.renderdoc4j.options.overlay.RenderDocOverlayBit;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

/**
 * <h1>RenderDoc API Wrapper for Java (RDOC4J)</h1>
 *
 * <h2>Getting Started</h2>
 *
 * <p>If you wanna' just get right to it, call {@link RenderDocAPI#getInstance()} before any Graphics API calls, and (as long as your program's window is up...) you will see that RenderDoc is injected into your program.
 *
 * <h3>Lazy Initialization (for those who need all the details...)</h3>
 *
 * <p>This library uses a lazy initialization approach
 * to allow for direct control of when the RenderDocAPI will be injected into the application.</p>
 *
 * <p>As soon as the {@link RenderDocAPI#INSTANCE Instance} of the library is built—whether it indirectly from {@link RenderDocAPI#getInstance()}, or from a {@link Builder} instance's {@link Builder#build() build()} call—it will be injected into your program.</p>
 *
 * <p>It is important that you initialize the {@link RenderDocAPI#INSTANCE Instance} <b>before</b> any Graphics API calls, just as it is somewhat required for RenderDoc.</p>
 *
 * <p>Initializing the {@link RenderDocAPI#INSTANCE Instance} after Graphics API calls have already been made has undefined results.</p>
 *
 * <h3>Obtaining an Instance</h3>
 *
 * <p>There are multiple ways to create and obtain the {@link RenderDocAPI#INSTANCE Instance}:</p>
 *
 * <ul>
 *     <li>{@link RenderDocAPI#getInstance()}
 *     <ul>
 *          <li> Calling this method will check if the {@link RenderDocAPI#INSTANCE Instance} is {@code null}, and if it is, will create a new default-setting {@link RenderDocAPI#INSTANCE Instance}.
 *     </ul>
 *
 *     <li>{@link RenderDocAPI#builder()}
 *     <ul>
 *          <li> This method will return a {@link Builder} instance which you can customize as you see fit. Consult the Javadocs for the {@link Builder} class for more information...
 *     </ul>
 *
 *     <li>{@link RenderDocAPI#builder(RenderDocAPIVersion)} & {@link RenderDocAPI#getOrBuildDefaultInstance(RenderDocAPIVersion)}
 *     <ul>
 *          <li> These two methods allow for you to request a specific {@link RenderDocAPIVersion}. Please see the <i>'API Versioning'</i> section below for more information...
 *     </ul>
 * </ul>
 *
 * <h2>API Versioning</h2>
 *
 * <p><b>Warning:</b> The {@link RenderDocAPIVersion} you request may not be available depending on the version of the RenderDoc shared-library file (you may specify).</p>
 *
 * <p>Specifying a {@link RenderDocAPIVersion} and/or shared-library file for RenderDoc is not a requirement to use this library.</p>
 *
 * <p>From the <a href="https://renderdoc.org/docs/in_application_api.html">Official RenderDoc Documentation</a>:</p>
 *
 * <blockquote>
 *     Make sure to use a matching API header for your build - if you use a newer header, the API version may not be available. All RenderDoc builds supporting this API ship the header in their root directory.
 * </blockquote>
 *
 * <p>As long as RenderDoc licensing allows: this library will come packaged with the latest RenderDoc .DLL (for Windows) by default. If you are on another platform, you can still use this API as long as the following is true:
 *  <ul>
 *      <li>RenderDoc can run on your platform</li>
 *      <li>You can provide the absolute path of the RenderDoc shared-library file to <a href="https://github.com/java-native-access/jna">JNA</a></li>
 *      <li><a href="https://github.com/java-native-access/jna">JNA</a> can load the shared-library file for RenderDoc on your platform</li>
 *  </ul>
 *
 * <p><i>See also {@link Builder#withAbsoluteSharedLibraryPath(Path)} and {@link Builder#withSharedLibraryResource(String)} for information on how to provide your own shared-library file...</i></p>
 *
 * <h2>Window Handles and Device Pointers</h2>
 *
 * <p>You have the option to supply a platform-specific Window Handle and/or Graphics API-specific Device Pointer.</p>
 *
 * <p>From the <a href="https://renderdoc.org/docs/in_application_api.html#:~:text=RENDERDOC_DevicePointer%20is%20a,Android%20ANativeWindow*.">Official RenderDoc Documentation</a>:</p>
 *
 * <blockquote>
 *  {@code RENDERDOC_DevicePointer} is a typedef to {@code void*}. The contents of it are API specific:
 *  <p>For D3D11 it must be the {@code ID3D11Device} device object.
 *  <p>For D3D12 it must be the {@code ID3D12Device} device object.
 *  <p>For OpenGL it must be the {@code HGLRC}, {@code GLXContext}, or {@code EGLContext} context object.
 *  <p>For OpenGLES it must be the {@code EGLContext} context object.
 *  <p>For Vulkan it must be the dispatch table pointer within the {@code VkInstance}. This is a pointer-sized value at the location pointed to by the {@code VkInstance}.
 *  <p>NOTE - this is not the actual {@code VkInstance} pointer itself. You can use the {@code RENDERDOC_DEVICEPOINTER_FROM_VKINSTANCE} helper macro defined in the renderdoc header to obtain this pointer from any {@code VkInstance}.
 *  <p>
 *  {@code RENDERDOC_WindowHandle} is a typedef to {@code void *}. It is the platform specific Windows {@code HWND}, Xcb {@code xcb_window_t}, Xlib {@code Window} / {@code Drawable}, Wayland {@code wl_surface*}, or Android {@code ANativeWindow*}.
 * </blockquote>
 *
 * <h2>Obtaining Window Handles and Device Pointers</h2>
 *
 * <p>My original use case for RenderDoc was capturing frames in a game I was building through LWJGL and thus OpenGL and GLFW.
 * I have listed some ways below that could be useful to you for providing these {@code long} values to this API.</p>
 *
 * <p><i>Where {@code glfwWindow} is defined as {@code long glfwWindow = glfwCreateWindow(...)}</i></p>
 * <blockquote>
 *     <p>Windows</p>
 *     <ul>
 *         <li>Your Window Handle: {@code GLFWNativeWin32.glfwGetWin32Window(glfwWindow)}</li>
 *         <li>Your API Device Pointer: {@code GLFWNativeWGL.glfwGetWGLContext(glfwWindow)}</li>
 *     </ul>
 *     <p>Linux</p>
 *     <ul>
 *         <li>Your Window Handle: {@code GLFWNativeX11.glfwGetX11Window(glfwWindow)}</li>
 *         <li>Your API Device Pointer: {@code GLFWNativeGLX.glfwGetGLXContext(glfwWindow)}</li>
 *     </ul>
 *     <i>Mac is omitted since RenderDoc does not operate on macOS at this time...</i>
 * </blockquote>
 *
 * <p>If you are using GLFW as well, see <a href="https://www.glfw.org/docs/latest/group__native.html">here</a> for more information.</p>
 *
 * <p>Since these objects are platform and Graphics API-specific, I can only suggest to see the documentation for the Graphics API (or Wrapper for the API) you are using.</p>
 *
 *
 */


//TODO: Documentation
//TODO: Remove unnecessary final/this keywords
public final class RenderDocAPI {

    static final Supplier<IllegalStateException> INSTANCE_BUILT_ALREADY = () -> new IllegalStateException("The Instance for the RenderDocAPI has already been built");

    static RenderDocAPI INSTANCE;

    private final RenderDocAPIInternal internal;

    private final RenderDocAPIVersion version;

    private final OverlaySettingBits overlaySettingBits;

    private final AtomicReference<RenderDocLibrary.RENDERDOC_DevicePointer> devicePointer = new AtomicReference<>(null);

    private final AtomicReference<RenderDocLibrary.RENDERDOC_WindowHandle> windowHandle = new AtomicReference<>(null);

    private final ArrayList<CaptureListener> captureListeners;

    private final ArrayDeque<Runnable> frameQueue = new ArrayDeque<>();

    private int maxFileNameLength;

    private int captureCount = 0;

    RenderDocAPI(@NotNull Builder builder) {

        RenderDocLibrary lib = builder.getLib();

        PointerByReference outAPIPointers = new PointerByReference();

        if (lib.RENDERDOC_GetAPI(builder.version, outAPIPointers) == 0) {
            throw new RuntimeException("Could not establish the API for RenderDoc for some reason");
        }

        Pointer struct = outAPIPointers.getValue();

        if (struct == null) {
            throw new RuntimeException("Error: The API Pointers struct has not been populated");
        }

        this.internal = new RenderDocAPIInternal(struct);

        IntByReference major = new IntByReference();
        IntByReference minor = new IntByReference();
        IntByReference patch = new IntByReference();

        in().GetAPIVersion.invoke(major, minor, patch);

        this.version = RenderDocAPIVersion.fromInt(Integer.parseInt("" + major.getValue() + minor.getValue() + patch.getValue()));

        this.maxFileNameLength = builder.maxFilePathLength;

        var overlayBits = builder.overlayBits;

        this.overlaySettingBits = new OverlaySettingBits(in(), overlayBits.isEmpty() ? EnumSet.of(RenderDocOverlayBit.DEFAULT) : overlayBits);

        if (builder.windowHandle != 0) {
            this.windowHandle.set(new RenderDocLibrary.RENDERDOC_WindowHandle(builder.windowHandle));
        }

        if (builder.apiDeviceHandle != 0) {
            this.devicePointer.set(new RenderDocLibrary.RENDERDOC_DevicePointer(builder.apiDeviceHandle));
        }

        builder.tasks.forEach(consumer -> consumer.accept(this));

        this.captureListeners = builder.captureListeners.isEmpty() ? new ArrayList<>(0) : builder.captureListeners;
    }

    private RenderDocAPIInternal in() {
        return this.internal;
    }

    /**
     * This method returns a new {@link Builder} that defaults to the latest {@link RenderDocAPIVersion}.
     *
     * @return A new {@link Builder} that defaults to the latest {@link RenderDocAPIVersion}
     * @throws IllegalStateException If the {@link RenderDocAPI#INSTANCE Instance} has already been initialized
     * @see RenderDocAPI#builder(RenderDocAPIVersion)
     * @see RenderDocAPI#getOrBuildDefaultInstance(RenderDocAPIVersion)
     * @see RenderDocAPI#getInstance()
     */
    //TEST: PASSING
    public static Builder builder() {

        if (INSTANCE != null) {
            throw INSTANCE_BUILT_ALREADY.get();
        }

        return new Builder(RenderDocAPIVersion.latest());
    }

    /**
     * Returns a new {@link RenderDocAPI#INSTANCE Instance} if it has not been created yet, or the {@link RenderDocAPI#INSTANCE Instance} that has been created already.
     *
     * @return A lazily-initialized {@link RenderDocAPI} with the default settings.
     */
    public static RenderDocAPI getInstance() {

        if (INSTANCE == null) {
            return builder(RenderDocAPIVersion.latest()).build();
        }

        return INSTANCE;
    }

    /**
     * This is a builder-instantiation method for creating the {@link RenderDocAPI RenderDocAPI} instance.
     *
     * <p><b>Warning: </b>The {@link RenderDocAPIVersion API Version} you request may NOT be available dependent upon the version of the RenderDoc shared-library file you specify.</p>
     *
     * <p>This library comes packaged with the latest RenderDoc Windows shared-library Files (.DLL) by default. If you are on another platform, you can still use this API as long as the following is true:
     * <ul>
     *     <li>RenderDoc can run on your platform</li>
     *     <li>You can provide the absolute path of the shared-library file to <a href="https://github.com/java-native-access/jna">JNA</a></li>
     *     <li><a href="https://github.com/java-native-access/jna">JNA</a> can load the shared-library file for RenderDoc on your platform</li>
     * </ul>
     *
     * <p><i>See also {@link Builder#withAbsoluteSharedLibraryPath(Path)} and {@link Builder#withSharedLibraryResource(String)} for information on how to provide your own shared-library file...</i></p>
     *
     * @param version The version of the API that you may or may not be able to retrieve access to
     * @return A builder that when built, will be the only usable instance of the RenderDocAPI. You can but are not required to save a reference to the built {@link RenderDocAPI}, as it can be obtained with the other get singleton methods.
     * @see Builder#withAbsoluteSharedLibraryPath(Path)
     * @see Builder#withSharedLibraryResource(String)
     */
    //TEST: PASSING
    public static Builder builder(RenderDocAPIVersion version) {

        if (INSTANCE != null) {
            throw INSTANCE_BUILT_ALREADY.get();
        }

        return new Builder(version);
    }

    /**
     * This method does one of the following:
     * <ol>
     *     <li>If the {@link RenderDocAPI#INSTANCE Instance} has not been initialized, it builds a default instance based on the {@link RenderDocAPIVersion} you request, and sets the {@link RenderDocAPI#INSTANCE Instance} equal to the newly built instance.</li>
     *     <li>If the {@link RenderDocAPI#INSTANCE Instance} has been initialized, it returns this instance.</li>
     * </ol>
     *
     * @param version The version of the API that you would like to request.
     * @return The {@link RenderDocAPI#INSTANCE Instance}
     */
    //TEST: PASSING
    public static RenderDocAPI getOrBuildDefaultInstance(RenderDocAPIVersion version) {

        if (INSTANCE == null) {
            builder(version).build();
        }

        return INSTANCE;
    }

    public void setMaxFileNameLength(int maxFileNameLength) {
        this.maxFileNameLength = maxFileNameLength;
    }

    /**
     * <b><i>Warning: This is an optional operation. If you do not know what you are doing, you most likely do not need to use it.</i></b>
     *
     * <p>Supplies a platform-specific <a href="https://renderdoc.org/docs/in_application_api.html#_CPPv415SetActiveWindow23RENDERDOC_DevicePointer22RENDERDOC_WindowHandle">Window Handle</a> to the underlying RenderDocAPI.
     *
     * @param windowHandle the platform-specific window handle
     * @return this instance of the {@link RenderDocAPI}
     */
    //TEST: PASSING
    public RenderDocAPI supplyWindowHandle(long windowHandle) {
        this.windowHandle.set(new RenderDocLibrary.RENDERDOC_WindowHandle(windowHandle));
        return this;
    }

    /**
     * <b><i>Warning: This is an optional operation. If you do not know what you are doing, you most likely do not need to use it.</i></b>
     *
     * <p>Supplies a Graphics API-specific <a href="https://renderdoc.org/docs/in_application_api.html#_CPPv415SetActiveWindow23RENDERDOC_DevicePointer22RENDERDOC_WindowHandle">API Device Pointer</a> to the underlying RenderDocAPI.
     *
     * @param apiDevicePointer The Graphics API-specific device pointer
     * @return this instance of the {@link RenderDocAPI}
     */
    //TEST: PASSING
    public RenderDocAPI supplyAPIDevicePointer(long apiDevicePointer) {
        this.devicePointer.set(new RenderDocLibrary.RENDERDOC_DevicePointer(apiDevicePointer));
        return this;
    }

    /**
     * Sets a new value for the {@link BooleanCaptureOption}. This change is in effect as of the next capture.
     *
     * @param value         The new value you would like to set for the  {@link BooleanCaptureOption}
     * @param captureOption The option you would like to attempt to change
     * @return true if and only if the option is valid and was set successfully
     */
    //TEST: PASSING
    public boolean setBooleanCaptureOption(boolean value, BooleanCaptureOption captureOption) {
        return in().SetCaptureOptionU32.invoke(captureOption, value ? 1 : 0) == 1;
    }

    /**
     * Sets a new value for the {@link FloatingPointCaptureOption}. This change is in effect as of the next capture.
     *
     * @param value         The new value you would like to set for the {@link FloatingPointCaptureOption}
     * @param captureOption The option you would like to attempt to change
     * @return true if and only if the option is valid and was set successfully
     */
    //TEST: PASSING
    public boolean setFloatCaptureOption(float value, FloatingPointCaptureOption captureOption) {
        return in().SetCaptureOptionF32.invoke(captureOption, value) == 1;
    }

    /**
     * Sets multiple {@link BooleanCaptureOption}s to the given {@code value}. This change is in effect as of the next capture.
     *
     * @param value The new value you would like to set for all the {@link BooleanCaptureOption}
     * @param array The array of options you would like to attempt to change
     * @return true if and only if <b>all</b> options are valid and set successfully
     */
    //TEST: PASSING
    public boolean setBooleanCaptureOptions(boolean value, BooleanCaptureOption... array) {
        byte i = 1;

        for (BooleanCaptureOption option : array) {
            if (in().SetCaptureOptionU32.invoke(option, value ? 1 : 0) != 1) {
                i = 0;
            }
        }

        return i == 1;
    }

    /**
     * Sets multiple {@link FloatingPointCaptureOption}s to the given {@code value}. This change is in effect as of the next capture.
     *
     * @param value The new value you would like to set for all the {@link FloatingPointCaptureOption}
     * @param array The array of options you would like to attempt to change
     * @return {@code true} if and only if <b>all</b> options are valid and set successfully
     */
    //TEST: PASSING
    public boolean setFloatCaptureOptions(float value, FloatingPointCaptureOption... array) {

        byte i = 1;

        for (FloatingPointCaptureOption option : array) {
            if (in().SetCaptureOptionF32.invoke(option, value) != 1) {
                i = 0;
            }
        }

        return i == 1;
    }

    /**
     * If you have registered {@link CaptureListener}s, this method <b>must</b> be called.
     *
     * <p>This library has no internal time, as the underlying RenderDoc API does not expose any.</p>
     *
     * <p>When a capture is {@link RenderDocAPI#triggerCapture() triggered}, it does not capture the current frame, but the frame after. Calling this update method periodically allows for the API to keep up with capture count changes and update listeners as necessary.</p>
     *
     * <p>When determining where to call this method for your listeners, consider how quickly you want your {@link CaptureListener}s to receive their updates.</p>
     *
     * @see CaptureListener
     * @see RenderDocAPI#addCaptureListener(CaptureListener)
     */
    //TEST:PASSING
    public void updateCaptureListeners() {
        final int captureCount = getNumCaptures();

        if (captureCount != this.captureCount) {
            this.captureCount = captureCount;
            while (!this.frameQueue.isEmpty()) {
                this.frameQueue.pop().run();
            }
        }
    }

    /**
     * From the <a href="https://renderdoc.org/docs/in_application_api.html#_CPPv414GetNumCapturesv">Official RenderDoc Documentation</a>:
     *
     * <blockquote>
     * This function returns the number of frame captures that have been made.
     * </blockquote>
     *
     * @return The number of frame captures that have been made
     */
    //TEST: PASSING
    public int getNumCaptures() {
        return in().GetNumCaptures.invoke();
    }

    /**
     * Returns the value of the given {@link BooleanCaptureOption}.
     *
     * @param captureOption The {@link BooleanCaptureOption} you would like to know the value of
     * @return the currently set value of the {@link BooleanCaptureOption}
     */
    //TEST: PASSING
    public boolean getBooleanCaptureOption(BooleanCaptureOption captureOption) {
        return in().GetCaptureOptionU32.invoke(captureOption) == 1;
    }

    /**
     * Returns the value of the given {@link FloatingPointCaptureOption}.
     *
     * @param captureOption The {@link FloatingPointCaptureOption} you would like to know the value of
     * @return the currently set value of the {@link FloatingPointCaptureOption}
     */
    //TEST: PASSING
    public float getFloatCaptureOption(FloatingPointCaptureOption captureOption) {
        return in().GetCaptureOptionF32.invoke(captureOption);
    }

    /**
     * This method returns the {@link RenderDocAPIVersion} being used for the API currently.
     *
     * <p>This value is determined during {@link RenderDocAPI#INSTANCE Instance} creation, and does not invoke the underlying {@code GetAPIVersion} function.</p>
     *
     * @return The {@link RenderDocAPIVersion} in use
     */
    //TEST: PASSING
    public RenderDocAPIVersion getAPIVersion() {
        return this.version;
    }

    /**
     * This method calls {@link RenderDocAPI#setFocusToggleKeys} with {@code null}.
     *
     * @see RenderDocAPI#setFocusToggleKeys(RenderDocInputButton[] buttons)
     */
    //TEST: PASSING
    public void disableFocusToggleKeys() {
        this.setFocusToggleKeys(null);
    }

    /**
     * This method sets the keys that will be used for focus-toggling. You may pass in {@code null} if you would like to disable focus-toggling keys.
     *
     * @param buttons An array of buttons you would like to use for focus-toggling, or {@code null} if you would not like to use any
     * @see RenderDocAPI#disableFocusToggleKeys()
     */
    //TEST: PASSING
    public void setFocusToggleKeys(@Nullable RenderDocInputButton[] buttons) {
        in().SetFocusToggleKeys.invoke(buttons, buttons == null ? 0 : buttons.length);
    }

    /**
     * This method calls {@link RenderDocAPI#setCaptureKeys} with {@code null}.
     *
     * @see RenderDocAPI#setCaptureKeys(RenderDocInputButton[] buttons)
     */
    //TEST: PASSING
    public void disableCaptureKeys() {
        this.setCaptureKeys(null);
    }

    /**
     * This method sets the keys that will be used for capturing. You may pass in {@code null} if you would like to disable capturing keys.
     *
     * @param buttons An array of buttons you would like to use for capturing, or {@code null} if you would not like to use any
     * @see RenderDocAPI#disableCaptureKeys()
     */
    //TEST: PASSING
    public void setCaptureKeys(@Nullable RenderDocInputButton[] buttons) {
        in().SetCaptureKeys.invoke(buttons, buttons == null ? 0 : buttons.length);
    }

    /**
     * From the <a href="https://renderdoc.org/docs/in_application_api.html#_CPPv418UnloadCrashHandlerv">Official RenderDoc Documentation</a>:
     *
     * <blockquote>
     * This function will remove RenderDoc’s crash handler from the target process.
     * If you have your own crash handler that you want to handle any exceptions,
     * RenderDoc’s handler could interfere so it can be disabled.
     * </blockquote>
     */
    //TEST: PASSING
    public void unloadCrashHandler() {
        in().UnloadCrashHandler.invoke();
    }

    /**
     * This method returns the current path in which capture files are being saved.
     *
     * @return The current path in which capture files are being saved
     */
    //TEST: PASSING
    public String getCaptureFilePath() {
        return in().GetCaptureFilePathUnion.GetCaptureFilePathTemplate.invoke();
    }

    /**
     * From the <a href="https://renderdoc.org/docs/in_application_api.html#_CPPv426SetCaptureFilePathTemplatePKc">Official RenderDoc Documentation</a>
     *
     * <blockquote>
     * Set the template for new captures. The template can either be a relative or absolute path,
     * which determines where captures will be saved and how they will be named.
     *
     * <p>If the path template is {@code my_captures/example} then captures saved will be e.g. {@code my_captures/example_frame123.rdc} and {@code my_captures/example_frame456.rdc}.
     *
     * <p>Relative paths will be saved relative to the process’s current working directory.
     *
     * <p>The default template is in a folder controlled by the UI - initially the system temporary folder, and the filename is the executable’s filename.</p>
     *
     * </blockquote>
     *
     * @param filePath The new file path template that you would like to set
     */
    //TEST: PASSING
    public void setCaptureFilePath(String filePath) {
        in().SetCaptureFilePathUnion.SetCaptureFilePathTemplate.invoke(filePath);
    }

    /**
     * This method returns the current {@link OverlaySettingBits}.
     *
     * @return The current {@link OverlaySettingBits}
     * @see OverlaySettingBits
     */
    //TEST: PASSING
    public OverlaySettingBits getOverlaySettingBits() {
        return this.overlaySettingBits;
    }

    /**
     * Triggers a capture, and if there are any {@link CaptureListener}s, they are queued to be executed if the {@link FrameCapture} is successfully processed.
     *
     * @see CaptureListener
     * @see Builder#addCaptureListener(CaptureListener)
     * @see RenderDocAPI#updateCaptureListeners()
     */
    //TEST: PASSING
    public void triggerCapture() {
        in().TriggerCapture.invoke();
    }

    /**
     * Attempts to gather the information for a capture at a given index.
     *
     * @param index The index of the capture you would like to try and find
     * @return An {@link Optional} that may contain a {@link FrameCapture} if the underlying RenderDoc API finds a capture at that index
     * @apiNote A new {@link FrameCapture} object is created every time this method is called unless, of course, the capture is not found. It is up to you to cache it if you would like.
     */
    //TEST: PASSING
    public Optional<FrameCapture> getCapture(int index) {

        ByteBuffer fileNameBuffer = ByteBuffer.allocate(this.maxFileNameLength);

        IntByReference pathLength = new IntByReference(this.maxFileNameLength);
        LongByReference timeStamp = new LongByReference();

        final int result = in().GetCapture.invoke(index, fileNameBuffer, pathLength, timeStamp);

        if (result == 0) {
            return Optional.empty();
        } else {
            final int realPathLength = pathLength.getValue();

            byte[] fileNameArr = new byte[realPathLength];

            fileNameBuffer.get(fileNameArr, 0, realPathLength);

            return Optional.of(new FrameCapture(index, new String(fileNameArr, StandardCharsets.UTF_8), timeStamp.getValue()));
        }
    }

    /**
     * From the <a href="https://renderdoc.org/docs/in_application_api.html#_CPPv424IsTargetControlConnectedv">Official RenderDoc Documentation</a>:
     *
     * <blockquote>
     * This function returns a value to indicate whether the RenderDoc UI is currently connected to the current process.
     * </blockquote>
     *
     * @return {@code true} if the RenderDoc UI is currently connected, or {@code false} otherwise.
     */
    //TEST: PASSING
    public boolean isTargetControlConnected() {
        return in().IsTargetControlConnectedUnion.IsTargetControlConnected.invoke() == 1;
    }

    /**
     * From the <a href="https://renderdoc.org/docs/in_application_api.html#_CPPv414LaunchReplayUI8uint32_tPKc">Official RenderDoc Documentation</a>:
     *
     * <blockquote>
     * <p>This function will determine the closest matching replay UI executable for the current RenderDoc module and launch it.</p>
     * </blockquote>
     * @param connectTargetControl should be set to {@code true} if the UI should immediately connect to the application.
     * @param cmdline              is an optional UTF-8 null-terminated string to be appended to the command line, e.g. a capture filename. If this parameter is {@code null}, the command line will be unmodified.
     * @return If the UI was successfully launched, this function will return the PID of the new process. Otherwise it will return 0.
     */
    //TEST: PASSING
    public int launchReplayUI(boolean connectTargetControl, String cmdline) {
        return in().LaunchReplayUI.invoke(connectTargetControl ? 1 : 0, cmdline);
    }

    /**
     * From the <a href="https://renderdoc.org/docs/in_application_api.html#_CPPv411RemoveHooksv">Official RenderDoc Documentation</a>:
     * <blockquote>
     * This function will attempt to remove RenderDoc and its hooks from the target process. It must be called as early as possible in the process, and will have undefined results if any graphics API functions have been called.
     *
     * <p> Note: This process is only possible on Windows, and even then it is not well defined so may not be possible in all circumstances. This function is provided at your own risk.
     * This function was renamed, in earlier versions of the API it was declared as Shutdown.
     *
     * <p>This rename is backwards compatible as the function signature did not change.
     * </blockquote>
     */
    //TEST: UNDEFINED
    public void removeHooks() {
        in().ShutdownUnion.RemoveHooks.invoke();
    }

    /**
     * Adds a capture listener that will listen.
     *
     * <p>You <b>must</b> call {@link RenderDocAPI#updateCaptureListeners()} periodically in order for these listener(s) to be updated.</p>
     *
     * @param listener The {@link CaptureListener} that you would like to add to the list of listeners.
     * @see RenderDocAPI#updateCaptureListeners()
     * @apiNote At this point, there is no way to remove a {@link CaptureListener}. This will be implemented in the future.
     */
    //TODO: TEST
    public void addCaptureListener(CaptureListener listener) {
        this.captureListeners.add(listener);
    }

    /**
     * <b><i>Warning: This is an optional operation. If you do not know what you are doing, you most likely do not need to use it.</i></b>
     *
     * <p>From the <a href="https://renderdoc.org/docs/in_application_api.html#_CPPv417StartFrameCapture23RENDERDOC_DevicePointer22RENDERDOC_WindowHandle">Official RenderDoc Documentation</a>:
     * <blockquote>
     *     This function will immediately begin a capture for the specified device/window combination.
     * </blockquote>
     *
     * @param devicePointer is a handle to the API ‘device’ object that will be set active. May be {@code null} to wildcard match.
     *
     * @param windowHandle is a handle to the platform window handle that will be set active. May be {@code null} to wildcard match.
     *
     * @see RenderDocAPI#startFrameCapture()
     */
    //TEST: PASSING
    public void startFrameCapture(long devicePointer, long windowHandle) {
        supplyPointers(devicePointer, windowHandle);
        startFrameCapture();
    }

    /**
     * Invokes {@link RenderDocAPI#startFrameCapture(long, long)} on the current set {@link RenderDocAPI#supplyAPIDevicePointer(long)  devicePointer} and {@link RenderDocAPI#supplyWindowHandle(long) windowHandle}.
     *
     * <p>If you have not set them, then the underlying API will wildcard match as specified by {@link RenderDocAPI#startFrameCapture(long, long)}.</p>
     *
     * <p>You are not required to set the pointers for this method to operate correctly. The underlying RenderDoc API works just fine without them.</p>
     *
     * @see RenderDocAPI#startFrameCapture(long, long)
     */
    public void startFrameCapture() {
        in().StartFrameCapture.invoke(this.devicePointer.get(), this.windowHandle.get());
    }

    /**
     * <b><i>Warning: This is an optional operation. If you do not know what you are doing, you most likely do not need to use it.</i></b>
     *
     * <p>From the <a href="https://renderdoc.org/docs/in_application_api.html#_CPPv415SetActiveWindow23RENDERDOC_DevicePointer22RENDERDOC_WindowHandle">Official RenderDoc Documentation</a>:
     *
     * <blockquote>
     * This function will explicitly set which window is considered active. The active window is the one that will be captured when the keybind to trigger a capture is pressed.
     * </blockquote>
     * @param devicePointer is a handle to the API ‘device’ object that will be set active. Must be valid.
     * @param windowHandle  is a handle to the platform window handle that will be set active. Must be valid.
     *
     * @see "The RenderDocAPI Javadoc section, <i>'Obtaining Window Handles and Device Pointers'</i> for more information..."
     */
    //TEST: PASSING
    public void setActiveWindow(long devicePointer, long windowHandle) {
        supplyPointers(devicePointer, windowHandle);

        in().SetActiveWindow.invoke(this.devicePointer.get(), this.windowHandle.get());
    }

    /**
     * <b><i>Warning: This is an optional operation. If you do not know what you are doing, you most likely do not need to use it.</i></b>
     *
     * @param devicePointer is a handle to the API ‘device’ object that will be set active. Must be valid.
     * @param windowHandle  is a handle to the platform window handle that will be set active. Must be valid.
     *
     * @see RenderDocAPI#supplyAPIDevicePointer(long)
     * @see RenderDocAPI#supplyWindowHandle(long)
     *
     * @see "The RenderDocAPI Javadoc section, <i>'Obtaining Window Handles and Device Pointers'</i> for more information..."
     */
    //TEST: PASSING
    public void supplyPointers(long devicePointer, long windowHandle) {
        this.devicePointer.set(new RenderDocLibrary.RENDERDOC_DevicePointer(devicePointer));
        this.windowHandle.set(new RenderDocLibrary.RENDERDOC_WindowHandle(windowHandle));
    }

    /**
     * From the <a href="https://renderdoc.org/docs/in_application_api.html#_CPPv416IsFrameCapturingv">Official RenderDoc Documentation</a>:
     * <blockquote>
     *     This function returns a value to indicate whether the current frame is capturing.
     * </blockquote>
     *
     * @return {@code true} if the frame is currently capturing, or {@code false} otherwise.
     */
    //TEST: PASSING
    public boolean isFrameCapturing() {
        return in().IsFrameCapturing.invoke() == 1;
    }

    /**
     * <b><i>Warning: This is an optional operation. If you do not know what you are doing, you most likely do not need to use it.</i></b>
     *
     * <p>From the <a href="https://renderdoc.org/docs/in_application_api.html#_CPPv415EndFrameCapture23RENDERDOC_DevicePointer22RENDERDOC_WindowHandle">Official RenderDoc Documentation</a>:
     *
     * <blockquote>
     *     This function will immediately end an active capture for the specified device/window combination.
     * </blockquote>
     *
     * @param devicePointer is a handle to the API ‘device’ object that will be set active. May be {@code null} to wildcard match.
     *
     * @param windowHandle is a handle to the platform window handle that will be set active. May be {@code null} to wildcard match.
     *
     * @return {@code true} if the capture succeeded, and {@code false} if there was an error capturing.
     *
     * @see "The RenderDocAPI Javadoc section, <i>'Obtaining Window Handles and Device Pointers'</i> for more information..."
     */
    //TEST: PASSING
    public boolean endFrameCapture(long devicePointer, long windowHandle) {
        supplyPointers(devicePointer, windowHandle);
        return endFrameCapture();
    }

    public boolean endFrameCapture() {
        return in().EndFrameCapture.invoke(this.devicePointer.get(), this.windowHandle.get()) == 1;
    }

    /**
     * From the <a href="https://renderdoc.org/docs/in_application_api.html#_CPPv424TriggerMultiFrameCapture8uint32_t">Official RenderDoc Documentation</a>:
     *
     * <blockquote>
     * <p>This function will trigger multiple sequential frame captures as if the user had pressed one of the capture hotkeys before each frame. The captures will be taken from the next frames presented to whichever window is considered current.
     *
     * <p>Each capture will be taken independently and saved to a separate file, with no reference to the other frames.
     * </blockquote>
     *
     * @param numFrames the number of frames to capture, as an unsigned integer.
     */
    //TEST: PASSING
    public void triggerMultiFrameCapture(int numFrames) {

        if (numFrames < 1) {
            throw new IllegalArgumentException("There can not be less than 1 frame captured");
        }

        in().TriggerMultiFrameCapture.invoke(numFrames);
    }

    /**
     * From the <a href="https://renderdoc.org/docs/in_application_api.html#_CPPv422SetCaptureFileCommentsPKcPKc">Official RenderDoc Documentation</a>:
     * <blockquote>
     * This function adds an arbitrary comments field to an existing capture on disk, which will then be displayed in the UI to anyone opening the capture.
     * </blockquote>
     * @param filePath The file path to the capture file you would like to adjust the comments for, or {@code null} if you would like to apply the comments to the most recent capture
     * @param comments The comments you would like to write to the capture file
     */
    //TODO: TEST
    public void setCaptureFileComments(@Nullable String filePath, String comments) {
        in().SetCaptureFileComments.invoke(filePath, comments);
    }

    /**
     * <b><i>Warning: This is an optional operation. If you do not know what you are doing, you most likely do not need to use it.</i></b>
     *
     * <p>From the <a href="https://renderdoc.org/docs/in_application_api.html#_CPPv419DiscardFrameCapture23RENDERDOC_DevicePointer22RENDERDOC_WindowHandle">Official RenderDoc Documentation</a>:
     *
     * <blockquote>
     * This function is similar to {@link #endFrameCapture(long, long)} but the capture contents will be discarded immediately, and not processed and written to disk.
     * This will be more efficient than {@link #endFrameCapture(long, long)} if the frame capture is not needed.
     * </blockquote>
     * @param devicePointer is a handle to the API ‘device’ object that will be set active. May be {@code null} to wildcard match.
     * @param windowHandle  is a handle to the platform window handle that will be set active. May be {@code null} to wildcard match.
     * @return {@code true} if the capture was discarded, and {@code false} if there was an error or no capture was in progress.
     * @see "The RenderDocAPI Javadoc section, <i>'Obtaining Window Handles and Device Pointers'</i> for more information..."
     */
    //TEST: PASSING
    public boolean discardFrameCapture(long devicePointer, long windowHandle) {
        supplyPointers(devicePointer, windowHandle);
        return discardFrameCapture();
    }

    public boolean discardFrameCapture() {
        return in().DiscardFrameCapture.invoke(this.devicePointer.get(), this.windowHandle.get()) == 1;
    }

    /**
     * From the <a href="https://renderdoc.org/docs/in_application_api.html#_CPPv412ShowReplayUIv">Official RenderDoc Documentation</a>:
     *
     * <blockquote>
     * <p>This function requests that the currently connected replay UI raise its window to the top. This is only possible if an instance of the replay UI is currently connected, otherwise this function does nothing.
     * <p>This can be used in conjunction with {@link #isTargetControlConnected()} and {@link #launchReplayUI(boolean, String)} to intelligently handle showing the UI after making a capture.
     * <p>Given OS differences it is not guaranteed that the UI will be successfully raised even if the request is passed on. On some OSs it may only be highlighted or otherwise indicated to the user.
     * </blockquote>
     *
     * @return If the request to be shown was passed onto the UI successfully this function will return {@code true}. If there is no UI connected currently or some other error occurred it will return {@code false}.
     */
    //TEST: PASSING
    public boolean showReplayUI() {
        return in().ShowReplayUI.invoke() == 1;
    }

    /**
     * From the <a href="https://renderdoc.org/docs/in_application_api.html#_CPPv415SetCaptureTitlePKc">Official RenderDoc Documentation</a>:
     * <blockquote>
     * This function sets a given title for the currently in-progress capture, which will be displayed in the UI.
     * This can be used either with a user-defined capture using a manual start and end, or an automatic capture triggered by TriggerCapture() or a keypress.
     * <p>
     * If multiple captures are ongoing at once, the title will be applied to the first capture to end only.
     * Any subsequent captures will not get any title unless the function is called again.
     * <p>
     * This function can only be called while a capture is in-progress, after {@code StartFrameCapture()} and before {@code EndFrameCapture()}.
     * If it is called elsewhere it will have no effect. If it is called multiple times within a capture, only the last title will have any effect.
     * </blockquote>
     *
     * @param title The title you would like to set
     */
    //TEST: PASSING
    public void setCaptureTitle(String title) {
        in().SetCaptureTitle.invoke(title);
    }

}
