//
//  Admob.swift
//  iosApp
//
//  Created by Danylo Vladyka on 11.10.2024.
//

import Foundation
import UIKit
import SwiftUI
import GoogleMobileAds
import ComposeApp

// MARK: - AppDelegate

public final class AppDelegate: UIResponder, UIApplicationDelegate {
    public func application(
        _ application: UIApplication,
        didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?
    ) -> Bool {
        MobileAds.shared.start(completionHandler: nil)
        return true
    }
}

// MARK: - Helpers

private enum AdMobIds {
    static let banner = "ca-app-pub-6281392964919353/3902289086"
    static let interstitial = "ca-app-pub-6281392964919353/1291168690"
    static let rewarded = "ca-app-pub-6281392964919353/5038842012"
}

private func topMostViewController() -> UIViewController? {
    guard
        let scene = UIApplication.shared.connectedScenes
            .compactMap({ $0 as? UIWindowScene })
            .first(where: { $0.activationState == .foregroundActive }),
        let root = scene.windows.first(where: { $0.isKeyWindow })?.rootViewController
    else { return nil }

    var top = root
    while let presented = top.presentedViewController {
        top = presented
    }
    if let nav = top as? UINavigationController { return nav.visibleViewController ?? nav }
    if let tab = top as? UITabBarController { return tab.selectedViewController ?? tab }
    return top
}

// MARK: - Banner

private struct BannerAdView: UIViewRepresentable {
    func makeUIView(context: Context) -> BannerView {
        let view = BannerView(adSize: AdSizeBanner)
        view.adUnitID = AdMobIds.banner
        return view
    }

    func updateUIView(_ uiView: BannerView, context: Context) {
        if uiView.rootViewController == nil, let vc = topMostViewController() {
            uiView.rootViewController = vc
            uiView.load(Request())
        }
    }
}

// MARK: - Interstitial

final class InterstitialAdManager: NSObject, FullScreenContentDelegate {
    private var ad: InterstitialAd?
    private var onClosed: (() -> Void)?

    func load() {
        InterstitialAd.load(with: AdMobIds.interstitial, request: Request()) { [weak self] ad, error in
            if let error {
                print("Failed to load interstitial: \(error.localizedDescription)")
                self?.ad = nil
                return
            }
            self?.ad = ad
            self?.ad?.fullScreenContentDelegate = self
            print("Interstitial loaded")
        }
    }

    func show(onClosed: @escaping () -> Void) {
        self.onClosed = onClosed

        guard let ad else {
            print("Interstitial not ready")
            onClosed()
            return
        }
        guard let vc = topMostViewController() else {
            print("No root view controller to present interstitial")
            onClosed()
            return
        }
        ad.present(from: vc)
    }

    func adDidDismissFullScreenContent(_ ad: FullScreenPresentingAd) {
        print("Interstitial dismissed")
        self.ad = nil
        onClosed?()
        onClosed = nil
        load() // optional: preload next
    }

    func ad(_ ad: FullScreenPresentingAd, didFailToPresentFullScreenContentWithError error: Error) {
        print("Interstitial failed to present: \(error.localizedDescription)")
        self.ad = nil
        onClosed?()
        onClosed = nil
    }
}

// MARK: - Rewarded

final class RewardedAdManager: NSObject, FullScreenContentDelegate {
    private var ad: RewardedAd?
    private var onClosed: (() -> Void)?
    private var onReward: ((AdReward) -> Void)?

    func load() {
        RewardedAd.load(with: AdMobIds.rewarded, request: Request()) { [weak self] ad, error in
            if let error {
                print("Failed to load rewarded: \(error.localizedDescription)")
                self?.ad = nil
                return
            }
            self?.ad = ad
            self?.ad?.fullScreenContentDelegate = self
            print("Rewarded loaded")
        }
    }

    func show(onReward: @escaping (AdReward) -> Void, onClosed: @escaping () -> Void) {
        self.onReward = onReward
        self.onClosed = onClosed

        guard let ad else {
            print("Rewarded not ready")
            onClosed()
            return
        }
        guard let vc = topMostViewController() else {
            print("No root view controller to present rewarded")
            onClosed()
            return
        }

        ad.present(from: vc) { [weak self] in
            guard let reward = self?.ad?.adReward else { return }
            self?.onReward?(reward)
        }
    }

    func adDidDismissFullScreenContent(_ ad: FullScreenPresentingAd) {
        print("Rewarded dismissed")
        self.ad = nil
        onClosed?()
        onClosed = nil
        onReward = nil
        load() // optional: preload next
    }

    func ad(_ ad: FullScreenPresentingAd, didFailToPresentFullScreenContentWithError error: Error) {
        print("Rewarded failed to present: \(error.localizedDescription)")
        self.ad = nil
        onClosed?()
        onClosed = nil
        onReward = nil
    }
    
    func adWillPresentFullScreenContent(_ ad: FullScreenPresentingAd) {
        print("Rewarded will present")
    }

    func adDidRecordImpression(_ ad: FullScreenPresentingAd) {
        print("Rewarded impression")
    }

    func adDidRecordClick(_ ad: FullScreenPresentingAd) {
        print("Rewarded click")
    }
}

// MARK: - KMP bridge

func getAdMob(context: ComposeView.Context) -> AdvertisingAdMobIos {
    let bannerController = UIHostingController(
        rootView: BannerAdView()
            .frame(width: 320, height: 50)
            .background(Color.clear)
    )


    return AdvertisingAdMobIos(
        bannerViewController: { bannerController },

        loadInterstitialAd: { context.coordinator.interstitial.load() },
        showInterstitialAd: { onAdClosed in
            context.coordinator.interstitial.show {
                _ = onAdClosed()
            }
        },

        loadRewardedAd: { context.coordinator.rewarded.load() },
        showRewardedAd: { onAdClosed, onReward in
            context.coordinator.rewarded.show(
                onReward: { _ in _ = onReward() },
                onClosed: { _ = onAdClosed() }
            )
        }
    )
}
