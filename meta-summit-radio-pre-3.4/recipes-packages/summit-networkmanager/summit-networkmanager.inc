SECTION = "net/misc"

LICENSE = "GPLv2+ & LGPLv2.1+"
LIC_FILES_CHKSUM = "file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263 \
                    file://COPYING.LGPL;md5=4fbd65380cdd255951079008b364516c \
"

PV = "${RADIO_VERSION}"

# coreutils-native needed to support 'printenv' on kirkstone and below
DEPENDS = " \
    coreutils-native \
    libnl \
    udev \
    util-linux \
    libndp \
    libnewt \
    openssl \
"

inherit meson pkgconfig gettext update-rc.d systemd gobject-introspection gtk-doc update-alternatives

SUMMIT_URI ?= "${SUMMIT_URI_BASE}"
SUMMIT_URI_summit-internal = "${SUMMIT_URI_BASE}/lrd-network-manager/src/${PV}"

SRC_URI = " \
    ${SUMMIT_URI}/summit-network-manager-src-${PV}.tar.xz;name=summit-network-manager \
    file://networkmanager.initd \
    file://NetworkManager.conf \
"

S = "${WORKDIR}/summit-network-manager-${PV}"

# ['auto', 'symlink', 'file', 'netconfig', 'resolvconf']
NETWORKMANAGER_DNS_RC_MANAGER_DEFAULT ??= "auto"

# ['dhcpcd', 'internal', 'nettools']
NETWORKMANAGER_DHCP_DEFAULT ??= "internal"

EXTRA_OEMESON = "\
    -Difcfg_rh=false \
    -Dtests=no \
    -Dnmtui=true \
    -Dudev_dir=${nonarch_base_libdir}/udev \
    -Dlibpsl=false \
    -Dqt=false \
    -Dconfig_dns_rc_manager_default=${NETWORKMANAGER_DNS_RC_MANAGER_DEFAULT} \
    -Dconfig_dhcp_default=${NETWORKMANAGER_DHCP_DEFAULT} \
    -Dcrypto=openssl \
"

# stolen from https://github.com/void-linux/void-packages/blob/master/srcpkgs/NetworkManager/template
# avoids:
# | ../NetworkManager-1.16.0/libnm-core/nm-json.c:106:50: error: 'RTLD_DEEPBIND' undeclared (first use in this function); did you mean 'RTLD_DEFAULT'?
CFLAGS_append_libc-musl = " \
    -DRTLD_DEEPBIND=0 \
"

do_compile_prepend() {
    export GI_TYPELIB_PATH="${B}}/src/libnm-client-impl${GI_TYPELIB_PATH:+:$GI_TYPELIB_PATH}"
}

PACKAGECONFIG ??= "libedit dnsmasq nmcli \
    ${@bb.utils.contains('DISTRO_FEATURES', 'systemd', 'systemd', bb.utils.contains('DISTRO_FEATURES', 'x11', 'consolekit', '', d), d)} \
    ${@bb.utils.contains('DISTRO_FEATURES', 'bluetooth', 'bluez5', '', d)} \
    ${@bb.utils.contains('DISTRO_FEATURES', 'wifi', 'wifi', '', d)} \
    ${@bb.utils.contains('DISTRO_FEATURES', 'polkit', 'polkit', '', d)} \
    ${@bb.utils.contains('DISTRO_FEATURES', 'selinux', 'selinux audit', '', d)} \
    ${@bb.utils.contains('DISTRO_FEATURES','3g','modemmanager','',d)} \
"

inherit ${@bb.utils.contains('PACKAGECONFIG', 'nmcli', 'bash-completion', '', d)}
inherit ${@bb.utils.contains('PACKAGECONFIG', 'vala', 'vala', '', d)}

