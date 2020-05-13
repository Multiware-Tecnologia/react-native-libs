//
//  RNJuno.swift
//
//  Created by Mauro Moura on 14/04/20.
//  Copyright © 2020 Facebook. All rights reserved.
//

import DirectCheckout.DirectCheckout

@objc public class RNJuno: NSObject{
    
    private static var publicToken: String?
    
   @objc public func initializeJuno(token: String, production: Bool){
        if(production){
            DirectCheckout.initialize(publicToken: token);
        }else{
            DirectCheckout.initialize(publicToken: token, environment: .sandbox);
        }
    }
    
    @objc public func generateHash(cardNumber: String, holderName: String, securityCode: String, expirationMonth: String, expirationYear: String) -> String {
        var valor: String = "";
        let card = Card(cardNumber: cardNumber,
                        holderName: holderName,
                        securityCode: securityCode,
                        expirationMonth: expirationMonth,
                        expirationYear: expirationYear)
        
        DirectCheckout.getCardHash(card){ result in
            do {
                //
                let hash = try result.get();
                valor = hash;
                /* Sucesso - A variável hash conterá o hash do cartão de crédito */

            } catch let error {
                /* Erro - A variável error conterá o erro ocorrido ao obter o hash */
                valor = "erro";
            }
            
        }
        
        //Gamb para resposta assincrona
        do {
            sleep(3)
        }
        
        return valor;
    }
}
