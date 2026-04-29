import UIKit
import SwiftUI
import ComposeApp

struct ComposeView: UIViewControllerRepresentable {
    
    func makeCoordinator() -> AdsCoordinator {
        AdsCoordinator()
    }
    
    func makeUIViewController(context: Context) -> UIViewController {
        let adMob = getAdMob(context: context)
        let appLovin = getAppLovin(context: context)

        return MainViewControllerKt.MainViewController(
            adMobIos: adMob,
            appLovinIos: appLovin
        )
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}

struct ContentView: View {
    var body: some View {
        ComposeView().ignoresSafeArea(.all)
    }
}

final class AdsCoordinator {
    let interstitial = InterstitialAdManager()
    let rewarded = RewardedAdManager()
    let appLovinInterstitial = AppLovinInterstitialAdManager()
    let appLovinRewarded = AppLovinRewardedAdManager()
}