PACKAGECONFIG[systemd] = "\
    -Dsystemdsystemunitdir=${systemd_unitdir}/system -Dsession_tracking=systemd,\
    -Dsystemdsystemunitdir=no -Dsystemd_journal=false -Dsession_tracking=no\
"
PACKAGECONFIG[polkit] = "-Dpolkit=true,-Dpolkit=false,polkit"
PACKAGECONFIG[bluez5] = "-Dbluez5_dun=true,-Dbluez5_dun=false,bluez5"
# consolekit is not picked by shlibs, so add it to RDEPENDS too
PACKAGECONFIG[consolekit] = "-Dsession_tracking_consolekit=true,-Dsession_tracking_consolekit=false,consolekit,consolekit"
PACKAGECONFIG[modemmanager] = "-Dmodem_manager=true,-Dmodem_manager=false,modemmanager mobile-broadband-provider-info"
PACKAGECONFIG[ofono] = "-Dofono=true,-Dofono=false,ofono"
PACKAGECONFIG[ppp] = "-Dppp=true -Dpppd=/usr/sbin/pppd,-Dppp=false,ppp,ppp"
PACKAGECONFIG[dnsmasq] = "-Ddnsmasq=${bindir}/dnsmasq,,,dnsmasq"
PACKAGECONFIG[resolvconf] = "-Dresolvconf=${base_sbindir}/resolvconf,-Dresolvconf=no,,resolvconf"
PACKAGECONFIG[wifi] = "-Dwext=false -Dwifi=true,-Dwext=false -Dwifi=false"
PACKAGECONFIG[ifupdown] = "-Difupdown=true,-Difupdown=false"
PACKAGECONFIG[cloud-setup] = "-Dnm_cloud_setup=true,-Dnm_cloud_setup=false"
PACKAGECONFIG[nmcli] = "-Dnmcli=true,-Dnmcli=false"
PACKAGECONFIG[readline] = "-Dreadline=libreadline,,readline"
PACKAGECONFIG[libedit] = "-Dreadline=libedit,,libedit"
PACKAGECONFIG[ovs] = "-Dovs=true,-Dovs=false,jansson"
PACKAGECONFIG[audit] = "-Dlibaudit=yes,-Dlibaudit=no"
PACKAGECONFIG[selinux] = "-Dselinux=true,-Dselinux=false,libselinux"
PACKAGECONFIG[vala] = "-Dvapi=true,-Dvapi=false"
PACKAGECONFIG[dhcpcd] = "-Ddhcpcd=yes,-Ddhcpcd=no,,dhcpcd"
PACKAGECONFIG[concheck] = "-Dconcheck=true,-Dconcheck=false,curl,"

PACKAGES =+ " \
    ${PN}-adsl \
    ${PN}-bluetooth \
    ${PN}-cloud-setup \
    ${PN}-nmcli ${PN}-nmcli-doc \
    ${PN}-nmtui ${PN}-nmtui-doc \
    ${PN}-wifi \
    ${PN}-wwan \
    ${PN}-ovs ${PN}-ovs-doc \
    ${PN}-ppp \
"

SYSTEMD_PACKAGES = "${PN} ${PN}-cloud-setup"

NETWORKMANAGER_PLUGINDIR = "${libdir}/NetworkManager/*"

FILES_${PN}-adsl = "${NETWORKMANAGER_PLUGINDIR}/libnm-device-plugin-adsl.so"

FILES_${PN}-bluetooth = "${NETWORKMANAGER_PLUGINDIR}/libnm-device-plugin-bluetooth.so"

FILES_${PN}-cloud-setup = " \
    ${libexecdir}/nm-cloud-setup \
    ${systemd_system_unitdir}/nm-cloud-setup.service \
    ${systemd_system_unitdir}/nm-cloud-setup.timer \
    ${libdir}/NetworkManager/dispatcher.d/90-nm-cloud-setup.sh \
    ${libdir}/NetworkManager/dispatcher.d/no-wait.d/90-nm-cloud-setup.sh \
"
ALLOW_EMPTY_${PN}-cloud-setup = "1"
SYSTEMD_SERVICE_${PN}-cloud-setup = "${@bb.utils.contains('PACKAGECONFIG', 'cloud-setup', 'nm-cloud-setup.service nm-cloud-setup.timer', '', d)}"

FILES_${PN}-nmcli = " \
    ${bindir}/nmcli \
"

FILES_${PN}-nmcli-doc = " \
    ${mandir}/man1/nmcli* \
"

FILES_${PN}-nmtui = " \
    ${bindir}/nmtui \
    ${bindir}/nmtui-edit \
    ${bindir}/nmtui-connect \
    ${bindir}/nmtui-hostname \
"

FILES_${PN}-nmtui-doc = " \
    ${mandir}/man1/nmtui* \
"

FILES_${PN}-wifi = "${NETWORKMANAGER_PLUGINDIR}/libnm-device-plugin-wifi.so"

