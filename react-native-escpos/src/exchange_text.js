import * as commands from './commands';

const BufferHelper = require('./buffer-helper');
const iconv = require('iconv-lite');

const Buffer = require('buffer').Buffer;
export const exchange_text = (data, options) => {
  const { text, textOptions } = data;

  options = options || {
    beep: false,
    cut: true,
    tailingLine: true,
    encoding: 'UTF8'
  };
  var init_printer_bytes = new Buffer([27, 64]);
  var check_state_bytes = new Buffer([29, 153]);
  var init_bytes = new Buffer([27, 33, 0]);
  var c_start_bytes = new Buffer([27, 97, 1]);
  var c_end_bytes = new Buffer([]); // [ 27, 97, 0 ];
  var reset_bytes = new Buffer([27, 97, 0, 29, 33, 0, 27, 50]);

  var m_start_bytes = new Buffer([27, 33, 16, 28, 33, 8]);
  var m_end_bytes = new Buffer([27, 33, 0, 28, 33, 0]);
  var b_start_bytes = new Buffer([27, 33, 48, 28, 33, 12]);
  var b_end_bytes = new Buffer([27, 33, 0, 28, 33, 0]);
  var cm_start_bytes = new Buffer([27, 97, 1, 27, 33, 16, 28, 33, 8]);
  var cm_end_bytes = new Buffer([27, 33, 0, 28, 33, 0]);
  var cb_start_bytes = new Buffer([27, 97, 1, 27, 33, 48, 28, 33, 12]);
  var cb_end_bytes = new Buffer([27, 33, 0, 28, 33, 0]);
  var cd_start_bytes = new Buffer([27, 97, 1, 27, 33, 32, 28, 33, 4]);
  var cd_end_bytes = new Buffer([27, 33, 0, 28, 33, 0]);
  var d_start_bytes = new Buffer([27, 33, 32, 28, 33, 4]);
  var d_end_bytes = new Buffer([27, 33, 0, 28, 33, 0]);

  var align_center_bytes = new Buffer([27, 97, 1]);
  var align_left_bytes = new Buffer([27, 97, 0]);

  var default_space_bytes = new Buffer([27, 50]);
  var none_space_bytes = new Buffer([27, 51, 0]);
  var b_space_bytes = new Buffer([27, 51, 120]);

  var cut_bytes = new Buffer([27, 105]);

  var moneybox_bytes = new Buffer([27, 112, 7]);

  var beep_bytes = new Buffer([27, 66, 3, 2]);

  var bytes = new BufferHelper();
  bytes.concat(commands.init_printer);

  if (textOptions.image) {
    var url = text;

    console.log(textOptions);
    const { width, height } = textOptions;
    const length = url.length;
    console.log(length);
    const yH = Math.floor(length / 255, 0);
    const xH = length - yH * 255;
    let xL = width - xH * 255;
    let yL = height - yH * 255;

    if (xL > 254) {
      xL = 0;
    }
    if (yL > 254) {
      yL = 0;
    }
    console.log(yH);
    console.log(xH);
    console.log(xL);
    console.log(yL);

    console.log(change_image_url_to_bytes(url));

    bytes.concat(align_center_bytes);
    bytes.concat(none_space_bytes);
    // bytes.concat(change_image_url_to_bytes(url));
    bytes.concat(default_space_bytes);
    bytes.concat(align_left_bytes);
  }

  var line_bytes = new Buffer([10, 10, 10, 10, 10]);
  if (options.tailingLine) {
    bytes.concat(line_bytes);
  }
  if (options.cut) {
    bytes.concat(cut_bytes);
  }
  if (options.beep) {
    bytes.concat(beep_bytes);
  }
  return bytes.toBuffer();
};

var change_image_url_to_bytes = (exports.change_image_url_to_bytes = function change_image_url_to_bytes(
  url,
  options
) {
  options = options || {
    encoding: 'UTF8'
  };
  var buffer = new Buffer(iconv.encode(url, options.encoding));

  return buffer;
});
