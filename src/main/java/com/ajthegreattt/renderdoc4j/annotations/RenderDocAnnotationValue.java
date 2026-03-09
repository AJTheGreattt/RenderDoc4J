package com.ajthegreattt.renderdoc4j.annotations;

import com.ajthegreattt.renderdoc4j.backbone.RenderDocLibrary;

/**
 * A Java representation of the {@link RenderDocLibrary.RENDERDOC_AnnotationValue} union, specifically with vector
 * fields for handling different types of annotation values.
 *
 * @see RenderDocAnnotationTypes
 * */
public abstract class RenderDocAnnotationValue {

    public final RenderDocAnnotationType annotationType;
    public final int vectorLength;

    RenderDocAnnotationValue(RenderDocAnnotationType annotationType) {
        this(annotationType, 0);
    }

    RenderDocAnnotationValue(RenderDocAnnotationType annotationType, int vectorLength) {
            this.annotationType = annotationType;
            this.vectorLength = vectorLength;
    }

    public abstract RenderDocLibrary.RENDERDOC_AnnotationValue.ByReference toUnion();

}
