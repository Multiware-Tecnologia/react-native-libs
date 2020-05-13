//
//  react-native-juno
//  Created by Mauro Moura - Group ADBRAX on 08/04/20.
//

import { NativeModules, Platform } from 'react-native';

const RNJuno = Platform.OS === "android" ? NativeModules.RNJuno : NativeModules.RNJunoManager;

// token: String
// production: Boolean
const iniciarJuno = (token, production) => { RNJuno.initializeJuno(token, production) };

  // except the callback which is the return, the others are string 
  const gerarHashANDROID = async (cardNumber, holderName, securityCode, expirationMonth, expirationYear) => {
    let hash;
    await RNJuno.generateHash(cardNumber, holderName, securityCode, expirationMonth, expirationYear, (callback) => {
      hash = callback;
    });
    return hash;
  };

  // except the callback which is the return, the others are string 
   const gerarHashIOS = async (cardNumber, holderName, securityCode, expirationMonth, expirationYear) => {
    let hash;
    await RNJuno.generateHash(cardNumber, holderName, securityCode, expirationMonth, expirationYear, (error, events) => {
      if (error) {
        console.log('error', error)
      } else {
        hash = events;
      }
    });

    return hash;
  }

export {
  iniciarJuno,
  gerarHashANDROID,
  gerarHashIOS
};

