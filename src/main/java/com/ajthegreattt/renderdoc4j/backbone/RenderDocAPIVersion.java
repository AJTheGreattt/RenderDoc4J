package com.ajthegreattt.renderdoc4j.backbone;

import com.sun.jna.FromNativeContext;
import com.sun.jna.NativeMapped;

public enum RenderDocAPIVersion implements NativeMapped {

    /**
     * RenderDocAPI V1.0.0.
     */
    Version_1_0_0(1, 0, 0),

    /**
     * RenderDocAPI V1.0.1.
     */
    Version_1_0_1(1, 0, 1),

    /**
     * RenderDocAPI V1.0.2.
     */
    Version_1_0_2(1, 0, 2),

    /**
     * RenderDocAPI V1.1.0.
     */
    Version_1_1_0(1, 1, 0),

    /**
     * RenderDocAPI V1.1.1.
     */
    Version_1_1_1(1, 1, 1),

    /**
     * RenderDocAPI V1.1.2.
     */
    Version_1_1_2(1, 1, 2),

    /**
     * RenderDocAPI V1.2.0.
     */
    Version_1_2_0(1, 2, 0),

    /**
     * RenderDocAPI V1.3.0.
     */
    Version_1_3_0(1, 3, 0),

    /**
     * RenderDocAPI V1.4.0.
     */
    Version_1_4_0(1, 4, 0),

    /**
     * RenderDocAPI V1.4.1.
     */
    Version_1_4_1(1, 4, 1),

    /**
     * RenderDocAPI V1.4.2.
     */
    Version_1_4_2(1, 4, 2),

    /**
     * RenderDocAPI V1.5.0.
     */
    Version_1_5_0(1, 5, 0),

    /**
     * The default version of the {@link RenderDocAPI} that will be used if no {@link RenderDocAPIVersion Version} is specified.
     *
     * <p>This is the latest version of the API.</p>
     *
     * <p>RenderDocAPI V1.6.0.</p>
     */
    Version_1_6_0(1, 6, 0);

    private final int major;
    private final int minor;
    private final int patch;

    private final int strippedVersion;

    private final int fullVersion;

    @SuppressWarnings("SameParameterValue")
    RenderDocAPIVersion(int major, int minor, int patch) {
        this.major = major;
        this.minor = minor;
        this.patch = patch;
        this.strippedVersion = Integer.parseInt("" + major + minor + patch);

        this.fullVersion = Integer.parseInt("" + major + (minor < 10 ? "0" + minor : minor) + (patch < 10 ? "0" + patch : patch));
    }

    /**
     * Attempts to parse an integer that should be in the format of {@code major,minor,patch}.
     *
     * <p>For example: the latest version would be "160", where the major is 1, the minor is 6, and the patch is 0.</p>
     *
     * @param version The version you would like to try and parse
     * @return The parsed {@link RenderDocAPIVersion}
     * @throws IllegalArgumentException If the {@code version} passed in is invalid
     */
    public static RenderDocAPIVersion fromInt(int version) {
        return (switch (version) {
            case 100 -> Version_1_0_0;
            case 101 -> Version_1_0_1;
            case 102 -> Version_1_0_2;
            case 110 -> Version_1_1_0;
            case 111 -> Version_1_1_1;
            case 112 -> Version_1_1_2;
            case 120 -> Version_1_2_0;
            case 130 -> Version_1_3_0;
            case 140 -> Version_1_4_0;
            case 141 -> Version_1_4_1;
            case 142 -> Version_1_4_2;
            case 150 -> Version_1_5_0;
            case 160 -> Version_1_6_0;
            default -> throw new IllegalArgumentException("There is no RenderDocAPI Version listed for " + version);
        });
    }

    @Override
    public Object fromNative(Object nativeValue, FromNativeContext context) {
        if (context.getTargetType().equals(this.getClass())) {
            if (nativeValue instanceof Integer intC) {
                for (RenderDocAPIVersion value : values()) {
                    if (value.fullVersion == intC) {
                        return value;
                    }
                }
            }
        }

        throw new IllegalArgumentException("Invalid contex for Enum conversion");
    }

    public static RenderDocAPIVersion latest() {
        final RenderDocAPIVersion[] values = values();

        return values[values.length - 1];
    }

    @Override
    public Object toNative() {
        return this.fullVersion;
    }

    @Override
    public Class<?> nativeType() {
        return Integer.TYPE;
    }

    public int major() {
        return this.major;
    }

    public int minor() {
        return this.minor;
    }

    public int patch() {
        return this.patch;
    }

    /**
     * @return The version formatted as described by {@link RenderDocAPIVersion#fromInt(int)}
     */
    public int getStrippedVersion() {
        return this.strippedVersion;
    }

    /**
     * Returns an integer that is formatted such that if a version position is less than {@code 10} and is not {@code 0}, a {@code 0} is appended to it regardless.
     *
     * <p>For example: the latest version would be {@code "10600"}, where the major is {@code 1}, the minor is {@code 6}, and the patch is {@code 0}.
     *
     * @return The integer version formatted as described above
     */
    public int getFullVersion() {
        return this.fullVersion;
    }
}
