# RenderDoc4J

This library wraps the API of the renown [RenderDoc](https://renderdoc.org)
software and creates high-level abstracted bindings that allow
for users to utilize it in Java.

## Getting Started

If you wanna' just get right to it, call `RenderDocAPI.getInstance()` before any Graphics API calls, and (as long as your program's window is up...) you will see that RenderDoc is injected into your program.

### Lazy Initialization (for those who need all the details...)

This library uses a lazy initialization approach
to allow for direct control of when the RenderDocAPI will be injected into the application.

As soon as the `Instance` of the library is built—whether it indirectly from `RenderDocAPI.getInstance()`, or from a
`Builder` instance's `build()` call—it will be injected into your program.

It is important that you initialize the `Instance` before any Graphics API calls, just as it is somewhat required for
RenderDoc.

Initializing the `Instance` after Graphics API calls have already been made has undefined results.

### Obtaining an Instance

There are multiple ways to create and obtain the `Instance`\:

* `RenderDocAPI.getInstance()`
  * Calling this method will check if the `Instance` is null, and if it is, will create a new default-setting `Instance`.
* `RenderDocAPI.builder()`
  * This method will return a `Builder` instance which you can customize as you see fit. Consult the Javadocs for the `Builder` class for more information...
* `RenderDocAPI.builder(version)` & `RenderDocAPI.getOrBuildDefaultInstance(version)`
  * These two methods allow for you to request a specific `RenderDocAPIVersion`. Please see the [API Versioning](#api-versioning) section below for more information...

## API Versioning

**Warning\:** The `RenderDocAPIVersion` you request may not be available depending on the version of the RenderDoc shared-library file you may specify.

Specifying a `RenderDocAPIVersion` and/or shared-library file for RenderDoc is not a requirement to use this library.

 From the [Official RenderDoc Documentation](https://renderdoc.org/docs/in_application_api.html)\:

 > Make sure to use a matching API header for your build - if you use a newer header, the API version may not be available.
 >
 > All RenderDoc builds supporting this API ship the header in their root directory.

 As long as RenderDoc licensing allows, this library will come packaged with the latest RenderDoc .DLL (for Windows) by default. If you are on another platform, you can still use this API as long as the following is true\:
  
   * RenderDoc can run on your platform
   * You can provide the absolute path of the RenderDoc shared-library file to [JNA](https://github.com/java-native-access/jna)
   * [JNA](https://github.com/java-native-access/jna) can load the shared-library file for RenderDoc on your platform


_See also `Builder#withAbsoluteSharedLibraryPath(Path)` and `Builder#withSharedLibraryResource(String)` for information on how to provide your own shared-library file..._

## Unassociated with RenderDoc

**Note**\: Although this library would not be possible without the fantastic work of Baldur Karlsson and contributors,
RenderDoc4J is unassociated with RenderDoc whatsoever.

Do not report bugs for RenderDoc4J to RenderDoc, and do not report bugs for RenderDoc to RenderDoc4J.

If you are unsure if the bug is from this library, JNA, or RenderDoc, you may post the issue on
the [RenderDoc4J GitHub](https://github.com), and we will do our best to guide you.

## Dependencies and Licensing

**Warning**\: While RenderDoc's Licensing allows, this library will bundle the [RenderDoc](https://renderdoc.org) .DLL (for Windows), which is also
licensed under the MIT License.

A copy of RenderDoc's License and Copyright Notices can be found in the [
`licenses/RenderDoc License.md`](licenses/RenderDoc%20License.md) markdown file within this distribution.