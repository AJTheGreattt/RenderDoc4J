package com.ajthegreattt.renderdoc4j.annotations;

import com.ajthegreattt.renderdoc4j.backbone.RenderDocLibrary;
import com.sun.jna.Pointer;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

/**
 * A Java representation of the {@code RENDERDOC_AnnotationType} enum.
 * */
public class RenderDocAnnotationType {

    public final int value;

    @NotNull
    public final java.lang.String fieldName;

    @NotNull
    public final java.lang.String vectorFieldName;

    RenderDocAnnotationType(java.lang.@NotNull String name, int value, boolean flag) {
        this.value = value;
        if (flag) {
            this.fieldName = name;
            this.vectorFieldName = this.fieldName.isEmpty() ? "" : this.fieldName + "s";
        } else {
            //noinspection DataFlowIssue
            this.fieldName = this.vectorFieldName = null;
        }
    }

    RenderDocAnnotationType(java.lang.String name, int enumValue) {
        this(name, enumValue, true);
    }

    public static class Bool extends RenderDocAnnotationType {
        Bool(java.lang.String name, int enumValue) {
            super(name, enumValue);
        }

        public RenderDocAnnotationValue value(boolean value) {
            return new RenderDocAnnotationValue(this) {
                @Override
                public RenderDocLibrary.RENDERDOC_AnnotationValue.ByReference toUnion() {
                    final RenderDocLibrary.RENDERDOC_AnnotationValue.ByReference byReference = new RenderDocLibrary.RENDERDOC_AnnotationValue.ByReference();

                    if (value) {
                        byReference.bool = 1;
                    } else {
                        byReference.bool = 0;
                    }

                    byReference.setType(this.annotationType.fieldName);

                    return byReference;
                }
            };
        }

        public RenderDocAnnotationValue vectorValue(boolean[] bools) {
            if (bools.length > 4) {
                throw new IllegalArgumentException("Vector length must be 4 or less");
            }

            return new RenderDocAnnotationValue(this, bools.length) {
                @Override
                public RenderDocLibrary.RENDERDOC_AnnotationValue.ByReference toUnion() {
                    final RenderDocLibrary.RENDERDOC_AnnotationValue.ByReference valueByReference = new RenderDocLibrary.RENDERDOC_AnnotationValue.ByReference();

                    final RenderDocLibrary.RENDERDOC_AnnotationVectorValue.ByValue vector = new RenderDocLibrary.RENDERDOC_AnnotationVectorValue.ByValue();;

                    for (int i = 0; i < this.vectorLength; ++i) {
                        //use an if since the compiler
                        // won't understand if we use a ternary operator
                        // 1 & 0 as byte values and requires int casting
                        if (bools[i]) {
                            vector.bools[i] = 1;
                        } else {
                            vector.bools[i] = 0;
                        }
                    }

                    vector.setType(this.annotationType.vectorFieldName);

                    valueByReference.vector = vector;

                    valueByReference.setType("vector");

                    return valueByReference;
                }
            };
        }
    }

    public static class Int extends RenderDocAnnotationType {
        private final Function<RenderDocLibrary.RENDERDOC_AnnotationVectorValue, int[]> vectorGetter;

        Int(java.lang.String name, int enumValue, Function<RenderDocLibrary.RENDERDOC_AnnotationVectorValue, int[]> vectorGetter) {
            super(name, enumValue);
            this.vectorGetter = vectorGetter;
        }

        public RenderDocAnnotationValue value(int value) {
            return new RenderDocAnnotationValue(this) {
                @Override
                public RenderDocLibrary.RENDERDOC_AnnotationValue.ByReference toUnion() {
                    final RenderDocLibrary.RENDERDOC_AnnotationValue.ByReference byReference = new RenderDocLibrary.RENDERDOC_AnnotationValue.ByReference();

                    switch (this.annotationType.fieldName) {
                        case "int32": {
                            byReference.int32 = value;
                            break;
                        }

                        case "uint32": {
                            byReference.uint32 = value;
                            break;
                        }
                    }

                    byReference.setType(this.annotationType.fieldName);

                    return byReference;
                }
            };
        }

