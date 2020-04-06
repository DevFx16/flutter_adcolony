#import "AdcolonyPlugin.h"
#if __has_include(<adcolony/adcolony-Swift.h>)
#import <adcolony/adcolony-Swift.h>
#else
// Support project import fallback if the generated compatibility header
// is not copied when this plugin is created as a library.
// https://forums.swift.org/t/swift-static-libraries-dont-copy-generated-objective-c-header/19816
#import "adcolony-Swift.h"
#endif

@implementation AdcolonyPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftAdcolonyPlugin registerWithRegistrar:registrar];
}
@end