FILES_${PN}-wwan = "\
    ${NETWORKMANAGER_PLUGINDIR}/libnm-device-plugin-wwan.so \
    ${NETWORKMANAGER_PLUGINDIR}/libnm-wwan.so \
"

FILES_${PN}-ovs = "\
    ${NETWORKMANAGER_PLUGINDIR}/libnm-device-plugin-ovs.so \
    ${systemd_system_unitdir}/NetworkManager.service.d/NetworkManager-ovs.conf \
"

FILES_${PN}-ovs-doc = "\
    ${mandir}/man7/nm-openvswitch.7* \
"

FILES_${PN}-ppp = "\
    ${NETWORKMANAGER_PLUGINDIR}/libnm-ppp-plugin.so \
    ${libdir}/pppd/*/nm-pppd-plugin.so \
"

FILES_${PN}-dev += " \
    ${libdir}/pppd/*/*.la \
    ${libdir}/NetworkManager/*.la \
    ${NETWORKMANAGER_PLUGINDIR}/*.la \
"

FILES_${PN} += " \
    ${libexecdir} \
    ${libdir}/NetworkManager \
    ${nonarch_libdir}/firewalld/zones \
    ${nonarch_libdir}/NetworkManager/conf.d \
    ${nonarch_libdir}/NetworkManager/dispatcher.d \
    ${nonarch_libdir}/NetworkManager/dispatcher.d/pre-down.d \
    ${nonarch_libdir}/NetworkManager/dispatcher.d/pre-up.d \
    ${nonarch_libdir}/NetworkManager/dispatcher.d/no-wait.d \
    ${nonarch_libdir}/NetworkManager/VPN \
    ${nonarch_libdir}/NetworkManager/system-connections \
    ${datadir}/polkit-1 \
    ${datadir}/dbus-1 \
    ${nonarch_base_libdir}/udev/* \
    ${systemd_system_unitdir} \
"

RRECOMMENDS_${PN} += "\
    iptables \
    ${@bb.utils.contains('PACKAGECONFIG', 'dnsmasq', 'dnsmasq', '', d)} \
    ${@bb.utils.contains('PACKAGECONFIG','adsl','${PN}-adsl','',d)} \
    ${@bb.utils.contains('PACKAGECONFIG','bluez5','${PN}-bluetooth','',d)} \
    ${@bb.utils.contains('PACKAGECONFIG','cloud-setup','${PN}-cloud-setup','',d)} \
    ${@bb.utils.contains('PACKAGECONFIG','nmcli','${PN}-nmcli','',d)} \
    ${@bb.utils.contains('PACKAGECONFIG','nmtui','${PN}-nmtui','',d)} \
    ${@bb.utils.contains('PACKAGECONFIG','wifi','${PN}-wifi','',d)} \
    ${@bb.utils.contains('PACKAGECONFIG','modemmanager','${PN}-wwan','',d)} \
    ${@bb.utils.contains('PACKAGECONFIG','ovs','${PN}-ovs','',d)} \
    ${@bb.utils.contains('PACKAGECONFIG','ppp','${PN}-ppp','',d)} \
"

INITSCRIPT_NAME = "network-manager"
SYSTEMD_SERVICE_${PN} = "\
    NetworkManager.service \
    NetworkManager-dispatcher.service \
"

ALTERNATIVE_PRIORITY = "100"
ALTERNATIVE_${PN} = "${@bb.utils.contains('DISTRO_FEATURES','systemd','resolv-conf','',d)}"
ALTERNATIVE_TARGET[resolv-conf] = "${@bb.utils.contains('DISTRO_FEATURES','systemd','${sysconfdir}/resolv-conf.NetworkManager','',d)}"
ALTERNATIVE_LINK_NAME[resolv-conf] = "${@bb.utils.contains('DISTRO_FEATURES','systemd','${sysconfdir}/resolv.conf','',d)}"

do_install_append() {
    install -Dm 0644 "${SUMMIT_UNPACKDIR}/NetworkManager.conf" "${D}${sysconfdir}/NetworkManager/NetworkManager.conf"
    install -Dm 0755 "${SUMMIT_UNPACKDIR}/networkmanager.initd" "${D}${sysconfdir}/init.d/network-manager"

    rm -rf "${D}/run" "${D}${localstatedir}/run"

    if ${@bb.utils.contains('DISTRO_FEATURES','systemd','true','false',d)}; then
        # For read-only filesystem, do not create links during bootup
        ln -sf ../run/NetworkManager/resolv.conf "${D}${sysconfdir}/resolv-conf.NetworkManager"

        # systemd v210 and newer do not need this rule file
        rm "${D}/${nonarch_base_libdir}/udev/rules.d/84-nm-drivers.rules"
    fi
}

