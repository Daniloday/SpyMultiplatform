import SwiftUI
import ComposeApp

@main
struct iOSApp: App {

    init() {
        KoinIosKt.StartKoin()
    }
    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