        public RenderDocAnnotationValue vectorValue(int[] values) {
            //noinspection DuplicatedCode
            if (values.length > 4) {
                throw new IllegalArgumentException("Vector length must be 4 or less");
            }

            return new RenderDocAnnotationValue(this, values.length) {
                @Override
                public RenderDocLibrary.RENDERDOC_AnnotationValue.ByReference toUnion() {
                    final RenderDocLibrary.RENDERDOC_AnnotationValue.ByReference valueByReference = new RenderDocLibrary.RENDERDOC_AnnotationValue.ByReference();

                    final RenderDocLibrary.RENDERDOC_AnnotationVectorValue.ByValue vector = new RenderDocLibrary.RENDERDOC_AnnotationVectorValue.ByValue();;

                    System.arraycopy(values, 0, vectorGetter.apply(vector), 0, this.vectorLength);

                    vector.setType(this.annotationType.vectorFieldName);

                    valueByReference.vector = vector;

                    valueByReference.setType("vector");

                    return valueByReference;
                }
            };
        }
    }

    public static class Long extends RenderDocAnnotationType {
        private final Function<RenderDocLibrary.RENDERDOC_AnnotationVectorValue, long[]> vectorGetter;

        Long(java.lang.String name, int enumValue, Function<RenderDocLibrary.RENDERDOC_AnnotationVectorValue, long[]> vectorGetter) {
            super(name, enumValue);
            this.vectorGetter = vectorGetter;
        }

        public RenderDocAnnotationValue value(int value) {
            return new RenderDocAnnotationValue(this) {
                @Override
                public RenderDocLibrary.RENDERDOC_AnnotationValue.ByReference toUnion() {
                    final RenderDocLibrary.RENDERDOC_AnnotationValue.ByReference byReference = new RenderDocLibrary.RENDERDOC_AnnotationValue.ByReference();

                    switch (this.annotationType.fieldName) {
                        case "int64": {
                            byReference.int64 = value;
                            break;
                        }
                        case "uint64": {
                            byReference.uint64 = value;
                            break;
                        }
                    }

                    byReference.setType(this.annotationType.fieldName);

                    return byReference;
                }
            };
        }

        public RenderDocAnnotationValue vectorValue(long[] values) {
            //noinspection DuplicatedCode
            if (values.length > 4) {
                throw new IllegalArgumentException("Vector length must be 4 or less");
            }

            return new RenderDocAnnotationValue(this, values.length) {
                @Override
                public RenderDocLibrary.RENDERDOC_AnnotationValue.ByReference toUnion() {
                    final RenderDocLibrary.RENDERDOC_AnnotationValue.ByReference valueByReference = new RenderDocLibrary.RENDERDOC_AnnotationValue.ByReference();

                    final RenderDocLibrary.RENDERDOC_AnnotationVectorValue.ByValue vector = new RenderDocLibrary.RENDERDOC_AnnotationVectorValue.ByValue();;

                    System.arraycopy(values, 0, vectorGetter.apply(vector), 0, this.vectorLength);

                    vector.setType(this.annotationType.vectorFieldName);

                    valueByReference.vector = vector;

                    valueByReference.setType("vector");

                    return valueByReference;
                }
            };
        }
    }

    public static class Float extends RenderDocAnnotationType {
        Float(java.lang.String name, int enumValue) {
            super(name, enumValue);
        }

        public RenderDocAnnotationValue value(float value) {
            return new RenderDocAnnotationValue(this) {
                @Override
                public RenderDocLibrary.RENDERDOC_AnnotationValue.ByReference toUnion() {
                    final RenderDocLibrary.RENDERDOC_AnnotationValue.ByReference byReference = new RenderDocLibrary.RENDERDOC_AnnotationValue.ByReference();

                    byReference.float32 = value;

                    byReference.setType(this.annotationType.fieldName);

                    return byReference;
                }
            };
        }

