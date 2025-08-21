SUMMARY = "TI351 Firmware"

require radio-firmware.inc
require radio-stack-version.inc

TI351_REGDOMAIN ?= "WW"

SRC_URI = "${SUMMIT_URI}/summit-ti351-${TI351_REGDOMAIN}-firmware-${PV}.tar.bz2;subdir=src;name=ti351-${TI351_REGDOMAIN}-firmware"
