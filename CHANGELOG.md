# Changelog

## 2.0.0

### What was added?

- This `CHANGELOG.md` file.
- Support for Java 8.
- Support for RenderDoc API V1.7.0.

### What has changed?

- Updated the RenderDoc `.dll` included with this project.
- Updated JNA to version `5.18.1`.
- Various documentation improvements and clarifications.
- Changed `CaptureListener#ifFailed()`'s input value to an index of the capture that failed rather than the capture
- count, as it is likely more intuitive. 

### Fixed any bugga-wuggas? (Fixed any bugs?)
Yes. Yes we did. :)
- Fixed an "issue" where the `null`-terminating character at the end of a capture's file path was appended to the Java String.
- Fixed an issue where `updateCaptureListeners()` would only notify listeners of the last capture rather than all captures missed.

## 1.0.0

The initial release.