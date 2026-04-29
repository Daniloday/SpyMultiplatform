//
//  AppDelegate.swift
//  iosApp
//

import UIKit

public final class AppDelegate: UIResponder, UIApplicationDelegate {
    public func application(
        _ application: UIApplication,
        didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?
    ) -> Bool {
        initializeAdMobSdk()
        initializeAppLovinSdk()
        return true
    }
}
