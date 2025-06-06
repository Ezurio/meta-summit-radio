SUMMARY = "Summit firmware for the LWB5+ SDIO/UART modules"

SUMMARY:lwb5plus-sdio-div-firmware = "Summit firmware for the LWB5+ SDIO/UART module with diversity antenna"
SUMMARY:lwb5plus-sdio-sa-firmware = "Summit firmware for the LWB5+ SDIO/UART module with single antenna"
SUMMARY:lwb5plus-sdio-sa-m2-firmware = "Summit firmware for the LWB5+ SDIO/UART module with single antenna M.2"
SUMMARY:lwb5plus-usb-div-firmware = "Summit firmware for the LWB5+ USB module with diversity antenna"
SUMMARY:lwb5plus-usb-sa-firmware = "Summit firmware for the LWB5+ USB module with single antenna"
SUMMARY:lwb5plus-usb-sa-m2-firmware = "Summit firmware for the LWB5+ USB module with single antenna M.2"

require radio-firmware-lwb.inc

SRC_URI = "\
    ${SUMMIT_URI}/summit-lwb5plus-sdio-div-firmware-${PV}.tar.bz2;name=lwb5plus-sdio-div-firmware  \
    ${SUMMIT_URI}/summit-lwb5plus-sdio-sa-firmware-${PV}.tar.bz2;name=lwb5plus-sdio-sa-firmware  \
    ${SUMMIT_URI}/summit-lwb5plus-sdio-sa-m2-firmware-${PV}.tar.bz2;name=lwb5plus-sdio-sa-m2-firmware  \
    ${SUMMIT_URI}/summit-lwb5plus-usb-div-firmware-${PV}.tar.bz2;name=lwb5plus-usb-div-firmware  \
    ${SUMMIT_URI}/summit-lwb5plus-usb-sa-firmware-${PV}.tar.bz2;name=lwb5plus-usb-sa-firmware  \
    ${SUMMIT_URI}/summit-lwb5plus-usb-sa-m2-firmware-${PV}.tar.bz2;name=lwb5plus-usb-sa-m2-firmware  \
    "

do_install:append() {
    # Remove the generic firmware files that are not used by LWB5+
    rm -f \
        "${D}${libdir}/firmware/brcm/BCM4373A0-04b4-640c.hcd" \
        "${D}${libdir}/firmware/brcm/brcmfmac4373-sdio.bin" \
        "${D}${libdir}/firmware/brcm/brcmfmac4373-sdio.txt" \
        "${D}${libdir}/firmware/brcm/brcmfmac4373-sdio.clm_blob" \
        "${D}${libdir}/firmware/brcm/BCM4373A0.hcd" \
        "${D}${libdir}/firmware/brcm/brcmfmac4373.clm_blob" \
        "${D}${libdir}/firmware/brcm/brcmfmac4373.bin"
}

PACKAGES =+ " \
    ${PN}-sdio-fw \
    ${PN}-div-clm \
    ${PN}-sa-clm \
    ${PN}-sa-m2-clm \
    lwb5plus-sdio-div-firmware \
    lwb5plus-sdio-sa-firmware \
    lwb5plus-sdio-sa-m2-firmware \
    lwb5plus-usb-div-firmware \
    lwb5plus-usb-sa-firmware \
    lwb5plus-usb-sa-m2-firmware \
    "

# Internal packages
FILES:${PN} = ""

FILES:${PN}-sdio-fw = "\
    ${libdir}/firmware/brcm/brcmfmac4373-sdio-prod_*.bin \
    "

FILES:${PN}-div-clm = "\
    ${libdir}/firmware/brcm/brcmfmac4373-clm-div.clm_blob \
    "

FILES:${PN}-sa-clm = "\
    ${libdir}/firmware/brcm/brcmfmac4373-clm-sa.clm_blob \
    "

FILES:${PN}-sa-m2-clm = "\
    ${libdir}/firmware/brcm/brcmfmac4373-clm-sa-m2.clm_blob \
    "

