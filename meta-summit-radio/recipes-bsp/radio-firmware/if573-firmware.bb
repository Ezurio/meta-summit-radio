SUMMARY = "Summit firmware for the IF573 modules"

SUMMARY:if573-sdio-firmware = "Summit firmware for the IF573 SDIO/UART module"
SUMMARY:if573-pcie-firmware = "Summit firmware for the IF573 PCIE/UART module"

require radio-firmware-lwb.inc

SRC_URI = "\
    ${SUMMIT_URI}/summit-if573-sdio-firmware-${PV}.tar.bz2;name=if573-sdio-firmware  \
    ${SUMMIT_URI}/summit-if573-pcie-firmware-${PV}.tar.bz2;name=if573-pcie-firmware  \
    "

PACKAGES =+ " \
    ${PN}-if573-fw \
    if573-sdio-firmware \
    if573-pcie-firmware \
    "

FILES:${PN} = ""

FILES:${PN}-if573-fw = "\
    ${libdir}/firmware/cypress/cyfmac55572-if573.txt \
    ${libdir}/firmware/cypress/cyfmac55572-if573_*.clm_blob \
    ${libdir}/firmware/cypress/CYW55560A1_*.hcd \
    ${libdir}/firmware/brcm/CYW55560A1.hcd \
    "

FILES:if573-sdio-firmware = "\
    ${libdir}/firmware/cypress/cyfmac55572-sdio-prod_*.trxse \
    ${libdir}/firmware/cypress/cyfmac55572-sdio.clm_blob \
    ${libdir}/firmware/cypress/cyfmac55572-sdio.trxse \
    ${libdir}/firmware/cypress/cyfmac55572-sdio.txt \
    "

FILES:if573-pcie-firmware = "\
    ${libdir}/firmware/cypress/cyfmac55572-pcie-prod_*.trxse \
    ${libdir}/firmware/cypress/cyfmac55572-pcie.clm_blob \
    ${libdir}/firmware/cypress/cyfmac55572-pcie.trxse \
    ${libdir}/firmware/cypress/cyfmac55572-pcie.txt \
    "

RDEPENDS:if573-sdio-firmware += "${PN}-if573-fw"
RDEPENDS:if573-pcie-firmware += "${PN}-if573-fw"
