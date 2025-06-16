package com.ajtg.renderdoc4j.options.overlay;

import com.ajtg.renderdoc4j.backbone.RenderDocAPI;
import com.ajtg.renderdoc4j.backbone.RenderDocAPIInternal;
import org.jetbrains.annotations.ApiStatus;

import java.util.EnumSet;

/**
 * A QoL wrapper for managing Overlay Bits.
 *
 * <p>Since RenderDoc does not allow setting bits directly, this class is meant to add some simplicity to the process.</p>
 *
 * @see RenderDocAPI#getOverlaySettingBits()
 */
//TEST: PASSING
public final class OverlaySettingBits {

    private final RenderDocAPIInternal internal;

    @ApiStatus.Internal
    public OverlaySettingBits(RenderDocAPIInternal internal, EnumSet<RenderDocOverlayBit> defaultSettings) {
        this.internal = internal;

        this.internal.MaskOverlayBits.invoke(0, 0);

        for (RenderDocOverlayBit option : defaultSettings) {
            this.internal.MaskOverlayBits.invoke(1, option.getBit());
        }
    }

    /**
     * Obtains the currently active overlay bits using {@link OverlaySettingBits#getOverlayBits()}, and checks what bits are active using bitwise AND ({@code &}) operations.
     *
     * @return An {@link EnumSet} of the currently active {@link RenderDocOverlayBit}s
     * @see OverlaySettingBits#getOverlayBits()
     **/
    public EnumSet<RenderDocOverlayBit> getActiveBits() {

        final int overlayBits = getOverlayBits();

        var toRet = EnumSet.noneOf(RenderDocOverlayBit.class);

        for (RenderDocOverlayBit value : RenderDocOverlayBit.values()) {
            if ((overlayBits & value.getBit()) == 0b1) {
                toRet.add(value);
            }
        }

        return toRet;
    }

    /**
     * @return The bit mask that determines what overlay settings are active
     * @see OverlaySettingBits#getActiveBits()
     */
    //TEST: PASSING
    public int getOverlayBits() {
        return this.internal.GetOverlayBits.invoke();
    }

    /**
     * Performs an XOR ({@code ^}) bitwise operation on the given {@link RenderDocOverlayBit}, effectively swapping the option between OFF ({@code 0}) and ON ({@code 1}) status.
     *
     * @param option The {@link RenderDocOverlayBit} that you would like to toggle
     * @see OverlaySettingBits#turnOff(RenderDocOverlayBit option)
     * @see OverlaySettingBits#turnOn(RenderDocOverlayBit option)
     */
    //TEST: PASSING
    public void toggle(RenderDocOverlayBit option) {
        int overlayBits = getOverlayBits();

        //Java doesn't do all that "unsigned bitness"...
        this.internal.MaskOverlayBits.invoke(~(option.getBit() << 1) >>> 1, option.getBit() & ~overlayBits);
    }

    /**
     * Enables the given {@link RenderDocOverlayBit}.
     *
     * @param option The {@link RenderDocOverlayBit} that you would like to turn on
     * @see OverlaySettingBits#toggle(RenderDocOverlayBit option)
     * @see OverlaySettingBits#turnOff(RenderDocOverlayBit option)
     */
    //TEST: PASSING
    public void turnOn(RenderDocOverlayBit option) {
        this.internal.MaskOverlayBits.invoke(1, option.getBit());
    }

    /**
     * Disables the given {@link RenderDocOverlayBit}.
     *
     * @param option The {@link RenderDocOverlayBit} that you would like to turn on
     * @see OverlaySettingBits#toggle(RenderDocOverlayBit option)
     * @see OverlaySettingBits#turnOn(RenderDocOverlayBit option)
     */
    //TEST: PASSING
    public void turnOff(RenderDocOverlayBit option) {
        this.internal.MaskOverlayBits.invoke(~option.getBit(), 0);
    }

    /**
     * Uses an AND ({@code &}) bitwise operation to determine if the given {@link RenderDocOverlayBit} is active.
     * @param option The option you would like to query the status of
     * @return True if the option is enabled
     **/
    //TEST: PASSING
    public boolean isEnabled(RenderDocOverlayBit option) {
        return (getOverlayBits() & option.getBit()) != 0;
    }

}