        public RenderDocAnnotationValue vectorValue(float[] values) {
            if (values.length > 4) {
                throw new IllegalArgumentException("Vector length must be 4 or less");
            }

            return new RenderDocAnnotationValue(this, values.length) {
                @Override
                public RenderDocLibrary.RENDERDOC_AnnotationValue.ByReference toUnion() {
                    final RenderDocLibrary.RENDERDOC_AnnotationValue.ByReference valueByReference = new RenderDocLibrary.RENDERDOC_AnnotationValue.ByReference();

                    final RenderDocLibrary.RENDERDOC_AnnotationVectorValue.ByValue vector = new RenderDocLibrary.RENDERDOC_AnnotationVectorValue.ByValue();;

                    System.arraycopy(values, 0, vector.float32s, 0, this.vectorLength);

                    vector.setType(this.annotationType.vectorFieldName);

                    valueByReference.vector = vector;

                    valueByReference.setType("vector");

                    return valueByReference;
                }
            };
        }
    }

    public static class Double extends RenderDocAnnotationType {
        Double(java.lang.String name, int enumValue) {
            super(name, enumValue);
        }

        public RenderDocAnnotationValue value(double value) {
            return new RenderDocAnnotationValue(this) {
                @Override
                public RenderDocLibrary.RENDERDOC_AnnotationValue.ByReference toUnion() {
                    final RenderDocLibrary.RENDERDOC_AnnotationValue.ByReference byReference = new RenderDocLibrary.RENDERDOC_AnnotationValue.ByReference();

                    byReference.float64 = value;

                    byReference.setType(this.annotationType.fieldName);

                    return byReference;
                }
            };
        }

        public RenderDocAnnotationValue vectorValue(double[] values) {
            if (values.length > 4) {
                throw new IllegalArgumentException("Vector length must be 4 or less");
            }

            return new RenderDocAnnotationValue(this, values.length) {
                @Override
                public RenderDocLibrary.RENDERDOC_AnnotationValue.ByReference toUnion() {
                    final RenderDocLibrary.RENDERDOC_AnnotationValue.ByReference valueByReference = new RenderDocLibrary.RENDERDOC_AnnotationValue.ByReference();

                    final RenderDocLibrary.RENDERDOC_AnnotationVectorValue.ByValue vector = new RenderDocLibrary.RENDERDOC_AnnotationVectorValue.ByValue();;

                    System.arraycopy(values, 0, vector.float64s, 0, this.vectorLength);

                    vector.setType(this.annotationType.vectorFieldName);

                    valueByReference.vector = vector;

                    valueByReference.setType("vector");

                    return valueByReference;
                }
            };
        }
    }

    public static class String extends RenderDocAnnotationType {
        String(java.lang.String name, int enumValue) {
            super(name, enumValue);
        }

        public RenderDocAnnotationValue value(java.lang.String value) {
            return new RenderDocAnnotationValue(this) {
                @Override
                public RenderDocLibrary.RENDERDOC_AnnotationValue.ByReference toUnion() {
                    final RenderDocLibrary.RENDERDOC_AnnotationValue.ByReference byReference = new RenderDocLibrary.RENDERDOC_AnnotationValue.ByReference();

                    byReference.string = value;

                    byReference.setType(this.annotationType.fieldName);

                    return byReference;
                }
            };
        }
    }

    public static class ApiObject extends RenderDocAnnotationType {
        ApiObject(int enumValue) {
            super("apiObject", enumValue);
        }

        public RenderDocAnnotationValue value(Pointer value) {
            return new RenderDocAnnotationValue(this) {
                @Override
                public RenderDocLibrary.RENDERDOC_AnnotationValue.ByReference toUnion() {
                    final RenderDocLibrary.RENDERDOC_AnnotationValue.ByReference byReference = new RenderDocLibrary.RENDERDOC_AnnotationValue.ByReference();

                    byReference.apiObject = value;

                    byReference.setType(this.annotationType.fieldName);

                    return byReference;
                }
            };
        }
    }

}
