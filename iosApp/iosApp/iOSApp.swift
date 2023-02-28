import SwiftUI
import shared

@main
struct iOSApp: App {
    
    init() {
        KoiniOSKt.doInitKoin()
    }
    
	var body: some Scene {
		WindowGroup {
			ContentView()
		}
	}
}
