import { NativeModules } from 'react-native';

const RNPaymentToken = NativeModules.RNPaymentToken

const generatePaymentToken = async (card) => {
    await RNPaymentToken.generate(card);
    return saveCard;
  };

  export {
      generatePaymentToken
  }