RCONFLICTS_${PN} = "connman"
RDEPENDS_${PN} += "ethtool"
RRECOMMENDS_${PN}-nmcli += "less"

PROVIDES += "networkmanager"

RPROVIDES_${PN} += "networkmanager"
RREPLACES_${PN} += "networkmanager"
RCONFLICTS_${PN} += "networkmanager"

RPROVIDES_${PN}-nmcli += "networkmanager-nmcli"
RREPLACES_${PN}-nmcli += "networkmanager-nmcli"
RCONFLICTS_${PN}-nmcli += "networkmanager-nmcli"

RPROVIDES_${PN}-nmcli-doc += "networkmanager-nmcli-doc"
RREPLACES_${PN}-nmcli-doc += "networkmanager-nmcli-doc"
RCONFLICTS_${PN}-nmcli-doc += "networkmanager-nmcli-doc"

RPROVIDES_${PN}-nmtui += "networkmanager-nmtui"
RREPLACES_${PN}-nmtui += "networkmanager-nmtui"
RCONFLICTS_${PN}-nmtui += "networkmanager-nmtui"

RPROVIDES_${PN}-nmtui-doc += "networkmanager-nmtui-doc"
RREPLACES_${PN}-nmtui-doc += "networkmanager-nmtui-doc"
RCONFLICTS_${PN}-nmtui-doc += "networkmanager-nmtui-doc"

RPROVIDES_${PN}-adsl += "networkmanager-adsl"
RREPLACES_${PN}-adsl += "networkmanager-adsl"
RCONFLICTS_${PN}-adsl += "networkmanager-adsl"

RPROVIDES_${PN}-bluetooth += "networkmanager-bluetooth"
RREPLACES_${PN}-bluetooth += "networkmanager-bluetooth"
RCONFLICTS_${PN}-bluetooth += "networkmanager-bluetooth"

RPROVIDES_${PN}-cloud-setup += "networkmanager-cloud-setup"
RREPLACES_${PN}-cloud-setup += "networkmanager-cloud-setup"
RCONFLICTS_${PN}-cloud-setup += "networkmanager-cloud-setup"

RPROVIDES_${PN}-wifi += "networkmanager-wifi"
RREPLACES_${PN}-wifi += "networkmanager-wifi"
RCONFLICTS_${PN}-wifi += "networkmanager-wifi"

RPROVIDES_${PN}-wwan += "networkmanager-wwan"
RREPLACES_${PN}-wwan += "networkmanager-wwan"
RCONFLICTS_${PN}-wwan += "networkmanager-wwan"

RPROVIDES_${PN}-ovs += "networkmanager-ovs"
RREPLACES_${PN}-ovs += "networkmanager-ovs"
RCONFLICTS_${PN}-ovs += "networkmanager-ovs"

RPROVIDES_${PN}-ppp += "networkmanager-ppp"
RREPLACES_${PN}-ppp += "networkmanager-ppp"
RCONFLICTS_${PN}-ppp += "networkmanager-ppp"

RPROVIDES_${PN}-ovs-doc += "networkmanager-ovs-doc"
RREPLACES_${PN}-ovs-doc += "networkmanager-ovs-doc"
RCONFLICTS_${PN}-ovs-doc += "networkmanager-ovs-doc"

RPROVIDES_${PN}-ppp-doc += "networkmanager-ppp-doc"
RREPLACES_${PN}-ppp-doc += "networkmanager-ppp-doc"
RCONFLICTS_${PN}-ppp-doc += "networkmanager-ppp-doc"

RPROVIDES_${PN}-dbg += "networkmanager-dbg"
RREPLACES_${PN}-dbg += "networkmanager-dbg"
RCONFLICTS_${PN}-dbg += "networkmanager-dbg"

RPROVIDES_${PN}-dev += "networkmanager-dev"
RREPLACES_${PN}-dev += "networkmanager-dev"
RCONFLICTS_${PN}-dev += "networkmanager-dev"
