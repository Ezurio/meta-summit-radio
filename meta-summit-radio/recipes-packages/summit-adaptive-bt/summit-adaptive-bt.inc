SUMMARY = "Summit Bluetooth Auto World-Wide Mode for 60 Radio"
SECTION = "Wireless"

LICENSE = "Ezurio"
NO_GENERIC_LICENSE[Ezurio] = "LICENSE.ezurio"
LIC_FILES_CHKSUM = "file://LICENSE.ezurio;md5=fd3dd0630b215465b6f50540642d5b93"

PV = "${RADIO_VERSION}"

inherit pkgconfig update-rc.d systemd

DEPENDS = "libnl bluez5"

SUMMIT_URI ?= "${SUMMIT_URI_BASE}"
SUMMIT_URI:summit-internal = "${SUMMIT_URI_BASE}/adaptive_bt/src/${PV}"

SRC_URI = "${SUMMIT_URI}/adaptive_bt-src-${PV}.tar.gz;name=adaptive-bt"

S = "${WORKDIR}/adaptive_bt-${PV}"

EXTRA_OEMAKE = "'LINKOPT=${LDFLAGS}'"

SYSTEMD_SERVICE:${PN} = "adaptive_bt.service"
SYSTEMD_AUTO_ENABLE = "enable"

INITSCRIPT_NAME = "adaptive_bt"
INITSCRIPT_PARAMS = "defaults 30"

FILES:${PN} += "${sysconfdir}"

do_install () {
	install -D -t "${D}${bindir}" -m 755 "${S}/bin/adaptive_bt"
	install -D -t "${D}${sysconfdir}/init.d" -m 755 "${S}/support/adaptive_bt"

	if ${@bb.utils.contains('DISTRO_FEATURES','systemd','true','false',d)}; then
		install -D -t "${D}${systemd_unitdir}/system" -m 644 "${S}/support/adaptive_bt.service"
	fi
}
