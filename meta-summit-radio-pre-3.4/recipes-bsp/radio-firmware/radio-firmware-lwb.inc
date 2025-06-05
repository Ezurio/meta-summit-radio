require radio-firmware.inc
require radio-stack-version.inc

LICENSE += "& Cypress"
NO_GENERIC_LICENSE[Cypress] = "LICENSE.cypress"
LIC_FILES_CHKSUM += "file://LICENSE.cypress;md5=cbc5f665d04f741f1e006d2096236ba7"

FILES_${PN} += "${sysconfdir}"

RDEPENDS_${PN} += "${@'lwb-if-regdomain' if d.getVar('LWB_REGDOMAIN') else ''}"
RRECOMMENDS_${PN} += "wireless-regdb-static"
