SUMMARY = "Summit 60 Firmware"
DESCRIPTION = "Firmware for Summit/Sterling 60 Series Radio"

DESCRIPTION:${PN}-sdio-uart = "${DESCRIPTION} SDIO UART"
DESCRIPTION:${PN}-sdio-sdio = "${DESCRIPTION} SDIO SDIO"
DESCRIPTION:${PN}-pcie-uart = "${DESCRIPTION} PCIe UART"
DESCRIPTION:${PN}-pcie-usb = "${DESCRIPTION} PCIe USB"
DESCRIPTION:${PN}-usb-uart = "${DESCRIPTION} USB UART"
DESCRIPTION:${PN}-usb-usb = "${DESCRIPTION} USB USB"

require radio-firmware.inc
require radio-stack-version.inc

LICENSE += "& NXP1"
NO_GENERIC_LICENSE[NXP1] = "LICENSE.nxp1"
LIC_FILES_CHKSUM += "file://LICENSE.nxp1;md5=933884af53a2d9e6d73e4e3065819f40"

inherit update-alternatives

SRC_URI = "\
    ${SUMMIT_URI}/summit-${BPN}-sdio-uart-${PV}.tar.bz2;name=${BPN}-sdio-uart \
    ${SUMMIT_URI}/summit-${BPN}-sdio-sdio-${PV}.tar.bz2;name=${BPN}-sdio-sdio \
    ${SUMMIT_URI}/summit-${BPN}-pcie-uart-${PV}.tar.bz2;name=${BPN}-pcie-uart \
    ${SUMMIT_URI}/summit-${BPN}-pcie-usb-${PV}.tar.bz2;name=${BPN}-pcie-usb \
    ${SUMMIT_URI}/summit-${BPN}-usb-uart-${PV}.tar.bz2;name=${BPN}-usb-uart \
    ${SUMMIT_URI}/summit-${BPN}-usb-usb-${PV}.tar.bz2;name=${BPN}-usb-usb \
    "

PACKAGES =+ " \
    ${PN}-power-tables \
    ${PN}-sdio-uart \
    ${PN}-sdio-sdio \
    ${PN}-pcie-uart \
    ${PN}-pcie-usb \
    ${PN}-usb-uart \
    ${PN}-usb-usb \
    "

do_install:append() {
    rm -f \
        "${D}${nonarch_base_libdir}/firmware/lrdmwl/88W8997_sdio.bin" \
        "${D}${nonarch_base_libdir}/firmware/lrdmwl/88W8997_pcie.bin" \
        "${D}${nonarch_base_libdir}/firmware/lrdmwl/88W8997_usb.bin"
}

FILES:${PN} = ""

FILES:${PN}-power-tables = "\
    ${nonarch_base_libdir}/firmware/lrdmwl/regpwr* \
    ${nonarch_base_libdir}/firmware/regulatory* \
    "

FILES:${PN}-sdio-uart = "\
    ${nonarch_base_libdir}/firmware/lrdmwl/88W8997_60_sdio_uart_* \
    ${nonarch_base_libdir}/firmware/lrdmwl/88W8997_sdio_uart.bin \
    "

FILES:${PN}-sdio-sdio = "\
    ${nonarch_base_libdir}/firmware/lrdmwl/88W8997_60_sdio_sdio_* \
    ${nonarch_base_libdir}/firmware/lrdmwl/88W8997_sdio_sdio.bin \
    "

FILES:${PN}-pcie-uart = "\
    ${nonarch_base_libdir}/firmware/lrdmwl/88W8997_60_pcie_uart_* \
    ${nonarch_base_libdir}/firmware/lrdmwl/88W8997_pcie_uart.bin \
    "
FILES:${PN}-pcie-usb = "\
    ${nonarch_base_libdir}/firmware/lrdmwl/88W8997_60_pcie_usb_* \
    ${nonarch_base_libdir}/firmware/lrdmwl/88W8997_pcie_usb.bin \
    "

