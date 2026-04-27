import SwiftUI
import ComposeApp
import GoogleMobileAds
import FirebaseCore

@main
struct iOSApp: App {
    
    init() {
            FirebaseApp.configure()
    }

    @UIApplicationDelegateAdaptor(AppDelegate.self) var appDelegate
    
    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}

