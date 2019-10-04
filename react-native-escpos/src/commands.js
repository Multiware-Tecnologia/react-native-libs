const Buffer = require('buffer').Buffer;

/**
 * Initialize Printer
 */
export const init_printer = Buffer.from([27, 64]);

// #region QRCode

/**
 * Specify encoding scheme of QRCode barcode
 * 29, 40, 107, 3, 0, 49, 65, n
 *
 * ```
 * n: 0 => QRCode
 * ```
 * ```
 * n: 1 => MicroQr
 * ```
 */
export const qr_model = Buffer.from([29, 40, 107, 3, 0, 49, 65, 0]);

/**
 * Specify dot size of the module of the QRcode barcode
 * 29, 40, 107, 3, 0, 49, 67, n
 * ```
 * 2 <= n <= 24
 * ```
 */
export const qr_module_size = Buffer.from([29, 40, 107, 3, 0, 49, 67, 20]);

/**
 * Specify QRcode barcode size
 * 29, 40, 107, 3, 0, 49, 67, n
 * ```
 * 0 <= n <= 40
 * ```
 */
export const qr_cell_size = Buffer.from([29, 40, 107, 3, 0, 49, 67, 0]);

/**
 * Specify the error correction level of the QRcode barcode
 * 29, 40, 107, 3, 0, 49, 69, n
 * ```
 * 0 <= n <= 4
 * ```
 */
export const qr_correction = Buffer.from([29, 40, 107, 3, 0, 49, 69, 0]);

/**
 * Prints the QRcode barcode data
 */
export const qr_start = Buffer.from([29, 40, 107, 3, 0, 49, 81, 49]);

// #endregion

// #region Rotate

export const enable_90 = Buffer.from([27, 86, 1]);
export const disable_90 = Buffer.from([27, 86, 0]);

// #endregion

// #region BarCode

/**
 * Set barcode height
 * 29, 104, n
 * ```
 * 1 <= n <= 255
 * ```
 */
export const barcode_height = Buffer.from([29, 104, 100]);

/**
 * Print barcode
 * @Format1
 * 29, 107, m, 0, [d1...dk]
 * ```
 * 0<=m <= 8 || 20
 *
 * ```
 * default `20`
 *
 * @Format2
 * 29, 107, m, n, [d1...dk]
 * ```
 * 65 <= m <=73 || 90
 * ```
 * default `90`
 * @n  Number of Characters
 */
export const barcode_print = Buffer.from([29, 107, 72]);
// #endregion

// #region Image

/**
 * Select image print mode
 * 27, 42, m, nL, nH, [d1...dk]
 * ```
 * m = 0 || 1 || 32 || 33
 * 0 <= nL <= 255
 * 0 <= nH <= 3
 *```
 */
export const select_image_mode = Buffer.from([27, 42, 33, 100, 2]);

/**
 * Print raster image
 * 29, 118, 48 m, xL, xH, yL, yH, [d1...dk]
 * ```
 * 0 <= m <= 3, 48<=m<=51
 * 0<=xL<=255
 * 0 <= xH <= 255 (1 <= xL + xH * 256 <= 65535)
 * 0 <= yL <= 255
 * 0 <= yH <= 8 (1 <= yL + yH * 256 <= 2047)
 * 0 <= d <= 255
 * k = (xL + xH <= 256) + (yL + yH <= 256)
 * ```
 */
export const print_image = Buffer.from([29, 117, 48, 0, 100, 100, 100, 5]);

// #endregion

// #region Print Graphic
/**
 * Receive graphic page from communication port
 * 27, 253, nL, nH
 * ```
 * 0 <= nL, nH <= 255
 * ```
 * Receives [nL + (nH × 256)] words from the port and puts them into the ram bank.
 */
export const receive_graphic = Buffer.from([27, 253, 200, 200]);

/**
 * Print graphic (640x409)
 * 27, 250, n, xH, xL, yH, yL
 * ```
 * 0<= n <= 1
 * 0<=xH, xL, yH, yL <= 255
 * ```
 */
export const print_graphic = Buffer.from([27, 250, 1, 1, 1, 1]);

// #endregion

// #region Mechanism Control

/**
 * Total Cut
 * @description This command enables cutter operation. If there is no cutter, a disabling lag is set and any subse- quent cut commands will be ignored.
 * @notes The printer waits to complete all paper movement commands before it executes a total cut.
 */
export const total_cut = Buffer.from([27, 105]);

/**
 * Partial cut
 * @description This command enables partial cutter operation.
 * @notes The printer waits to complete all paper movement commands before it executes a partial cut.
 */
export const partial_cut = Buffer.from([27, 109]);

// #endregion

// #region Line Spacing Commands
/**
 * Select 1/6-inch line spacing
 * @description Selects 1/6-inch line spacing.
 * @reference 0x1B 0x30, 0x1B 0x33
 */
export const spacing_1_6_inch = Buffer.from([27, 50]);

// #endregion
