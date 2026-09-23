SUMMARY = "MT320 SDIO Firmware (MediaTek MT7961 chipset, WiFi+Bluetooth)"

require radio-firmware.inc
require radio-stack-version.inc

do_install:append() {
	install -d "${D}${nonarch_base_libdir}/firmware"
	install -m 0644 \
		"${D}${nonarch_base_libdir}/firmware/mediatek/BT_RAM_CODE_MT7961_1_2_hdr.bin" \
		"${D}${nonarch_base_libdir}/firmware/"
}