# User facing packages
FILES:lwb5plus-sdio-div-firmware = "\
    ${libdir}/firmware/brcm/BCM4373A0-sdio-div_*.hcd \
    ${libdir}/firmware/brcm/brcmfmac4373-div.txt \
    "

FILES:lwb5plus-sdio-sa-firmware = "\
    ${libdir}/firmware/brcm/BCM4373A0-sdio-sa_*.hcd \
    ${libdir}/firmware/brcm/brcmfmac4373-sa.txt \
    "

FILES:lwb5plus-sdio-sa-m2-firmware = "\
    ${libdir}/firmware/brcm/BCM4373A0-sdio-sa-m2_*.hcd \
    ${libdir}/firmware/brcm/brcmfmac4373-sa-m2.txt \
    "

FILES:lwb5plus-usb-div-firmware = "\
    ${libdir}/firmware/brcm/brcmfmac4373-usb-div-prod_*.bin \
    ${libdir}/firmware/brcm/BCM4373A0-usb-div_*.hcd \
    "

FILES:lwb5plus-usb-sa-firmware = "\
    ${libdir}/firmware/brcm/brcmfmac4373-usb-sa-prod_*.bin \
    ${libdir}/firmware/brcm/BCM4373A0-usb-sa_*.hcd \
    "

FILES:lwb5plus-usb-sa-m2-firmware = "\
    ${libdir}/firmware/brcm/brcmfmac4373-usb-sa-m2-prod_*.bin \
    ${libdir}/firmware/brcm/BCM4373A0-usb-sa-m2_*.hcd \
    ${libdir}/firmware/brcm/brcmfmac4373-sa-m2.txt \
    "

RDEPENDS:lwb5plus-sdio-div-firmware += "${PN}-sdio-fw ${PN}-div-clm"
RDEPENDS:lwb5plus-sdio-sa-firmware += "${PN}-sdio-fw ${PN}-sa-clm"
RDEPENDS:lwb5plus-sdio-sa-m2-firmware += "${PN}-sdio-fw ${PN}-sa-m2-clm"
RDEPENDS:lwb5plus-usb-div-firmware += "${PN}-div-clm"
RDEPENDS:lwb5plus-usb-sa-firmware += "${PN}-sa-clm"
RDEPENDS:lwb5plus-usb-sa-m2-firmware += "${PN}-sa-m2-clm"

ALTERNATIVE:lwb5plus-sdio-div-firmware = "lwb5plus-sdio-bt lwb5plus-sdio-fw lwb5plus-sdio-clm lwb5plus-nvram"
ALTERNATIVE:lwb5plus-sdio-sa-firmware = "lwb5plus-sdio-bt lwb5plus-sdio-fw lwb5plus-sdio-clm lwb5plus-nvram"
ALTERNATIVE:lwb5plus-sdio-sa-m2-firmware = "lwb5plus-sdio-bt lwb5plus-sdio-fw lwb5plus-sdio-clm lwb5plus-nvram"
ALTERNATIVE:lwb5plus-usb-div-firmware = "lwb5plus-usb-bt lwb5plus-usb-fw lwb5plus-usb-clm"
ALTERNATIVE:lwb5plus-usb-sa-firmware = "lwb5plus-usb-bt lwb5plus-usb-fw lwb5plus-usb-clm"
ALTERNATIVE:lwb5plus-usb-sa-m2-firmware = "lwb5plus-usb-bt lwb5plus-usb-fw lwb5plus-usb-clm"

ALTERNATIVE_LINK_NAME[lwb5plus-sdio-bt] = "${libdir}/firmware/brcm/BCM4373A0.hcd"
ALTERNATIVE_LINK_NAME[lwb5plus-nvram] = "${libdir}/firmware/brcm/brcmfmac4373-sdio.txt"
ALTERNATIVE_LINK_NAME[lwb5plus-sdio-fw] = "${libdir}/firmware/brcm/brcmfmac4373-sdio-prod.bin"
ALTERNATIVE_LINK_NAME[lwb5plus-sdio-clm] = "${libdir}/firmware/brcm/brcmfmac4373-sdio-clm.clm_blob"

