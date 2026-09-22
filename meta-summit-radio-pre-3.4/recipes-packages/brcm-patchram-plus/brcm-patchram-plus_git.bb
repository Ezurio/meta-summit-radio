SUMMARY = "Infineon/Cypress/Broadcom Patchram Plus utility"
SECTION = "Wireless"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=691691b063f1b4034300dc452e36b68d"

inherit pkgconfig

require radio-stack-version.inc

SRC_URI = "git://github.com/Ezurio/brcm_patchram.git;nobranch=1;protocol=https"
SRC_URI:summit-internal = "git://github.com/rfpros/cp_linux-brcm_patchram.git;nobranch=1;protocol=https"

SRCREV = "025ec1a81769afbd9cb1498428a8255ac20c1d7e"

S = "${SUMMIT_GIT_UNPACKDIR}"

DEPENDS += "${@bb.utils.contains('DISTRO_FEATURES','bluez5','bluez5','bluez4',d)}"

do_compile () {
	oe_runmake brcm_patchram_plus
}

do_install () {
	install -D -t "${D}${sbindir}" -m 755 brcm_patchram_plus
}