FILES:${PN}-usb-uart = "\
    ${nonarch_base_libdir}/firmware/lrdmwl/88W8997_60_usb_uart_* \
    ${nonarch_base_libdir}/firmware/lrdmwl/88W8997_usb_uart.bin \
    "

FILES:${PN}-usb-usb = "\
    ${nonarch_base_libdir}/firmware/lrdmwl/88W8997_60_usb_usb_* \
    ${nonarch_base_libdir}/firmware/lrdmwl/88W8997_usb_usb.bin \
    "

ALTERNATIVE:${PN}-sdio-uart = "60-sdio"
ALTERNATIVE:${PN}-sdio-sdio = "60-sdio"
ALTERNATIVE:${PN}-pcie-uart = "60-pcie"
ALTERNATIVE:${PN}-pcie-usb = "60-pcie"
ALTERNATIVE:${PN}-usb-usb = "60-usb"
ALTERNATIVE:${PN}-usb-uart = "60-usb"

ALTERNATIVE_LINK_NAME[60-sdio] = "${nonarch_base_libdir}/firmware/lrdmwl/88W8997_sdio.bin"
ALTERNATIVE_LINK_NAME[60-pcie] = "${nonarch_base_libdir}/firmware/lrdmwl/88W8997_pcie.bin"
ALTERNATIVE_LINK_NAME[60-usb] = "${nonarch_base_libdir}/firmware/lrdmwl/88W8997_usb.bin"

ALTERNATIVE_PRIORITY_60-radio-firmware-sdio-uart[60-sdio] = "100"
ALTERNATIVE_PRIORITY_60-radio-firmware-sdio-sdio[60-sdio] = "90"
ALTERNATIVE_PRIORITY_60-radio-firmware-pcie-uart[60-pcie] = "100"
ALTERNATIVE_PRIORITY_60-radio-firmware-pcie-usb[60-pcie] = "90"
ALTERNATIVE_PRIORITY_60-radio-firmware-usb-usb[60-usb] = "100"
ALTERNATIVE_PRIORITY_60-radio-firmware-usb-uart[60-usb] = "90"

ALTERNATIVE_TARGET_60-radio-firmware-sdio-uart[60-sdio] = "${nonarch_base_libdir}/firmware/lrdmwl/88W8997_sdio_uart.bin"
ALTERNATIVE_TARGET_60-radio-firmware-sdio-sdio[60-sdio] = "${nonarch_base_libdir}/firmware/lrdmwl/88W8997_sdio_sdio.bin"
ALTERNATIVE_TARGET_60-radio-firmware-pcie-uart[60-pcie] = "${nonarch_base_libdir}/firmware/lrdmwl/88W8997_pcie_uart.bin"
ALTERNATIVE_TARGET_60-radio-firmware-pcie-usb[60-pcie] = "${nonarch_base_libdir}/firmware/lrdmwl/88W8997_pcie_usb.bin"
ALTERNATIVE_TARGET_60-radio-firmware-usb-usb[60-usb] = "${nonarch_base_libdir}/firmware/lrdmwl/88W8997_usb_usb.bin"
ALTERNATIVE_TARGET_60-radio-firmware-usb-uart[60-usb] = "${nonarch_base_libdir}/firmware/lrdmwl/88W8997_usb_uart.bin"

RDEPENDS:${PN}-sdio-uart = "${PN}-power-tables"
RDEPENDS:${PN}-sdio-sdio = "${PN}-power-tables"
RDEPENDS:${PN}-pcie-uart = "${PN}-power-tables"
RDEPENDS:${PN}-pcie-usb = "${PN}-power-tables"
RDEPENDS:${PN}-usb-usb = "${PN}-power-tables"
RDEPENDS:${PN}-usb-uart = "${PN}-power-tables"

RPROVIDES:${PN}-power-tables  += "wireless-regdb-static"
RREPLACES:${PN}-power-tables  += "wireless-regdb-static"
RCONFLICTS:${PN}-power-tables += "wireless-regdb-static"