ALTERNATIVE_LINK_NAME[lwb5plus-usb-bt] = "${libdir}/firmware/brcm/BCM4373A0-04b4-640c.hcd"
ALTERNATIVE_LINK_NAME[lwb5plus-usb-fw] = "${libdir}/firmware/brcm/brcmfmac4373-usb-prod.bin"
ALTERNATIVE_LINK_NAME[lwb5plus-usb-clm] = "${libdir}/firmware/brcm/brcmfmac4373-usb-clm.clm_blob"

ALTERNATIVE_PRIORITY_lwb5plus-sdio-div-firmware[lwb5plus-sdio-bt] = "100"
ALTERNATIVE_PRIORITY_lwb5plus-sdio-sa-firmware[lwb5plus-sdio-bt] = "90"
ALTERNATIVE_PRIORITY_lwb5plus-sdio-sa-m2-firmware[lwb5plus-sdio-bt] = "80"
ALTERNATIVE_PRIORITY_lwb5plus-usb-div-firmware[lwb5plus-usb-bt] = "100"
ALTERNATIVE_PRIORITY_lwb5plus-usb-sa-firmware[lwb5plus-usb-bt] = "90"
ALTERNATIVE_PRIORITY_lwb5plus-usb-sa-m2-firmware[lwb5plus-usb-bt] = "80"

ALTERNATIVE_PRIORITY_lwb5plus-sdio-div-firmware[lwb5plus-nvram] = "100"
ALTERNATIVE_PRIORITY_lwb5plus-sdio-sa-firmware[lwb5plus-nvram] = "90"
ALTERNATIVE_PRIORITY_lwb5plus-sdio-sa-m2-firmware[lwb5plus-nvram] = "80"

ALTERNATIVE_PRIORITY_lwb5plus-sdio-div-firmware[lwb5plus-sdio-fw] = "100"
ALTERNATIVE_PRIORITY_lwb5plus-sdio-sa-firmware[lwb5plus-sdio-fw] = "90"
ALTERNATIVE_PRIORITY_lwb5plus-sdio-sa-m2-firmware[lwb5plus-sdio-fw] = "80"
ALTERNATIVE_PRIORITY_lwb5plus-usb-div-firmware[lwb5plus-usb-fw] = "100"
ALTERNATIVE_PRIORITY_lwb5plus-usb-sa-firmware[lwb5plus-usb-fw] = "90"
ALTERNATIVE_PRIORITY_lwb5plus-usb-sa-m2-firmware[lwb5plus-usb-fw] = "80"

ALTERNATIVE_PRIORITY_lwb5plus-sdio-div-firmware[lwb5plus-sdio-clm] = "100"
ALTERNATIVE_PRIORITY_lwb5plus-sdio-sa-firmware[lwb5plus-sdio-clm] = "90"
ALTERNATIVE_PRIORITY_lwb5plus-sdio-sa-m2-firmware[lwb5plus-sdio-clm] = "80"
ALTERNATIVE_PRIORITY_lwb5plus-usb-div-firmware[lwb5plus-usb-clm] = "100"
ALTERNATIVE_PRIORITY_lwb5plus-usb-sa-firmware[lwb5plus-usb-clm] = "90"
ALTERNATIVE_PRIORITY_lwb5plus-usb-sa-m2-firmware[lwb5plus-usb-clm] = "80"

def getfile(d, p):
    import glob

    # Get the path to the source directory
    destdir = d.getVar('D', True)
    file_path = destdir + d.getVar('libdir', True) + '/firmware/brcm/'

    # Use glob to find the file, in case it has a version suffix
    file_names = glob.glob(file_path + p)
    if not file_names:
        bb.fatal("File %s does not exist in %s" % (p, file_path))

    return file_names[0][len(destdir):]

