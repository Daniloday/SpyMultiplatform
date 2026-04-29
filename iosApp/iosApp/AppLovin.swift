//
//  AppLovin.swift
//  iosApp
//

import UIKit
import SwiftUI
import AppLovinSDK
import ComposeApp

// MARK: - Helpers

private enum AppLovinIds {
    static let sdkKey = "x_TchekDbUE0hxA9LZG5yT3c7e0Te4D0Sy50yBpgfrwVOwyebMqEySqgcEweMrtEIxi8BTNHF_RQo92LK5H_T6"
    static let banner = "6016857d4f3fd881"
    static let interstitial = "79ecbb30e7c27259"
    static let rewarded = "3ff0e7155daf6728"
}

func initializeAppLovinSdk() {
    let initConfig = ALSdkInitializationConfiguration(sdkKey: AppLovinIds.sdkKey) { builder in
        builder.mediationProvider = ALMediationProviderMAX
    }
    ALSdk.shared().initialize(with: initConfig) { _ in }
}

// MARK: - Banner

private struct AppLovinBannerAdView: UIViewRepresentable {
    func makeUIView(context: Context) -> MAAdView {
        let view = MAAdView(adUnitIdentifier: AppLovinIds.banner)
        view.backgroundColor = .clear
        view.loadAd()
        return view
    }

    func updateUIView(_ uiView: MAAdView, context: Context) {}
}

// MARK: - Interstitial

final class AppLovinInterstitialAdManager: NSObject, MAAdDelegate {
    private let ad = MAInterstitialAd(adUnitIdentifier: AppLovinIds.interstitial)
    private var onClosed: (() -> Void)?

    override init() {
        super.init()
        ad.delegate = self
    }

    func load() {
        ad.load()
    }

    func show(onClosed: @escaping () -> Void) {
        self.onClosed = onClosed

        guard ad.isReady else {
            print("AppLovin interstitial not ready")
            onClosed()
            load()
            return
        }

        ad.show()
    }

    func didLoad(_ ad: MAAd) {
        print("AppLovin interstitial loaded")
    }

    func didFailToLoadAd(forAdUnitIdentifier adUnitIdentifier: String, withError error: MAError) {
        print("Failed to load AppLovin interstitial: \(error)")
    }

    func didDisplay(_ ad: MAAd) {}

    func didClick(_ ad: MAAd) {}

    func didHide(_ ad: MAAd) {
        onClosed?()
        onClosed = nil
        load()
    }

    func didFail(toDisplay ad: MAAd, withError error: MAError) {
        print("AppLovin interstitial failed to present: \(error)")
        onClosed?()
        onClosed = nil
        load()
    }
}

// MARK: - Rewarded

final class AppLovinRewardedAdManager: NSObject, MARewardedAdDelegate {
    private let ad = MARewardedAd.shared(withAdUnitIdentifier: AppLovinIds.rewarded)
    private var onClosed: (() -> Void)?
    private var onReward: (() -> Void)?

    override init() {
        super.init()
        ad.delegate = self
    }

    func load() {
        ad.load()
    }

    func show(onReward: @escaping () -> Void, onClosed: @escaping () -> Void) {
        self.onReward = onReward
        self.onClosed = onClosed

        guard ad.isReady else {
            print("AppLovin rewarded not ready")
            onClosed()
            clearCallbacks()
            load()
            return
        }

        ad.show()
    }

    func didLoad(_ ad: MAAd) {
        print("AppLovin rewarded loaded")
    }

    func didFailToLoadAd(forAdUnitIdentifier adUnitIdentifier: String, withError error: MAError) {
        print("Failed to load AppLovin rewarded: \(error)")
    }

    func didDisplay(_ ad: MAAd) {}

    func didClick(_ ad: MAAd) {}

    func didHide(_ ad: MAAd) {
        onClosed?()
        clearCallbacks()
        load()
    }

    func didFail(toDisplay ad: MAAd, withError error: MAError) {
        print("AppLovin rewarded failed to present: \(error)")
        onClosed?()
        clearCallbacks()
        load()
    }

    func didRewardUser(for ad: MAAd, with reward: MAReward) {
        onReward?()
    }

    private func clearCallbacks() {
        onClosed = nil
        onReward = nil
    }
}

// MARK: - KMP bridge

func getAppLovin(context: ComposeView.Context) -> AdvertisingAppLovinIos {
    let bannerController = UIHostingController(
        rootView: AppLovinBannerAdView()
            .frame(width: UIScreen.main.bounds.width, height: 50)
            .background(Color.clear)
    )

    return AdvertisingAppLovinIos(
        bannerViewController: { bannerController },

        loadInterstitialAd: { context.coordinator.appLovinInterstitial.load() },
        showInterstitialAd: { onAdClosed in
            context.coordinator.appLovinInterstitial.show {
                _ = onAdClosed()
            }
        },

        loadRewardedAd: { context.coordinator.appLovinRewarded.load() },
        showRewardedAd: { onAdClosed, onReward in
            context.coordinator.appLovinRewarded.show(
                onReward: { _ = onReward() },
                onClosed: { _ = onAdClosed() }
            )
        }
    )
}
