SUMMARY = "Summit firmware for the LWB5+ SDIO/UART modules"

SUMMARY_lwb5plus-sdio-div-firmware = "Summit firmware for the LWB5+ SDIO/UART module with diversity antenna"
SUMMARY_lwb5plus-sdio-sa-firmware = "Summit firmware for the LWB5+ SDIO/UART module with single antenna"
SUMMARY_lwb5plus-sdio-sa-m2-firmware = "Summit firmware for the LWB5+ SDIO/UART module with single antenna M.2"
SUMMARY_lwb5plus-usb-div-firmware = "Summit firmware for the LWB5+ USB module with diversity antenna"
SUMMARY_lwb5plus-usb-sa-firmware = "Summit firmware for the LWB5+ USB module with single antenna"
SUMMARY_lwb5plus-usb-sa-m2-firmware = "Summit firmware for the LWB5+ USB module with single antenna M.2"

require radio-firmware-lwb.inc
inherit update-alternatives

SRC_URI = "\
    ${SUMMIT_URI}/summit-lwb5plus-sdio-div-firmware-${PV}.tar.bz2;name=lwb5plus-sdio-div-firmware  \
    ${SUMMIT_URI}/summit-lwb5plus-sdio-sa-firmware-${PV}.tar.bz2;name=lwb5plus-sdio-sa-firmware  \
    ${SUMMIT_URI}/summit-lwb5plus-sdio-sa-m2-firmware-${PV}.tar.bz2;name=lwb5plus-sdio-sa-m2-firmware  \
    ${SUMMIT_URI}/summit-lwb5plus-usb-div-firmware-${PV}.tar.bz2;name=lwb5plus-usb-div-firmware  \
    ${SUMMIT_URI}/summit-lwb5plus-usb-sa-firmware-${PV}.tar.bz2;name=lwb5plus-usb-sa-firmware  \
    ${SUMMIT_URI}/summit-lwb5plus-usb-sa-m2-firmware-${PV}.tar.bz2;name=lwb5plus-usb-sa-m2-firmware  \
    "

do_install_append() {
    # Remove the links that will be recreated later
    rm -f \
        "${D}${nonarch_base_libdir}/firmware/brcm/BCM4373A0-04b4-640c.hcd" \
        "${D}${nonarch_base_libdir}/firmware/brcm/brcmfmac4373-sdio.bin" \
        "${D}${nonarch_base_libdir}/firmware/brcm/brcmfmac4373-sdio.txt" \
        "${D}${nonarch_base_libdir}/firmware/brcm/BCM4373A0.hcd" \
        "${D}${nonarch_base_libdir}/firmware/brcm/brcmfmac4373.bin"
}

PACKAGES =+ " \
    ${PN}-sdio-fw \
    ${PN}-clm \
    ${PN}-usb-clm \
    lwb5plus-sdio-div-firmware \
    lwb5plus-sdio-sa-firmware \
    lwb5plus-sdio-sa-m2-firmware \
    lwb5plus-usb-div-firmware \
    lwb5plus-usb-sa-firmware \
    lwb5plus-usb-sa-m2-firmware \
    "

# Internal packages
FILES_${PN} = ""

FILES_${PN}-sdio-fw = "\
    ${nonarch_base_libdir}/firmware/brcm/brcmfmac4373-sdio-prod_v${LWB_PLUS_SDIO_BIN_VER}.bin \
    ${nonarch_base_libdir}/firmware/brcm/brcmfmac4373-sdio.clm_blob \
    "

FILES_${PN}-clm = "\
    ${nonarch_base_libdir}/firmware/brcm/brcmfmac4373-lwb5p_*.clm_blob \
    "

FILES_${PN}-usb-clm = "\
    ${nonarch_base_libdir}/firmware/brcm/brcmfmac4373.clm_blob \
    "

