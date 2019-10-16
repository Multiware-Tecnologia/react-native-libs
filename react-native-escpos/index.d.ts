export interface OptionsPrintImage {
  width: number;
}

export interface USBPrinterProps {
  init: () => Promise<void>;
  getDeviceList: () => Promise<{ vendorID: number; productID: number }>;
  connectPrinter: (vendorID: number, productID: number) => Promise<void>;
  printImage: (base64: string, options?: OptionsPrintImage) => Promise<boolean>;
}

declare const USBPrinter: USBPrinterProps;

export { USBPrinter };
