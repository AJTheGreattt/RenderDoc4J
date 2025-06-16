package com.ajtg.renderdoc4j.util;

import com.sun.jna.FromNativeContext;
import com.sun.jna.NativeMapped;

import java.util.Optional;

/**
 * An interface for inlining identical {@link NativeMapped} functionality for Enums under this library.
 */
public interface EnumIntNativeMapped extends NativeMapped {

    @Override
    default Object fromNative(Object nativeValue, FromNativeContext context) {

        final Class<? extends EnumIntNativeMapped> enumClass = this.getClass();

        if (!enumClass.isEnum()) {
            throw new IllegalArgumentException("Only enum classes can implement this interface");
        }

        if (context.getTargetType().equals(enumClass)) {
            if (nativeValue instanceof Integer intC) {
                for (EnumIntNativeMapped value : enumClass.getEnumConstants()) {
                    if (intC.equals(value.toNative())) {
                        return Optional.of(value);
                    }
                }
            }
        }

        throw new IllegalArgumentException("Invalid context for Enum conversion");
    }

    @Override
    default Class<?> nativeType() {
        return Integer.TYPE;
    }
}