# User facing packages
FILES_lwb5plus-sdio-div-firmware = "\
    ${nonarch_base_libdir}/firmware/brcm/BCM4373A0-sdio-div_v${LWB_PLUS_SDIO_HCD_VER}.hcd \
    ${nonarch_base_libdir}/firmware/brcm/brcmfmac4373-div.txt \
    "

FILES_lwb5plus-sdio-sa-firmware = "\
    ${nonarch_base_libdir}/firmware/brcm/BCM4373A0-sdio-sa_v${LWB_PLUS_SDIO_SA_HCD_VER}.hcd \
    ${nonarch_base_libdir}/firmware/brcm/brcmfmac4373-sa.txt \
    "

FILES_lwb5plus-sdio-sa-m2-firmware = "\
    ${nonarch_base_libdir}/firmware/brcm/BCM4373A0-sdio-sa-m2_v${LWB_PLUS_SDIO_HCD_VER}.hcd \
    ${nonarch_base_libdir}/firmware/brcm/brcmfmac4373-sa-m2.txt \
    "

FILES_lwb5plus-usb-div-firmware = "\
    ${nonarch_base_libdir}/firmware/brcm/brcmfmac4373-usb-div-prod_v${LWB_PLUS_USB_BIN_VER}.bin \
    ${nonarch_base_libdir}/firmware/brcm/BCM4373A0-usb-div_v${LWB_PLUS_USB_HCD_VER}.hcd \
    "

FILES_lwb5plus-usb-sa-firmware = "\
    ${nonarch_base_libdir}/firmware/brcm/brcmfmac4373-usb-sa-prod_v${LWB_PLUS_USB_BIN_VER}.bin \
    ${nonarch_base_libdir}/firmware/brcm/BCM4373A0-usb-sa_v${LWB_PLUS_USB_SA_HCD_VER}.hcd \
    "

FILES_lwb5plus-usb-sa-m2-firmware = "\
    ${nonarch_base_libdir}/firmware/brcm/brcmfmac4373-usb-sa-m2-prod_v${LWB_PLUS_USB_BIN_VER}.bin \
    ${nonarch_base_libdir}/firmware/brcm/BCM4373A0-usb-sa-m2_v${LWB_PLUS_USB_HCD_VER}.hcd \
    "

RDEPENDS_lwb5plus-sdio-div-firmware += "\
    ${PN}-sdio-fw \
    ${PN}-clm \
    ${@'lwb-if-regdomain' if d.getVar('LWB_REGDOMAIN') else ''} \
    "

RDEPENDS_lwb5plus-sdio-sa-firmware += "\
    ${PN}-sdio-fw \
    ${PN}-clm \
    ${@'lwb-if-regdomain' if d.getVar('LWB_REGDOMAIN') else ''} \
    "

RDEPENDS_lwb5plus-sdio-sa-m2-firmware += "\
    ${PN}-sdio-fw \
    ${PN}-clm \
    ${@'lwb-if-regdomain' if d.getVar('LWB_REGDOMAIN') else ''} \
    "

RDEPENDS_lwb5plus-usb-div-firmware += "\
    ${PN}-clm \
    ${PN}-usb-clm \
    ${@'lwb-if-regdomain' if d.getVar('LWB_REGDOMAIN') else ''} \
    "

RDEPENDS_lwb5plus-usb-sa-firmware += "\
    ${PN}-clm \
    ${PN}-usb-clm \
    ${@'lwb-if-regdomain' if d.getVar('LWB_REGDOMAIN') else ''} \
    "

RDEPENDS_lwb5plus-usb-sa-m2-firmware += "\
    ${PN}-clm \
    ${PN}-usb-clm \
    ${@'lwb-if-regdomain' if d.getVar('LWB_REGDOMAIN') else ''} \
    "

