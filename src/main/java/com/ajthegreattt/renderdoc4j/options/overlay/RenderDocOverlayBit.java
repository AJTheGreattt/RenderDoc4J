package com.ajthegreattt.renderdoc4j.options.overlay;

import com.ajthegreattt.renderdoc4j.util.EnumIntNativeMapped;

public enum RenderDocOverlayBit implements EnumIntNativeMapped {

        /**
         * From the <a href="https://renderdoc.org/docs/in_application_api.html#_CPPv4N21RENDERDOC_OverlayBits26eRENDERDOC_Overlay_EnabledE">Official RenderDoc Documentation</a>:
         *
         * <blockquote>
         *     ...is an overall enable/disable bit. If this is disabled, no overlay renders.
         * </blockquote>
         */
        ENABLED(0b1),

        /**
         * From the <a href="https://renderdoc.org/docs/in_application_api.html#_CPPv4N21RENDERDOC_OverlayBits28eRENDERDOC_Overlay_FrameRateE">Official RenderDoc Documentation</a>:
         *
         * <blockquote>
         *     ...shows the average, min and max frame time in milliseconds, and the average framerate.
         * </blockquote>
         */
        FRAME_RATE(0b10),

        /**
         * From the <a href="https://renderdoc.org/docs/in_application_api.html#_CPPv4N21RENDERDOC_OverlayBits30eRENDERDOC_Overlay_FrameNumberE">Official RenderDoc Documentation</a>:
         *
         * <blockquote>
         *     ...shows the current frame number, as counted by the number of presents.
         * </blockquote>
         */
        FRAME_NUMBER(0b100),

        /**
         * From the <a href="https://renderdoc.org/docs/in_application_api.html#_CPPv4N21RENDERDOC_OverlayBits30eRENDERDOC_Overlay_CaptureListE">Official RenderDoc Documentation</a>:
         *
         * <blockquote>
         *     ...shows how many total captures have been made, and a list of captured frames in the last few seconds.
         * </blockquote>
         */
        CAPTURE_LIST(0b1000),

        /**
         * From the <a href="https://renderdoc.org/docs/in_application_api.html#_CPPv4N21RENDERDOC_OverlayBits26eRENDERDOC_Overlay_DefaultE">Official RenderDoc Documentation</a>:
         *
         * <blockquote>
         *     ...is the default set of bits, which is the value of the mask at startup.
         * </blockquote>
         */
        DEFAULT(ENABLED.bit | FRAME_RATE.bit | FRAME_NUMBER.bit | CAPTURE_LIST.bit),

        /**
         * From the <a href="https://renderdoc.org/docs/in_application_api.html#_CPPv4N21RENDERDOC_OverlayBits22eRENDERDOC_Overlay_AllE">Official RenderDoc Documentation</a>:
         *
         * <blockquote>
         *     ...is equal to ~0U so all bits are enabled.
         * </blockquote>
         *
         * @apiNote There are no unsigned numbers in Java, so we use the manual inverse...
         */
        ALL(0b1111),

        /**
         * From the <a href="https://renderdoc.org/docs/in_application_api.html#_CPPv4N21RENDERDOC_OverlayBits23eRENDERDOC_Overlay_NoneE">Official RenderDoc Documentation</a>:
         *
         * <blockquote>
         *     ...is equal to 0 so all bits are disabled.
         * </blockquote>
         */
        NONE(0b0000);

    private final int bit;

    RenderDocOverlayBit(int bit) {
        this.bit = bit;
    }

    @Override
    public Object toNative() {
        return getBit();
    }

    /**
     * @return The bit that corresponds with this {@link RenderDocOverlayBit}
     * @see OverlaySettingBits
     */
    public int getBit() {
        return this.bit;
    }
}
