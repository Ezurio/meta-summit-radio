SUMMARY = "Summit MSD50 Firmware SDIO-UART"

require radio-firmware.inc
require radio-stack-version.inc

LICENSE += "& Atheros"
NO_GENERIC_LICENSE[Atheros] = "LICENSE.atheros"
LIC_FILES_CHKSUM += "file://LICENSE.atheros;md5=30a14c7823beedac9fa39c64fdd01a13"

RPROVIDES:${PN}  += "wireless-regdb-static"
RREPLACES:${PN}  += "wireless-regdb-static"
RCONFLICTS:${PN} += "wireless-regdb-static"

RDEPENDS:${PN}:remove = "wireless-regdb-static"
