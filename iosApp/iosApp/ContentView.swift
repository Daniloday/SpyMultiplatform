import UIKit
import SwiftUI
import ComposeApp

struct ComposeView: UIViewControllerRepresentable {
    
    func makeCoordinator() -> InterstitialAdManager {
        InterstitialAdManager()
    }
    
    func makeUIViewController(context: Context) -> UIViewController {

            let adMob = getAdMob(context: context)
        
            return MainViewControllerKt.MainViewController(adMobIos: adMob)
        }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}

struct ContentView: View {
    var body: some View {
        ComposeView().ignoresSafeArea(.all)
    }
}