ALTERNATIVE_lwb5plus-sdio-div-firmware = "lwb5plus-sdio-bt lwb5plus-sdio-fw lwb5plus-nvram"
ALTERNATIVE_lwb5plus-sdio-sa-firmware = "lwb5plus-sdio-bt lwb5plus-sdio-fw lwb5plus-nvram"
ALTERNATIVE_lwb5plus-sdio-sa-m2-firmware = "lwb5plus-sdio-bt lwb5plus-sdio-fw lwb5plus-nvram"
ALTERNATIVE_lwb5plus-usb-div-firmware = "lwb5plus-usb-bt lwb5plus-usb-fw"
ALTERNATIVE_lwb5plus-usb-sa-firmware = "lwb5plus-usb-bt lwb5plus-usb-fw"
ALTERNATIVE_lwb5plus-usb-sa-m2-firmware = "lwb5plus-usb-bt lwb5plus-usb-fw"

ALTERNATIVE_LINK_NAME[lwb5plus-sdio-bt] = "${nonarch_base_libdir}/firmware/brcm/BCM4373A0.hcd"
ALTERNATIVE_LINK_NAME[lwb5plus-nvram] = "${nonarch_base_libdir}/firmware/brcm/brcmfmac4373-sdio.txt"
ALTERNATIVE_LINK_NAME[lwb5plus-sdio-fw] = "${nonarch_base_libdir}/firmware/brcm/brcmfmac4373-sdio.bin"

ALTERNATIVE_LINK_NAME[lwb5plus-usb-bt] = "${nonarch_base_libdir}/firmware/brcm/BCM4373A0-04b4-640c.hcd"
ALTERNATIVE_LINK_NAME[lwb5plus-usb-fw] = "${nonarch_base_libdir}/firmware/brcm/brcmfmac4373.bin"

ALTERNATIVE_PRIORITY_lwb5plus-sdio-div-firmware[lwb5plus-sdio-bt] = "100"
ALTERNATIVE_PRIORITY_lwb5plus-sdio-div-firmware[lwb5plus-nvram] = "100"
ALTERNATIVE_PRIORITY_lwb5plus-sdio-div-firmware[lwb5plus-sdio-fw] = "100"

ALTERNATIVE_PRIORITY_lwb5plus-sdio-sa-firmware[lwb5plus-sdio-bt] = "90"
ALTERNATIVE_PRIORITY_lwb5plus-sdio-sa-firmware[lwb5plus-nvram] = "90"
ALTERNATIVE_PRIORITY_lwb5plus-sdio-sa-firmware[lwb5plus-sdio-fw] = "90"

ALTERNATIVE_PRIORITY_lwb5plus-sdio-sa-m2-firmware[lwb5plus-sdio-bt] = "80"
ALTERNATIVE_PRIORITY_lwb5plus-sdio-sa-m2-firmware[lwb5plus-nvram] = "80"
ALTERNATIVE_PRIORITY_lwb5plus-sdio-sa-m2-firmware[lwb5plus-sdio-fw] = "80"

ALTERNATIVE_PRIORITY_lwb5plus-usb-sa-firmware[lwb5plus-usb-bt] = "100"
ALTERNATIVE_PRIORITY_lwb5plus-usb-sa-firmware[lwb5plus-usb-fw] = "100"
ALTERNATIVE_PRIORITY_lwb5plus-usb-sa-firmware[lwb5plus-usb-clm] = "100"

ALTERNATIVE_PRIORITY_lwb5plus-usb-div-firmware[lwb5plus-usb-bt] = "90"
ALTERNATIVE_PRIORITY_lwb5plus-usb-div-firmware[lwb5plus-usb-fw] = "90"

ALTERNATIVE_PRIORITY_lwb5plus-usb-sa-m2-firmware[lwb5plus-usb-bt] = "80"
ALTERNATIVE_PRIORITY_lwb5plus-usb-sa-m2-firmware[lwb5plus-usb-fw] = "80"

