SECTION = "Wireless"

LICENSE = "BSD-3-Clause & Ezurio"
NO_GENERIC_LICENSE[Ezurio] = "LICENSE.ezurio"
LIC_FILES_CHKSUM = "file://COPYING;md5=5ebcb90236d1ad640558c3d3cd3035df \
                    file://README;beginline=1;endline=56;md5=6e4b25e7d74bfc44a32ba37bdf5210a6 \
                    file://wpa_supplicant/wpa_supplicant.c;beginline=1;endline=12;md5=f5ccd57ea91e04800edb88267bf8eae4 \
                    file://LICENSE.ezurio;md5=fd3dd0630b215465b6f50540642d5b93 \
                    "

PV = "${RADIO_VERSION}"

DEPENDS = "libnl openssl"
RRECOMMENDS_${PN} = "${PN}-passphrase ${PN}-cli"

inherit pkgconfig systemd

SYSTEMD_SERVICE_${PN} = "wpa_supplicant.service wpa_supplicant-nl80211@.service wpa_supplicant-wired@.service"
SYSTEMD_AUTO_ENABLE = "disable"

SUMMIT_URI ?= "${SUMMIT_URI_BASE}"
SUMMIT_URI_summit-internal = "${SUMMIT_URI_BASE}/summit_supplicant/laird/${PV}"

SRC_URI += "${SUMMIT_URI}/summit_supplicant-src-${PV}.tar.gz;name=summit-supplicant"

S = "${WORKDIR}/summit_supplicant-${PV}"
B = "${S}/wpa_supplicant"

RPROVIDES_${PN} += "wpa-supplicant"
RREPLACES_${PN} += "wpa-supplicant"
RCONFLICTS_${PN} += "wpa-supplicant"

RPROVIDES_${PN} += "wpa-supplicant-dbg"
RREPLACES_${PN} += "wpa-supplicant-dbg"
RCONFLICTS_${PN} += "wpa-supplicant-dbg"

RPROVIDES_${PN}-passphrase  += "wpa-supplicant-passphrase"
RREPLACES_${PN}-passphrase  += "wpa-supplicant-passphrase"
RCONFLICTS_${PN}-passphrase += "wpa-supplicant-passphrase"

RPROVIDES_${PN}-cli  += "wpa-supplicant-cli"
RREPLACES_${PN}-cli  += "wpa-supplicant-cli"
RCONFLICTS_${PN}-cli += "wpa-supplicant-cli"

RPROVIDES_${PN}-lib  += "wpa-supplicant-lib"
RREPLACES_${PN}-lib  += "wpa-supplicant-lib"
RCONFLICTS_${PN}-lib += "wpa-supplicant-lib"

PACKAGES_prepend = "${PN}-passphrase ${PN}-cli ${PN}-lib "

FILES_${PN}-passphrase = "${bindir}/wpa_passphrase"
FILES_${PN}-cli = "${sbindir}/wpa_cli"
FILES_${PN}-lib = "${libdir}/libwpa_client*"
FILES_${PN} += "${systemd_unitdir}/system/*"

SOLIBS = ".so*"
FILES_SOLIBSDEV = ""

do_configure () {
    cp -f "${DEFCONFIG}" .config

    # For rebuild
    rm -f ./*.d dbus/*.d
}

do_compile_append () {
    ${OBJCOPY} wpa_supplicant sdcsupp
}

EXTRA_OEMAKE = "'BINDIR=${sbindir}'"

SUPP_DBUS_P2P_ENABLE ?= "0"

do_install () {
    install -m 0755 -D -t "${D}${sbindir}" wpa_cli sdcsupp
    install -m 0755 -D -t "${D}${bindir}" wpa_passphrase
	install -m 0644 -D -t "${D}${includedir}" "${S}/src/common/wpa_ctrl.h"

    if ${@bb.utils.contains('DISTRO_FEATURES', 'systemd', 'true', 'false', d)}; then
        install -m 0644 -D -t "${D}${systemd_unitdir}/system" systemd/*.service
    fi
    if [ "${SUPP_DBUS_P2P_ENABLE}" = "0" ]; then
        mkdir -p "${D}${sysconfdir}/wpa_supplicant"
        echo "p2p_disabled=1" > "${D}${sysconfdir}/wpa_supplicant/dbus_overlay.conf"
    fi
}

pkg_postinst_${PN} () {
    # If we're offline, we don't need to do this.
    if [ -z "$D" ]; then
        killall -q -HUP dbus-daemon || true
    fi
}
