package com.ajthegreattt.renderdoc4j.annotations;

/**
 * An enum that corresponds to the various possible return values of Annotation Viewer System specific functions.
 *
 * @apiNote The ordinals of these enums coincide with the return values of the RenderDoc API functions.
 * */
public enum ReturnResult {
    /**
     * From the <a href="https://renderdoc.org/docs/in_application_api.html#_CPPv415SetCaptureTitlePKc">Official RenderDoc Documentation</a>:
     * <blockquote>
     * Returns {@code 0} if the annotation was successfully set.
     * </blockquote>
     * */
    SUCCESS,

    /**
     * From the <a href="https://renderdoc.org/docs/in_application_api.html#_CPPv415SetCaptureTitlePKc">Official RenderDoc Documentation</a>:
     * <blockquote>
     * Returns {@code 1} if the device is unknown or invalid.
     * </blockquote>
     * */
    DEVICE_UNKNOWN_OR_INVALID,

    /**
     * From the <a href="https://renderdoc.org/docs/in_application_api.html#_CPPv415SetCaptureTitlePKc">Official RenderDoc Documentation</a>:
     * <blockquote>
     * Returns {@code 2} if the device is valid but the annotation
     * is not recognised or not supported for API-specific reasons,
     * such as an unrecognised or invalid object or queue/commandbuffer.
     * </blockquote>
     * */
    ANNOTATION_NOT_RECOGNIZED,

    /**
     * From the <a href="https://renderdoc.org/docs/in_application_api.html#_CPPv415SetCaptureTitlePKc">Official RenderDoc Documentation</a>:
     * <blockquote>
     * Returns {@code 3} if the call is ill-formed or invalid e.g. empty is
     * specified with a value pointer, or non-empty is specified with a {@code null} value pointer.
     * </blockquote>
     * */
    INVALID_CALL;

    public static ReturnResult map(int result) {
        switch (result) {
            case 0:
                return SUCCESS;
            case 1:
                return DEVICE_UNKNOWN_OR_INVALID;
            case 2:
                return ANNOTATION_NOT_RECOGNIZED;
            case 3:
                return INVALID_CALL;
            default:
                return null;
        }
    }
}
