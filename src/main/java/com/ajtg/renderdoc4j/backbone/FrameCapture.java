package com.ajtg.renderdoc4j.backbone;

/**
 * A record that stores all the information for a capture.
 *
 * @param index The index of this capture, which is dependent upon the amount of captures in the current session
 * @param fileName The name and path to the capture file. This path may not be absolute depending upon the {@link RenderDocAPI#setCaptureFilePath}.
 * @param timestamp The timestamp of when the capture was taken.
 * @see RenderDocAPI#getCapture(int)
 * @see RenderDocAPI#setCaptureFilePath
 * @see RenderDocAPI#getCaptureFilePath
 */
public record FrameCapture(int index, String fileName, long timestamp) {}
