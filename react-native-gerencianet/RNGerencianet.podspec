Pod::Spec.new do |s|
    s.name         = "RNGerencianet"
    s.version      = "0.3.0"
    s.summary      = "A simple lib for easy integration of your mobile app with the payment services provided by Gerencianet."
    s.homepage     = "https://github.com/gerencianet/gn-api-sdk-ios"
    s.license      = { :type => "MIT", :file => "LICENSE" }
    s.author       = { "thomazfeitoza" => "thomazfeitoza@gmail.com" }
    s.source       = { :git => "https://github.com/gerencianet/gn-api-sdk-ios.git", :tag => "0.3.0" }
    s.source_files = "RNGerencianet", "RNGerencianet/*.{h,m}"
    s.requires_arc = true
    s.platform     = :ios, "7.0"
    s.dependency "AFNetworking", "~> 4.0"
    s.dependency "PromiseKit", "~> 1.5"
    s.dependency "RegexKitLite"
end