ALTERNATIVE_TARGET_lwb5plus-sdio-div-firmware[lwb5plus-sdio-bt] = "${@getfile(d, 'BCM4373A0-sdio-div_*.hcd')}"
ALTERNATIVE_TARGET_lwb5plus-sdio-sa-firmware[lwb5plus-sdio-bt] = "${@getfile(d, 'BCM4373A0-sdio-sa_*.hcd')}"
ALTERNATIVE_TARGET_lwb5plus-sdio-sa-m2-firmware[lwb5plus-sdio-bt] = "${@getfile(d, 'BCM4373A0-sdio-sa-m2_*.hcd')}"
ALTERNATIVE_TARGET_lwb5plus-usb-div-firmware[lwb5plus-usb-bt] = "${@getfile(d, 'BCM4373A0-usb-div_*.hcd')}"
ALTERNATIVE_TARGET_lwb5plus-usb-sa-firmware[lwb5plus-usb-bt] = "${@getfile(d, 'BCM4373A0-usb-sa_*.hcd')}"
ALTERNATIVE_TARGET_lwb5plus-usb-sa-m2-firmware[lwb5plus-usb-bt] = "${@getfile(d, 'BCM4373A0-usb-sa-m2_*.hcd')}"

ALTERNATIVE_TARGET_lwb5plus-sdio-div-firmware[lwb5plus-nvram] = "${libdir}/firmware/brcm/brcmfmac4373-div.txt"
ALTERNATIVE_TARGET_lwb5plus-sdio-sa-firmware[lwb5plus-nvram] = "${libdir}/firmware/brcm/brcmfmac4373-sa.txt"
ALTERNATIVE_TARGET_lwb5plus-sdio-sa-m2-firmware[lwb5plus-nvram] = "${libdir}/firmware/brcm/brcmfmac4373-sa-m2.txt"

ALTERNATIVE_TARGET_lwb5plus-sdio-div-firmware[lwb5plus-sdio-fw] = "${@getfile(d, 'brcmfmac4373-sdio-prod_*.bin')}"
ALTERNATIVE_TARGET_lwb5plus-sdio-sa-firmware[lwb5plus-sdio-fw] = "${@getfile(d, 'brcmfmac4373-sdio-prod_*.bin')}"
ALTERNATIVE_TARGET_lwb5plus-sdio-sa-m2-firmware[lwb5plus-sdio-fw] = "${@getfile(d, 'brcmfmac4373-sdio-prod_*.bin')}"
ALTERNATIVE_TARGET_lwb5plus-usb-div-firmware[lwb5plus-usb-fw] = "${@getfile(d, 'brcmfmac4373-usb-div-prod_*.bin')}"
ALTERNATIVE_TARGET_lwb5plus-usb-sa-firmware[lwb5plus-usb-fw] = "${@getfile(d, 'brcmfmac4373-usb-sa-prod_*.bin')}"
ALTERNATIVE_TARGET_lwb5plus-usb-sa-m2-firmware[lwb5plus-usb-fw] = "${@getfile(d, 'brcmfmac4373-usb-sa-m2-prod_*.bin')}"

ALTERNATIVE_TARGET_lwb5plus-sdio-div-firmware[lwb5plus-sdio-clm] = "${libdir}/firmware/brcm/brcmfmac4373-clm-div.clm_blob"
ALTERNATIVE_TARGET_lwb5plus-sdio-sa-firmware[lwb5plus-sdio-clm] = "${libdir}/firmware/brcm/brcmfmac4373-clm-sa.clm_blob"
ALTERNATIVE_TARGET_lwb5plus-sdio-sa-m2-firmware[lwb5plus-sdio-clm] = "${libdir}/firmware/brcm/brcmfmac4373-clm-sa-m2.clm_blob"
ALTERNATIVE_TARGET_lwb5plus-usb-div-firmware[lwb5plus-usb-clm] = "${libdir}/firmware/brcm/brcmfmac4373-clm-div.clm_blob"
ALTERNATIVE_TARGET_lwb5plus-usb-sa-firmware[lwb5plus-usb-clm] = "${libdir}/firmware/brcm/brcmfmac4373-clm-sa.clm_blob"
ALTERNATIVE_TARGET_lwb5plus-usb-sa-m2-firmware[lwb5plus-usb-clm] = "${libdir}/firmware/brcm/brcmfmac4373-clm-sa-m2.clm_blob"
