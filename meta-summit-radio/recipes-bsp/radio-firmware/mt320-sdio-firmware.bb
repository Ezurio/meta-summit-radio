SUMMARY = "MT320 SDIO Firmware (MediaTek MT7961 chipset, WiFi+Bluetooth)"

LICENSE += "& MEDIATEK"
NO_GENERIC_LICENSE[MEDIATEK] = "LICENSE.mediatek"
LIC_FILES_CHKSUM += "file://LICENSE.mediatek;md5=7c1976b63217d76ce47d0a11d8a79cf2"

require radio-firmware.inc
require radio-stack-version.inc

RDEPENDS:${PN} += "${@'mt-regdomain' if d.getVar('MT_REGDOMAIN') else ''}"

do_install:append() {
	install -d "${D}${nonarch_base_libdir}/firmware"
	install -m 0644 \
		"${D}${nonarch_base_libdir}/firmware/mediatek/BT_RAM_CODE_MT7961_1_2_hdr.bin" \
		"${D}${nonarch_base_libdir}/firmware/"
}
