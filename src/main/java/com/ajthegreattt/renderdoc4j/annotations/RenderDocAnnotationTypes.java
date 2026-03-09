package com.ajthegreattt.renderdoc4j.annotations;

/**
 * A utility class for containing the various {@link RenderDocAnnotationType}s.
 *
 * <p>Types that support creation of {@link RenderDocAnnotationValue}s have a {@code value()}
 * and {@code vectorValue()} (if the type supports vectors) method.</p>
 * */
public final class RenderDocAnnotationTypes {
    public static final RenderDocAnnotationType           EMPTY      = new RenderDocAnnotationType("", 0, false);
    public static final RenderDocAnnotationType.Bool      BOOL       = new RenderDocAnnotationType.Bool("bool", 1);
    public static final RenderDocAnnotationType.Int       INT32      = new RenderDocAnnotationType.Int("int32", 2, reference -> reference.int32s);
    public static final RenderDocAnnotationType.Int       UINT32     = new RenderDocAnnotationType.Int("uint32", 3, reference -> reference.uint32s);
    public static final RenderDocAnnotationType.Long      INT64      = new RenderDocAnnotationType.Long("int64", 4, reference -> reference.int64s);
    public static final RenderDocAnnotationType.Long      UINT64     = new RenderDocAnnotationType.Long("uint64", 5, reference -> reference.uint64s);
    public static final RenderDocAnnotationType.Float     FLOAT32    = new RenderDocAnnotationType.Float("float32", 6);
    public static final RenderDocAnnotationType.Double    FLOAT64    = new RenderDocAnnotationType.Double("float64", 7);
    public static final RenderDocAnnotationType.String    STRING     = new RenderDocAnnotationType.String("string", 8);
    public static final RenderDocAnnotationType.ApiObject API_OBJECT = new RenderDocAnnotationType.ApiObject(9);

    private RenderDocAnnotationTypes() {
        throw new AssertionError("You may not instantiate this class.");
    }
}
