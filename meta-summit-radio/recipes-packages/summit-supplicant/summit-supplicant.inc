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
RRECOMMENDS:${PN} = "${PN}-passphrase ${PN}-cli"

inherit pkgconfig systemd

SYSTEMD_SERVICE:${PN} = "wpa_supplicant.service wpa_supplicant-nl80211@.service wpa_supplicant-wired@.service"
SYSTEMD_AUTO_ENABLE = "disable"

SUMMIT_URI ?= "${SUMMIT_URI_BASE}"
SUMMIT_URI:summit-internal = "${SUMMIT_URI_BASE}/summit_supplicant/laird/${PV}"

SRC_URI += "${SUMMIT_URI}/summit_supplicant-src-${PV}.tar.gz;name=summit-supplicant"

S = "${WORKDIR}/summit_supplicant-${PV}"
B = "${S}/wpa_supplicant"

RPROVIDES:${PN} += "wpa-supplicant"
RREPLACES:${PN} += "wpa-supplicant"
RCONFLICTS:${PN} += "wpa-supplicant"

RPROVIDES:${PN} += "wpa-supplicant-dbg"
RREPLACES:${PN} += "wpa-supplicant-dbg"
RCONFLICTS:${PN} += "wpa-supplicant-dbg"

RPROVIDES:${PN}-passphrase  += "wpa-supplicant-passphrase"
RREPLACES:${PN}-passphrase  += "wpa-supplicant-passphrase"
RCONFLICTS:${PN}-passphrase += "wpa-supplicant-passphrase"

RPROVIDES:${PN}-cli  += "wpa-supplicant-cli"
RREPLACES:${PN}-cli  += "wpa-supplicant-cli"
RCONFLICTS:${PN}-cli += "wpa-supplicant-cli"

RPROVIDES:${PN}-lib  += "wpa-supplicant-lib"
RREPLACES:${PN}-lib  += "wpa-supplicant-lib"
RCONFLICTS:${PN}-lib += "wpa-supplicant-lib"

PACKAGES:prepend = "${PN}-passphrase ${PN}-cli ${PN}-lib "

FILES:${PN}-passphrase = "${bindir}/wpa_passphrase"
FILES:${PN}-cli = "${sbindir}/wpa_cli"
FILES:${PN}-lib = "${libdir}/libwpa_client.so"
FILES:${PN} += "${systemd_unitdir}/system/* ${datadir}/dbus-1/* ${sysconfdir}/dbus-1/*"

RDEPENDS:${PN}-dev += "${PN}-lib"

do_configure () {
    cp -f "${DEFCONFIG}" .config

    # For rebuild
    rm -f ./*.d dbus/*.d
}

do_compile:append () {
    ${OBJCOPY} wpa_supplicant sdcsupp
}

EXTRA_OEMAKE = "'BINDIR=${sbindir}'"

SUPP_DBUS_P2P_ENABLE ?= "0"

do_install () {
    install -m 0755 -D -t "${D}${sbindir}" wpa_cli sdcsupp
    install -m 0755 -D -t "${D}${bindir}" wpa_passphrase

    if [ -e libwpa_client.so ]; then
        install -m 0644 -D -t "${D}${includedir}" "${S}/src/common/wpa_ctrl.h"
        install -m 0644 -D -t "${D}${libdir}" libwpa_client.so
    fi

    if ${@bb.utils.contains('DEPENDS', 'dbus', 'true', 'false', d)}; then
        install -m 0644 -D -t "${D}${sysconfdir}/dbus-1/system.d" dbus/dbus-wpa_supplicant.conf

        if [ "${SUPP_DBUS_P2P_ENABLE}" = "0" ]; then
            mkdir -p "${D}${sysconfdir}/wpa_supplicant"
            echo "p2p_disabled=1" > "${D}${sysconfdir}/wpa_supplicant/dbus_overlay.conf"
        fi
    fi

    if ${@bb.utils.contains('DISTRO_FEATURES', 'systemd', 'true', 'false', d)}; then
        install -m 0644 -D -t "${D}${systemd_unitdir}/system" systemd/*.service

        if ${@bb.utils.contains('DEPENDS', 'dbus', 'true', 'false', d)}; then
            install -m 0644 -D -t "${D}${datadir}/dbus-1/system-services" dbus/*.service
        fi
    fi
}

pkg_postinst:${PN} () {
    # If we're offline, we don't need to do this.
    if [ -z "$D" ]; then
        killall -q -HUP dbus-daemon || true
    fi
}
