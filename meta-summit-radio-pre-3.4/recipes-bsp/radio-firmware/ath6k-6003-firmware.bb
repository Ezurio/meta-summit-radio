SUMMARY = "Summit MSD45 Firmware SDIO-UART"

require radio-firmware.inc
require radio-stack-version.inc

LICENSE += "& Atheros"
NO_GENERIC_LICENSE[Atheros] = "LICENSE.atheros"
LIC_FILES_CHKSUM += "file://LICENSE.atheros;md5=30a14c7823beedac9fa39c64fdd01a13"

RPROVIDES_${PN}  += "wireless-regdb-static"
RREPLACES_${PN}  += "wireless-regdb-static"
RCONFLICTS_${PN} += "wireless-regdb-static"
