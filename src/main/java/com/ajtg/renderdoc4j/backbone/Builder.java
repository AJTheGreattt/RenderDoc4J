package com.ajtg.renderdoc4j.backbone;

import com.ajtg.renderdoc4j.options.overlay.RenderDocOverlayBit;
import com.ajtg.renderdoc4j.util.CaptureListener;
import com.sun.jna.Native;
import org.jetbrains.annotations.NotNull;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * The builder for the RenderDocAPI instance. A {@link Builder} can only {@link Builder#build} once per program execution. If a {@link Builder#build} call occurs again, an {@link IllegalStateException} will be thrown.
 *
 * @see RenderDocAPI#builder()
 */
public class Builder {

    private static final Supplier<IllegalArgumentException> NO_PATH = () -> new IllegalArgumentException("The Path passed in for the specified RenderDoc .DLL does not exist," +
            " or cannot be seen by this JVM instance");


    final ArrayList<CaptureListener> captureListeners = new ArrayList<>(3);

    final RenderDocAPIVersion version;

    final EnumSet<RenderDocOverlayBit> overlayBits = EnumSet.noneOf(RenderDocOverlayBit.class);

    final ArrayList<Consumer<RenderDocAPI>> tasks = new ArrayList<>();

    long windowHandle;

    long apiDeviceHandle;

    int maxFilePathLength = 1024;

    private String sharedLibraryName = "renderdoc";

    Builder(RenderDocAPIVersion version) {
        this.version = version;
    }

    /**
     * When the underlying API is initialized, all {@link RenderDocOverlayBit}s within this set will be enabled.
     *
     * <p><b>Warning: If you do not enable </b>{@link RenderDocOverlayBit#ENABLED}<b>, the overlay will not be enabled at all.</b></p>
     *
     * @param bits The {@link EnumSet} of {@link RenderDocOverlayBit}s that you would like to be enabled
     * @return this {@link Builder Builder}
     * @see EnumSet#of(Enum e...)
     * @see EnumSet#range(Enum, Enum)
     */

    public Builder withOverlayBits(@NotNull EnumSet<RenderDocOverlayBit> bits) {

        Objects.requireNonNull(bits);

        if (!this.overlayBits.addAll(bits)) {
            throw new IllegalArgumentException("Some of the RenderDocOverlayBits are already present in the set. Please ensure that a bit is only added once, or this method is only called once");
        }

        return this;
    }

    /**
     * Specifies an absolute path to your RenderDoc shared-library file using Java's {@link Path} interface. This is useful for making sure that the API Version you request is available, since the .DLL that comes with this library will always be the latest version of RenderDoc.
     *
     * @param absolutePath The absolute path to the shared-library as specified by the JNA {@link com.sun.jna.NativeLibrary NativeLibrary} and determined by {@link Path#isAbsolute()}
     * @return this {@link Builder Builder}
     */
    //TODO: Test & Adjust
    public Builder withAbsoluteSharedLibraryPath(@NotNull Path absolutePath) {
        Objects.requireNonNull(absolutePath);

        if (!Files.exists(absolutePath)) {
            throw NO_PATH.get();
        }

        if (!absolutePath.isAbsolute()) {
            throw new IllegalArgumentException("The Path provided is not absolute\nPath: " + absolutePath);
        }

        if (absolutePath.toFile().isFile()) {
            //Just in case, we call absolutePath again
            //ideally, this should be separated into our own if/else
            absolutePath = Objects.requireNonNullElse(absolutePath.getParent(), absolutePath).toAbsolutePath();
        }

        this.sharedLibraryName = absolutePath.toString();

        return this;
    }

    /**
     * Specifies the name and (optionally) the directory to where your RenderDoc shared-library file is stored in your programs {@code resources} file. The {@link ClassLoader#getSystemClassLoader() System Class Loader} is used to search for this resource file.
     *
     * @param resourceFileName The file name of the resource in this program's resources folder, including its file extension that needs to be loaded. The rules of the JNA {@link com.sun.jna.NativeLibrary NativeLibrary} must be followed.
     * @return this {@link Builder Builder}
     * @throws NullPointerException If the resource cannot be found
     * @see Builder#withAbsoluteSharedLibraryPath(Path)
     */
    public Builder withSharedLibraryResource(String resourceFileName) {
        final URL resource = Objects.requireNonNull(ClassLoader.getSystemClassLoader()
                .getResource(resourceFileName), () -> ("Could not find the shared-library [%s] for RenderDoc. " +
                "Please be advised that the resource name must include the extension of the file, *if* the file has an extension").formatted(resourceFileName));

        try {
            this.sharedLibraryName = Path.of(resource.toURI()).toAbsolutePath().toString();
        } catch (URISyntaxException e) {
            throw new RuntimeException("Could not convert the resource's URL to a URI to provide it to JNA", e);
        }


        return this;
    }

    /**
     * Updates the name of the shared library file that JNA will look for. JNA intelligently looks for this file depending on the name you provide.
     *
     * <p>Here is an excerpt from the <a href="https://java-native-access.github.io/jna/5.17.0/javadoc/#loading">JNA</a> documentation:</p>
     *
     * <blockquote>
     * <table>
     *  <tr><th><b>OS</b></th><th><b>Library Name</b></th><th><b>String</b></th></tr>
     * <p>
     *  <tr><td>Windows</td><td>user32.dll</td><td>user32</td></tr>
     * <p>
     *  <tr><td>Linux</td><td>libX11.so</td><td>X11</td></tr>
     * <p>
     *  <tr><td>Mac OS X</td><td>libm.dylib</td><td>m</td></tr>
     * <p>
     *  <tr><td>Mac OS X Framework</td><td>/System/Library/Frameworks/Carbon.framework/Carbon</td><td>Carbon</td></tr>
     * <p>
     *  <tr><td>Any Platform</td><td>&lt;current process&gt;</td><td><code>null</code></td></tr>
     * </table>
     * </blockquote>
     *
     * @param sharedLibraryName The undecorated name of the shared-library
     *
     * @return this {@link Builder Builder}
     */
    public Builder withSharedLibraryName(String sharedLibraryName) {
        this.sharedLibraryName = sharedLibraryName;
        return this;
    }

    /**
     * When the underlying API is initialized, all keys that are used for capturing, by default, will be disabled.
     *
     * @return this {@link Builder Builder}
     */
    public Builder withCaptureKeysDisabled() {
        this.tasks.add(RenderDocAPI::disableCaptureKeys);
        return this;
    }

    /**
     * When the underlying API is initialized, all keys that are used for focus-toggling, by default, will be disabled.
     *
     * @return this {@link Builder Builder}
     */
    public Builder withFocusToggleKeysDisabled() {
        this.tasks.add(RenderDocAPI::disableFocusToggleKeys);
        return this;
    }

    /**
     * <b><i>Warning: This is an optional operation. If you do not know what you are doing, you most likely do not need to use it.</i></b>
     *
     * <p>Specifies the platform-specific window handle that RenderDocAPI will attempt to use</p>
     *
     * @param windowHandle The platform-specific window handle
     * @return this {@link Builder Builder}
     * @see "The RenderDocAPI Javadoc section, <i>'Obtaining Window Handles and Device Pointers'</i> for more information..."
     */
    public Builder withWindowHandle(long windowHandle) {
        this.windowHandle = windowHandle;
        return this;
    }

    /**
     * <b><i>Warning: This is an optional operation. If you do not know what you are doing, you most likely do not need to use it.</i></b>
     *
     * <p>Specifies the Graphics API-specific device handle that RenderDocAPI will attempt to use</p>
     *
     * @param apiDeviceHandle The Graphics API-specific device handle
     * @return this {@link Builder Builder}
     * @see "The RenderDocAPI Javadoc section, <i>'Obtaining Window Handles and Device Pointers'</i> for more information..."
     */
    public Builder withAPIDeviceHandle(long apiDeviceHandle) {
        this.apiDeviceHandle = apiDeviceHandle;
        return this;
    }

    /**
     * Specifies the maximum length in characters (bytes) that you would like to allocate for the file name/path. This is necessary due to the functionality of the C language.
     *
     * <p>By default, this is {@code 1024}.</p>
     *
     * @param maxFilePathLength The max length in characters (bytes) that you would like to allocate for the file path.
     * @return this {@link Builder Builder}
     */
    public Builder maxFilePathLength(int maxFilePathLength) {
        this.maxFilePathLength = maxFilePathLength;
        return this;
    }

    /**
     * Adds a {@link CaptureListener} to the list of stored listeners.
     *
     * <p>You <b>must</b> call {@link RenderDocAPI#updateCaptureListeners()} periodically in order for these listener(s) to be updated.</p>
     *
     * @param listener A listener for successful {@link FrameCapture} processing
     * @return this {@link Builder Builder}
     * @see CaptureListener
     * @see RenderDocAPI#updateCaptureListeners()
     */
    public Builder addCaptureListener(CaptureListener listener) {

        this.captureListeners.add(listener);

        return this;
    }

    /**
     * If the {@link RenderDocAPI#INSTANCE Instance} is not built already, this method will build it with the configured settings, and return it.
     *
     * @return the now-default {@link RenderDocAPI#INSTANCE Instance}
     * @throws IllegalStateException If the {@link RenderDocAPI#INSTANCE Instance} is built already
     */
    public RenderDocAPI build() {

        if (RenderDocAPI.INSTANCE != null) {
            throw RenderDocAPI.INSTANCE_BUILT_ALREADY.get();
        }

        return RenderDocAPI.INSTANCE = new RenderDocAPI(this);
    }

    RenderDocLibrary getLib() {
        return Native.load(this.sharedLibraryName, RenderDocLibrary.class);
    }
}
