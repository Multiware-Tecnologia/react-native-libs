Pod::Spec.new do |spec|

  spec.name         = "DirectCheckout"
  spec.version      = "0.0.2"
  spec.summary      = "SDK para criptografia e validação de dados do cartão de crédito para integração com a API de pagamentos da Juno/BoletoBancário."
  spec.homepage     = "http://www.juno.com.br"
  spec.author       = { "Diego Trevisan Lara" => "diego@juno.com.br" }
  spec.license      = "Apache"

  spec.ios.deployment_target = "10.3"
  spec.source       = { :git => "https://github.com/tamojuno/direct-checkout-ios.git", :tag => "#{spec.version}" }
  spec.source_files  = "DirectCheckout", "DirectCheckout/**/*.{h,m,swift}"
  spec.public_header_files = "DirectCheckout/**/*.h"
  spec.swift_version = "5.0"

end
