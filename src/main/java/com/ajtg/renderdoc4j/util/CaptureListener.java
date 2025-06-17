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

    /**
     * This is an optional method that you can override if you would like certain functionality to run when a {@link FrameCapture} fails to process after a capture is made.
     *
     * @param captureCount The current capture count
     *
     * @apiNote Just because a {@link FrameCapture} failed to process does not mean that the <i>capture</i> failed to process. The underlying RenderDoc API does not specify if the capture is made successfully.
     *
     * @see RenderDocAPI#getNumCaptures()
     */
    default void ifFailed(int captureCount) {}
}
