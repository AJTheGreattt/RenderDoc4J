package com.ajthegreattt.renderdoc4j.backbone;

import java.util.Objects;

/**
 * A POJO that stores all the information for a capture.
 *
 * @see RenderDocAPI#getCapture(int)
 * @see RenderDocAPI#setCaptureFilePath
 * @see RenderDocAPI#getCaptureFilePath
 */
public final class FrameCapture {
    private final int index;
    private final String fileName;
    private final long timestamp;

    /**
     * @param index     The index of this capture, which is dependent upon the number of captures in the current session
     * @param fileName  The name and path to the capture file. This path may not be absolute depending upon the {@link RenderDocAPI#setCaptureFilePath}.
     * @param timestamp The timestamp of when the capture was taken
     *
     */
    public FrameCapture(int index, String fileName, long timestamp) {
        this.index = index;
        this.fileName = fileName;
        this.timestamp = timestamp;
    }

    public int index() {
        return index;
    }

    public String fileName() {
        return fileName;
    }

    public long timestamp() {
        return timestamp;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        FrameCapture that = (FrameCapture) obj;
        return this.index == that.index &&
                Objects.equals(this.fileName, that.fileName) &&
                this.timestamp == that.timestamp;
    }

    @Override
    public int hashCode() {
        return Objects.hash(index, fileName, timestamp);
    }

    @Override
    public String toString() {
        return "FrameCapture[" +
                "index=" + index + ", " +
                "fileName=" + fileName + ", " +
                "timestamp=" + timestamp + ']';
    }
}
