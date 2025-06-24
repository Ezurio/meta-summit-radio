SUMMARY = "NX61x Firmware SDIO-UART"
DESCRIPTION = "NX61x Firmware for SDIO-UART modules, including 1216, 2230, SIP and 1218 variants."

DESCRIPTION:${PN}-1216-btattach = " \
    NX61x SDIO-UART firmware for 1216, 2230, SIP modules with \
    both Wi-Fi and Bluetooth firmwares loaded through Wi-Fi interface \
    by the Wi-FI driver. Bluetooth support is initiated by launching \
    btattach utility. \
    "

DESCRIPTION:${PN}-1218-btattach = " \
    NX61x SDIO-UART firmware for 1218 modules with \
    both Wi-Fi and Bluetooth firmwares loaded through Wi-Fi interface. \
    by the Wi-FI driver. Bluetooth support is initiated by launching \
    btattach utility. \
    "

DESCRIPTION:${PN}-1216-serdev = " \
    NX61x SDIO-UART firmware for 1216, 2230, SIP modules with \
    Wi-Fi and Bluetooth firmwares loaded independently by appropriate \
    interface drivers. Bluetooth support initiated by configuring serdev \
    on serial port in device tree. \
    "

DESCRIPTION:${PN}-1218-serdev = " \
    NX61x SDIO-UART firmware for 1218 modules with \
    Wi-Fi and Bluetooth firmwares loaded independently by appropriate \
    interface drivers. Bluetooth support initiated by configuring serdev \
    on serial port in device tree. \
    "

LICENSE += "& NXP2"
NO_GENERIC_LICENSE[NXP2] = "LICENSE.nxp2"
LIC_FILES_CHKSUM += "file://LICENSE.nxp2;md5=7b112d07b0616149941639c05f68f431"

require radio-firmware.inc
require radio-stack-version.inc

SRC_URI += "\
    ${SUMMIT_URI}/summit-nx61x-1218-firmware-${PV}.tar.bz2;name=nx61x-1218-firmware \
    "

do_install:append() {
    install -d  "${D}${sysconfdir}/modprobe.d"
    echo "options moal mod_para=nxp/wifi_prod_params.conf" > "${D}${sysconfdir}/modprobe.d/moal-btattach.conf"
    echo "options moal mod_para=nxp/wifi_prod_serdev_params.conf" > "${D}${sysconfdir}/modprobe.d/moal-serdev.conf"
}

# Internal support packages
PACKAGES =+ " \
    ${PN}-firmware-btattach \
    ${PN}-firmware-serdev \
    ${PN}-1216-power-tables \
    ${PN}-1218-power-tables \
    "

FILES:${PN}-firmware-btattach = " \
    ${nonarch_base_libdir}/firmware/nxp/sduart_* \
    ${nonarch_base_libdir}/firmware/nxp/wifi_prod_params.conf \
    ${sysconfdir}/modprobe.d/moal-btattach.conf \
    "

FILES:${PN}-firmware-serdev = " \
    ${nonarch_base_libdir}/firmware/nxp/sd_* \
    ${nonarch_base_libdir}/firmware/nxp/uart* \
    ${nonarch_base_libdir}/firmware/nxp/wifi_prod_serdev_params.conf \
    ${sysconfdir}/modprobe.d/moal-serdev.conf \
    "

FILES:${PN} = ""

FILES:${PN}-1216-power-tables = "${nonarch_base_libdir}/firmware/nxp/rgpower*"
FILES:${PN}-1218-power-tables = "${nonarch_base_libdir}/firmware/nxp/1218_rgpower*"

# User installed packages
PACKAGES =+ " \
    ${PN}-1216-btattach \
    ${PN}-1218-btattach \
    ${PN}-1216-serdev \
    ${PN}-1218-serdev \
    "

ALLOW_EMPTY:${PN}-1216-btattach = "1"
ALLOW_EMPTY:${PN}-1218-btattach = "1"
ALLOW_EMPTY:${PN}-1216-serdev = "1"
ALLOW_EMPTY:${PN}-1218-serdev = "1"

RDEPENDS:${PN}-1216-btattach = "${PN}-firmware-btattach ${PN}-1216-power-tables"
RDEPENDS:${PN}-1218-btattach = "${PN}-firmware-btattach ${PN}-1218-power-tables"
RDEPENDS:${PN}-1216-serdev = "${PN}-firmware-serdev ${PN}-1216-power-tables"
RDEPENDS:${PN}-1218-serdev = "${PN}-firmware-serdev ${PN}-1218-power-tables"

RCONFLICTS:${PN}-1216-btattach = "${PN}-1216-serdev ${PN}-1218-serdev"
RCONFLICTS:${PN}-1218-btattach = "${PN}-1216-serdev ${PN}-1218-serdev"
RCONFLICTS:${PN}-1216-serdev = "${PN}-1216-btattach ${PN}-1218-btattach"
RCONFLICTS:${PN}-1218-serdev = "${PN}-1216-btattach ${PN}-1218-btattach"
