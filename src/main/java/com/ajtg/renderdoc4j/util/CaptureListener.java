package com.ajtg.renderdoc4j.util;

import com.ajtg.renderdoc4j.backbone.FrameCapture;
import com.ajtg.renderdoc4j.backbone.RenderDocAPI;

/**
 * A functional interface for listening to when frames are captured.
 * @see RenderDocAPI#triggerCapture()
 * @see FrameCapture
 */
@FunctionalInterface
public interface CaptureListener {

    /**
     * Called after a {@link FrameCapture} is processed and the previous capture is assumed to have been successful.
     *
     * @param frameCapture The {@link FrameCapture} that was processed as a result of an <i>assumingly</i> successfully capture.
     */
    void process(FrameCapture frameCapture);

}
