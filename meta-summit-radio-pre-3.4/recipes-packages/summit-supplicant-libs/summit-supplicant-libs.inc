SECTION = "Wireless"

LICENSE = "Ezurio"
NO_GENERIC_LICENSE[Ezurio] = "LICENSE.ezurio"
LIC_FILES_CHKSUM = "file://LICENSE.ezurio;md5=fd3dd0630b215465b6f50540642d5b93"

PV = "${RADIO_VERSION}"

HFP = "${@bb.utils.contains('TUNE_FEATURES', 'callconvention-hard', 'eabihf', 'eabi', d)}"

SUMMIT_URI ?= "${SUMMIT_URI_BASE}"
SUMMIT_URI_summit-internal = "${SUMMIT_URI_BASE}/summit_supplicant/laird/${PV}"

SUPP_ARCH_x86 = "-x86"
SUPP_ARCH_x86-64 = "-x86_64"
SUPP_ARCH_powerpc64 = "-powerpc64-e5500"
SUPP_ARCH_arm = "-arm-${HFP}"
SUPP_ARCH_aarch64 = "-aarch64"

SRC_URI = "${SUMMIT_URI}/summit_supplicant_libs${SUPP_ARCH}-${PV}.tar.bz2;subdir=src;name=summit-supplicant-libs${SUPP_ARCH}"

do_configure[noexec] = "1"
do_compile[noexec] = "1"

S = "${WORKDIR}/src"

INSANE_SKIP_${PN} = "ldflags already-stripped"
INSANE_SKIP_${PN}-passphrase = "ldflags already-stripped"
INSANE_SKIP_${PN}-cli = "ldflags already-stripped"

ALLOW_EMPTY_${PN}-dev = "0"
ALLOW_EMPTY_${PN}-dbg = "0"

FILES_SOLIBSDEV = ""

FILES_${PN} += "${libdir}/libsdcsupp.so"

do_install () {
	install -D -t "${D}${libdir}" -m 0644 "${S}/usr/lib/libsdcsupp.so"
}
