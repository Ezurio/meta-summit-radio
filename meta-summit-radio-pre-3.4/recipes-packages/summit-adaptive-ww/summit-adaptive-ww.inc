SUMMARY = "Wi-Fi Summit 60 Auto Regulatory Domain"
SECTION = "Wireless"

LICENSE = "Ezurio"
NO_GENERIC_LICENSE[Ezurio] = "LICENSE.ezurio"
LIC_FILES_CHKSUM = "file://LICENSE.ezurio;md5=fd3dd0630b215465b6f50540642d5b93"

PV = "${RADIO_VERSION}"

inherit update-rc.d systemd

SYSTEMD_SERVICE_${PN} = "adaptive_ww.service"
SYSTEMD_AUTO_ENABLE = "enable"

INITSCRIPT_NAME = "adaptive_ww"
INITSCRIPT_PARAMS = "defaults 30"

HFP = "${@bb.utils.contains('TUNE_FEATURES', 'callconvention-hard', 'eabihf', 'eabi', d)}"

SUMMIT_URI ?= "${SUMMIT_URI_BASE}"
SUMMIT_URI_summit-internal = "${SUMMIT_URI_BASE}/adaptive_ww/laird/${PV}"

ADAP_ARCH_x86 = "-x86"
ADAP_ARCH_x86-64 = "-x86_64"
ADAP_ARCH_powerpc64 = "-powerpc64-e5500"
ADAP_ARCH_arm = "-arm-${HFP}"
ADAP_ARCH_aarch64 = "-aarch64"

SRC_URI = "${SUMMIT_URI}/adaptive_ww${ADAP_ARCH}-${PV}.tar.bz2;subdir=src;name=adaptive-ww${ADAP_ARCH}"

do_configure[noexec] = "1"
do_compile[noexec] = "1"

S = "${SUMMIT_UNPACKDIR}/src"

RDEPENDS_${PN} = "libnl libnl-genl libnl-route"

INSANE_SKIP_${PN} = "ldflags already-stripped"

ALLOW_EMPTY_${PN}-dev = "0"
ALLOW_EMPTY_${PN}-dbg = "0"

FILES_${PN} += "${base_libdir} ${sysconfdir}"

AWM_PARAMS = ""

do_install () {
	install -D -t "${D}${bindir}" -m 755 "${S}/usr/bin/adaptive_ww"

	install -d "${D}${sysconfdir}/default"
	echo "AWM_ARGS=${AWM_PARAMS}" > "${D}${sysconfdir}/default/adaptive_ww"
	install -D -t "${D}${sysconfdir}/init.d" -m 755 "${S}/etc/init.d/adaptive_ww"

	if ${@bb.utils.contains('DISTRO_FEATURES','systemd','true','false',d)}; then
		install -D -t "${D}${systemd_unitdir}/system" -m 644 "${S}/usr/lib/systemd/system/adaptive_ww.service"
	fi
}
