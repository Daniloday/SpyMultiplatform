//
//  Admob.swift
//  iosApp
//
//  Created by Danylo Vladyka on 11.10.2024.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import UIKit
import GoogleMobileAds
import SwiftUI
import ComposeApp

public class AppDelegate: UIResponder, UIApplicationDelegate {
    public func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?) -> Bool {
        GADMobileAds.sharedInstance().start(completionHandler: nil)
        return true
    }
}

private struct BannerAdView: UIViewRepresentable {

    public func makeUIView(context: Context) -> GADBannerView {
        let bannerView = GADBannerView()
        
        bannerView.adUnitID = "ca-app-pub-6281392964919353/1238874171"
        let windowScene = UIApplication.shared.connectedScenes.first as? UIWindowScene
        if let rootViewController = windowScene?.windows.first?.rootViewController {
            bannerView.rootViewController = rootViewController
        }
        bannerView.adSize = GADAdSizeBanner
        bannerView.load(GADRequest())
        return bannerView
    }
    
    public func updateUIView(_ uiView: GADBannerView, context: Context) {
        
    }
    
}

class InterstitialAdManager: NSObject {

    private var rewardedAd: GADRewardedAd?
    private var onAdClosed: (() -> Void)?


    func loadAd() {
        let adRequest = GADRequest()
        GADRewardedAd.load(withAdUnitID: "ca-app-pub-6281392964919353/9291844380", request: adRequest) { [weak self] ad, error in
            if let error = error {
                print("Failed to load rewarded ad: \(error.localizedDescription)")
                self?.rewardedAd = nil
            } else {
                self?.rewardedAd = ad
                print("Rewarded ad loaded successfully")
            }
        }
    }

    func showAd(onAdClosed: @escaping () -> Void) {
        self.onAdClosed = onAdClosed

        if let rewardedAd = rewardedAd {
            rewardedAd.fullScreenContentDelegate = self
            rewardedAd.present(fromRootViewController: nil) {
                // Логика для награды (если нужно)
            }
        } else {
            print("Ad is not ready yet.")
            onAdClosed()
        }
    }
}

extension InterstitialAdManager: GADFullScreenContentDelegate {


    func adDidDismissFullScreenContent(_ ad: GADFullScreenPresentingAd) {
        print("Ad was dismissed")
        onAdClosed?()
       
    }

    func ad(_ ad: GADFullScreenPresentingAd, didFailToPresentFullScreenContentWithError error: Error) {
        print("Failed to show ad: \(error.localizedDescription)")
        rewardedAd = nil
    }

}

func getAdMob(context: ComposeView.Context) -> AdvertisingAdMobIos {
    let bannerController = UIHostingController(rootView: BannerAdView().frame(width: 320, height: 50).background(Color.gray.opacity(0)))
    return AdvertisingAdMobIos(
        bannerViewController: { () -> UIViewController in
            return bannerController
        },
        loadRewardedAd: {
            Task { [weak manager = context.coordinator] in
                manager?.loadAd() // Загружаем рекламу через менеджер
            }
        },
        showRewardedAd: { onAdCloded in
            context.coordinator.showAd() {
                onAdCloded()
            }
        }
    )
}




