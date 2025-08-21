SECTION = "kernel"

LICENSE = "Ezurio"
NO_GENERIC_LICENSE[Ezurio] = "LICENSE.ezurio"
LIC_FILES_CHKSUM = "file://LICENSE.ezurio;md5=fd3dd0630b215465b6f50540642d5b93"

PV = "${RADIO_VERSION}"

inherit allarch

SUMMIT_URI ?= "${SUMMIT_URI_BASE}"
SUMMIT_URI_summit-internal = "${SUMMIT_URI_BASE}/firmware/${PV}"

SRC_URI = "${SUMMIT_URI}/summit-${BPN}-${PV}.tar.bz2;subdir=src;name=${BPN}"

do_configure[noexec] = "1"
do_compile[noexec] = "1"

FILES_${PN} += "${nonarch_base_libdir}"

S = "${WORKDIR}/src"

ALLOW_EMPTY_${PN}-dev = "0"
ALLOW_EMPTY_${PN}-dbg = "0"

# Firmware files are generally not ran on the CPU, so they can be
# allarch despite being architecture specific
INSANE_SKIP = "arch"

# Don't warn about already stripped files
INSANE_SKIP_${PN} = "already-stripped"

# No need to put firmware into the sysroot
SYSROOT_DIRS_IGNORE += "${nonarch_base_libdir}/firmware"

do_install() {
	install -d  "${D}${nonarch_base_libdir}"
	cp -r "${S}/lib/"* "${D}${nonarch_base_libdir}"
}
