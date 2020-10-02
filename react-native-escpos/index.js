import { NativeModules } from 'react-native';

import { exchange_text } from './src/exchange_text';

var RNUSBPrinter = NativeModules.RNUSBPrinter;
var RNBLEPrinter = NativeModules.RNBLEPrinter;
var RNNetPrinter = NativeModules.RNNetPrinter;

var textTo64Buffer = data => {
  let options = {
    beep: false,
    cut: false,
    tailingLine: false,
    encoding: 'utf8'
  };
  const buffer = exchange_text(data, options);

  return buffer.toString('base64');
};

var billTo64Buffer = data => {
  let options = {
    beep: true,
    cut: true,
    encoding: 'GBK',
    tailingLine: true
  };
  const buffer = exchange_text(data, options);
  return buffer.toString('base64');
};

export const USBPrinter = {
  init: () =>
    new Promise((resolve, reject) =>
      RNUSBPrinter.init(() => resolve(), error => reject(error))
    ),

  getDeviceList: () =>
    new Promise((resolve, reject) =>
      RNUSBPrinter.getDeviceList(
        printers => resolve(printers),
        error => reject(error)
      )
    ),

  printImage: (base64, options) =>
  new Promise((resolve, reject) =>
    RNUSBPrinter.printPic(base64, options, (result) => resolve(result), error => console.warn(error))),

  connectPrinter: (vendorId, productId) =>
    new Promise((resolve, reject) =>
      RNUSBPrinter.connectPrinter(
        vendorId,
        productId,
        printer => resolve(printer),
        error => reject(error)
      )
    ),

  closeConn: () =>
    new Promise((resolve, reject) => {
      RNUSBPrinter.closeConn();
      resolve();
    }),

  printText: data =>
    RNUSBPrinter.printRawData(data, error =>
      console.warn(error)
    ),

    

  printBill: data =>
    RNUSBPrinter.printRawData(billTo64Buffer(data), error =>
      console.warn(error)
    )
};

export const BLEPrinter = {
  init: () =>
    new Promise((resolve, reject) =>
      RNBLEPrinter.init(() => resolve(), error => reject(error))
    ),

  getDeviceList: () =>
    new Promise((resolve, reject) =>
      RNBLEPrinter.getDeviceList(
        printers => resolve(printers),
        error => reject(error)
      )
    ),

  connectPrinter: inner_mac_address =>
    new Promise((resolve, reject) =>
      RNBLEPrinter.connectPrinter(
        inner_mac_address,
        printer => resolve(printer),
        error => reject(error)
      )
    ),

  closeConn: () =>
    new Promise((resolve, reject) => {
      RNBLEPrinter.closeConn();
      resolve();
    }),

  printText: text =>
    RNBLEPrinter.printRawData(textTo64Buffer(text), error =>
      console.warn(error)
    ),

  printBill: text =>
    RNBLEPrinter.printRawData(billTo64Buffer(text), error =>
      console.warn(error)
    )
};

export const NetPrinter = {
  init: () =>
    new Promise((resolve, reject) =>
      RNNetPrinter.init(() => resolve(), error => reject(error))
    ),

  connectPrinter: (host, port) =>
    new Promise((resolve, reject) =>
      RNNetPrinter.connectPrinter(
        host,
        port,
        printer => resolve(printer),
        error => reject(error)
      )
    ),

  closeConn: () =>
    new Promise((resolve, reject) => {
      RNNetPrinter.closeConn();
      resolve();
    }),

  printText: text =>
    RNNetPrinter.printRawData(textTo64Buffer(text), error =>
      console.warn(error)
    ),

  printBill: text =>
    RNNetPrinter.printRawData(billTo64Buffer(text), error =>
      console.warn(error)
    )
};
