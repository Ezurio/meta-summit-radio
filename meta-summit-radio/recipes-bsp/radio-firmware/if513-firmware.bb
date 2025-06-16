SUMMARY = "Summit firmware for the IF513 SDIO/UART modules"

SUMMARY:if513-sdio-div-firmware = "Summit firmware for the IF513 SDIO/UART module with diversity antenna"
SUMMARY:if513-sdio-sa-firmware = "Summit firmware for the IF513 SDIO/UART module with single antenna"

require radio-firmware-lwb.inc
inherit update-alternatives

SRC_URI = "\
    ${SUMMIT_URI}/summit-if513-sdio-div-firmware-${PV}.tar.bz2;name=if513-sdio-div-firmware  \
    ${SUMMIT_URI}/summit-if513-sdio-sa-firmware-${PV}.tar.bz2;name=if513-sdio-sa-firmware  \
    "

do_install:append() {
    # Remove the generic firmware files that are not used by LWB5+
    rm -f "${D}${libdir}/firmware/cypress/cyfmac55500-sdio.txt"
}

PACKAGES =+ " \
    ${PN}-sdio-fw \
    if513-sdio-div-firmware \
    if513-sdio-sa-firmware \
    "

# Internal packages
FILES:${PN} = ""

# User facing packages
FILES:${PN}-sdio-fw = "\
    ${libdir}/firmware/cypress/cyfmac55500-if513_*.clm_blob \
    ${libdir}/firmware/cypress/cyfmac55500-sdio.clm_blob \
    ${libdir}/firmware/cypress/cyfmac55500-sdio.trxse \
    ${libdir}/firmware/cypress/cyfmac55500-sdio-prod_*.trxse \
    ${libdir}/firmware/cypress/CYW55500A1_*.hcd \
    ${libdir}/firmware/brcm/CYW55500A1.hcd \
    "

FILES:if513-sdio-div-firmware = "\
    ${libdir}/firmware/cypress/cyfmac55500-if513-div.txt \
    "

FILES:if513-sdio-sa-firmware = "\
    ${libdir}/firmware/cypress/cyfmac55500-if513-sa.txt \
    "

RDEPENDS:if513-sdio-div-firmware += "${PN}-sdio-fw"
RDEPENDS:if513-sdio-sa-firmware += "${PN}-sdio-fw"
 
ALTERNATIVE:if513-sdio-div-firmware = "if513-nvram"
ALTERNATIVE:if513-sdio-sa-firmware = "if513-nvram"

ALTERNATIVE_LINK_NAME[if513-nvram] = "${libdir}/firmware/cypress/cyfmac55500-sdio.txt"

ALTERNATIVE_PRIORITY_if513-sdio-div-firmware[if513-nvram] = "100"
ALTERNATIVE_PRIORITY_if513-sdio-sa-firmware[if513-nvram] = "90"

ALTERNATIVE_TARGET_if513-sdio-div-firmware[if513-nvram] = "${libdir}/firmware/cypress/cyfmac55500-if513-div.txt"
ALTERNATIVE_TARGET_if513-sdio-sa-firmware[if513-nvram] = "${libdir}/firmware/cypress/cyfmac55500-if513-sa.txt"
