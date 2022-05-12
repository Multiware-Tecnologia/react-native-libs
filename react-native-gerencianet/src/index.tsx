import { NativeModules, Platform } from 'react-native';

const LINKING_ERROR =
  `The package 'react-native-gerencianet' doesn't seem to be linked. Make sure: \n\n` +
  Platform.select({ ios: "- You have run 'pod install'\n", default: '' }) +
  '- You rebuilt the app after installing the package\n' +
  '- You are not using Expo managed workflow\n';

  interface cardProps{
    brand: string;
    number: number;
    cvv: number;
    expiration_month: number;
    expiration_year: number;
    accountId: number;
    approvalMode: boolean;
  }


const Gerencianet = NativeModules.GerencianetModule
  ? NativeModules.Gerencianet
  : new Proxy(
      {},
      {
        get() {
          throw new Error(LINKING_ERROR);
        },
      }
    );

export function generateHash(card: cardProps): Promise<number> {
  return Gerencianet.generateHash(card);
}