ALTERNATIVE_TARGET_lwb5plus-sdio-div-firmware[lwb5plus-sdio-bt] = "${nonarch_base_libdir}/firmware/brcm/BCM4373A0-sdio-div_v${LWB_PLUS_SDIO_HCD_VER}.hcd"
ALTERNATIVE_TARGET_lwb5plus-sdio-sa-firmware[lwb5plus-sdio-bt] = "${nonarch_base_libdir}/firmware/brcm/BCM4373A0-sdio-sa_v${LWB_PLUS_SDIO_SA_HCD_VER}.hcd"
ALTERNATIVE_TARGET_lwb5plus-sdio-sa-m2-firmware[lwb5plus-sdio-bt] = "${nonarch_base_libdir}/firmware/brcm/BCM4373A0-sdio-sa-m2_v${LWB_PLUS_SDIO_HCD_VER}.hcd"
ALTERNATIVE_TARGET_lwb5plus-usb-div-firmware[lwb5plus-usb-bt] = "${nonarch_base_libdir}/firmware/brcm/BCM4373A0-usb-div_v${LWB_PLUS_USB_HCD_VER}.hcd"
ALTERNATIVE_TARGET_lwb5plus-usb-sa-firmware[lwb5plus-usb-bt] = "${nonarch_base_libdir}/firmware/brcm/BCM4373A0-usb-sa_v${LWB_PLUS_USB_SA_HCD_VER}.hcd"
ALTERNATIVE_TARGET_lwb5plus-usb-sa-m2-firmware[lwb5plus-usb-bt] = "${nonarch_base_libdir}/firmware/brcm/BCM4373A0-usb-sa-m2_v${LWB_PLUS_USB_HCD_VER}.hcd"

ALTERNATIVE_TARGET_lwb5plus-sdio-div-firmware[lwb5plus-nvram] = "${nonarch_base_libdir}/firmware/brcm/brcmfmac4373-div.txt"
ALTERNATIVE_TARGET_lwb5plus-sdio-sa-firmware[lwb5plus-nvram] = "${nonarch_base_libdir}/firmware/brcm/brcmfmac4373-sa.txt"
ALTERNATIVE_TARGET_lwb5plus-sdio-sa-m2-firmware[lwb5plus-nvram] = "${nonarch_base_libdir}/firmware/brcm/brcmfmac4373-sa-m2.txt"

ALTERNATIVE_TARGET_lwb5plus-sdio-div-firmware[lwb5plus-sdio-fw] = "${nonarch_base_libdir}/firmware/brcm/brcmfmac4373-sdio-prod_v${LWB_PLUS_SDIO_BIN_VER}.bin"
ALTERNATIVE_TARGET_lwb5plus-sdio-sa-firmware[lwb5plus-sdio-fw] = "${nonarch_base_libdir}/firmware/brcm/brcmfmac4373-sdio-prod_v${LWB_PLUS_SDIO_BIN_VER}.bin"
ALTERNATIVE_TARGET_lwb5plus-sdio-sa-m2-firmware[lwb5plus-sdio-fw] = "${nonarch_base_libdir}/firmware/brcm/brcmfmac4373-sdio-prod_v${LWB_PLUS_SDIO_BIN_VER}.bin"
ALTERNATIVE_TARGET_lwb5plus-usb-div-firmware[lwb5plus-usb-fw] = "${nonarch_base_libdir}/firmware/brcm/brcmfmac4373-usb-div-prod_v${LWB_PLUS_USB_BIN_VER}.bin"
ALTERNATIVE_TARGET_lwb5plus-usb-sa-firmware[lwb5plus-usb-fw] = "${nonarch_base_libdir}/firmware/brcm/brcmfmac4373-usb-sa-prod_v${LWB_PLUS_USB_BIN_VER}.bin"
ALTERNATIVE_TARGET_lwb5plus-usb-sa-m2-firmware[lwb5plus-usb-fw] = "${nonarch_base_libdir}/firmware/brcm/brcmfmac4373-usb-sa-m2-prod_v${LWB_PLUS_USB_BIN_VER}.bin"
