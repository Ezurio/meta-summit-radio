SUMMARY = "Summit firmware for the IF573 modules"

SUMMARY_if573-sdio-firmware = "Summit firmware for the IF573 SDIO/UART module"
SUMMARY_if573-pcie-firmware = "Summit firmware for the IF573 PCIE/UART module"

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

FILES_${PN} = ""

FILES_${PN}-if573-fw = "\
    ${nonarch_base_libdir}/firmware/cypress/cyfmac55572-if573.txt \
    ${nonarch_base_libdir}/firmware/cypress/cyfmac55572-if573_*.clm_blob \
    ${nonarch_base_libdir}/firmware/cypress/CYW55560A1_*.hcd \
    ${nonarch_base_libdir}/firmware/brcm/CYW55560A1.hcd \
    "

FILES_if573-sdio-firmware = "\
    ${nonarch_base_libdir}/firmware/cypress/cyfmac55572-sdio-prod_*.trxse \
    ${nonarch_base_libdir}/firmware/cypress/cyfmac55572-sdio.clm_blob \
    ${nonarch_base_libdir}/firmware/cypress/cyfmac55572-sdio.trxse \
    ${nonarch_base_libdir}/firmware/cypress/cyfmac55572-sdio.txt \
    "

FILES_if573-pcie-firmware = "\
    ${nonarch_base_libdir}/firmware/cypress/cyfmac55572-pcie-prod_*.trxse \
    ${nonarch_base_libdir}/firmware/cypress/cyfmac55572-pcie.clm_blob \
    ${nonarch_base_libdir}/firmware/cypress/cyfmac55572-pcie.trxse \
    ${nonarch_base_libdir}/firmware/cypress/cyfmac55572-pcie.txt \
    "

RDEPENDS_if573-sdio-firmware += "\
    ${PN}-if573-fw \
    ${@'lwb-if-regdomain' if d.getVar('LWB_REGDOMAIN') else ''} \
    "
RDEPENDS_if573-pcie-firmware += "\
    ${PN}-if573-fw \
    ${@'lwb-if-regdomain' if d.getVar('LWB_REGDOMAIN') else ''} \
    "
