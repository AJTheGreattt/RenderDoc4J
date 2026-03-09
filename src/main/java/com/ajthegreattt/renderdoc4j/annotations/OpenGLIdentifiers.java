package com.ajthegreattt.renderdoc4j.annotations;

import com.ajthegreattt.renderdoc4j.backbone.RenderDocLibrary;

/**
 * An enum that simply maps OpenGL identifiers to their respective values.
 * It exists primarily to ensure proper {@code int}s are used with {@link RenderDocLibrary.RENDERDOC_GLResourceReference}.
 * */
public enum OpenGLIdentifiers {
    BUFFER(0x82E0),
    SHADER(0x82E1),
    PROGRAM(0x82E2),
    QUERY(0x82E3),
    PROGRAM_PIPELINE(0x82E4),
    SAMPLER(0x82E6),
    VERTEX_ARRAY(0x8074),
    TRANSFORM_FEEDBACK(0x8E22),
    TEXTURE(0x1702),
    RENDERBUFFER(0x8D41),
    FRAMEBUFFER(0x8D40),

    /**
     * @apiNote The OpenGL specs state that this identifier is only available in the Compatibility Profile.
     *
     **/
    DISPLAY_LIST(0x82E7);

    public final int value;

    OpenGLIdentifiers(int value) {
        this.value = value;
    }
}
